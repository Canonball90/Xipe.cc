package xipe.module.modules.Combat;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.potion.PotionUtil;
import xipe.module.Mod;
import xipe.module.settings.BooleanSetting;
import xipe.module.settings.NumberSetting;
import xipe.utils.player.ChatUtil;
import xipe.utils.player.InventoryUtils;
import xipe.utils.world.FindItemResult;
import xipe.utils.world.TimerUtil;

public class Quiver extends Mod{
	
	public BooleanSetting strength = new BooleanSetting("Strenght", true);
	public BooleanSetting speed = new BooleanSetting("Speed", true);
	public BooleanSetting lowMode = new BooleanSetting("Low Mode", true);
	public BooleanSetting onlyOnGround = new BooleanSetting("Only on gorund", true);
	public BooleanSetting checkEffects = new BooleanSetting("Check Effects", true);
	public BooleanSetting silentBow = new BooleanSetting("Silent", true);
	
	public NumberSetting delay = new NumberSetting("Delay", 1, 30, 2, 0.1);

	public Quiver() {
		super("Quiver", "Shoots arrows with positive effects at yourself", Category.COMBAT);
		addSettings(strength,speed,lowMode,onlyOnGround,checkEffects,silentBow,delay);
	}

	  private final List<Integer> arrowSlots = new ArrayList<>();
	    TimerUtil afterTimer = new TimerUtil();
	    int interval;
	    int prevBowSlot;

	    @Override
	    public void onEnable() {
	        afterTimer.reset();
	        interval = 0;
	        prevBowSlot = -1;

	        FindItemResult bow = InventoryUtils.find(Items.BOW);

	        if (!bow.found()) {
	            toggle();
	            return;
	        }

	        if (silentBow.isEnabled() && !bow.isHotbar()) {
	            prevBowSlot = bow.getSlot();
	            InventoryUtils.move().from(bow.getSlot()).to(mc.player.getInventory().selectedSlot);
	        } else if (!bow.isHotbar()) {
	            ChatUtil.printError("Bow not found. Disabling");
	            toggle();
	            return;
	        }

	        mc.options.useKey.setPressed(false);
	        mc.interactionManager.stopUsingItem(mc.player);

	        if (!silentBow.isEnabled()) InventoryUtils.swap(bow.getSlot(), true);

	        arrowSlots.clear();

	        List<StatusEffect> usedEffects = new ArrayList<>();

	        for (int i = mc.player.getInventory().size(); i > 0; i--) {
	            if (i == mc.player.getInventory().selectedSlot) continue;

	            ItemStack item = mc.player.getInventory().getStack(i);

	            if (item.getItem() != Items.TIPPED_ARROW) continue;

	            List<StatusEffectInstance> effects = PotionUtil.getPotionEffects(item);

	            if (effects.isEmpty()) continue;

	            StatusEffect effect = effects.get(0).getEffectType();

	            if ((strength.isEnabled() && effect == StatusEffects.STRENGTH || speed.isEnabled() && effect == StatusEffects.SPEED)
	                    && !usedEffects.contains(effect)
	                    && (!hasEffect(effect) || !checkEffects.isEnabled())) {
	                usedEffects.add(effect);
	                arrowSlots.add(i);
	            }
	        }
	    }

	    private boolean hasEffect(StatusEffect effect) {
	        for (StatusEffectInstance statusEffect : mc.player.getStatusEffects()) {
	            if (statusEffect.getEffectType() == effect) return true;
	        }

	        return false;
	    }

	    @Override
	    public void onDisable() {
	        if (lowMode.isEnabled()) mc.options.sneakKey.setPressed(false);
	        if (silentBow.isEnabled() && prevBowSlot != -1)
	        	InventoryUtils.move().from(mc.player.getInventory().selectedSlot).to(prevBowSlot);
	        else
	        	InventoryUtils.swapBack();
	    }

	    @Override
	    public void onTick() {
	        if (onlyOnGround.isEnabled() && !mc.player.isOnGround()) return;
	        if (arrowSlots.isEmpty() || !InventoryUtils.findInHotbar(Items.BOW).isMainHand()) {
	            if (afterTimer.passedSec(1)) toggle();
	            return;
	        }

	        interval--;
	        if (interval > 0) return;

	        boolean charging = mc.options.useKey.isPressed();
	        double charge = lowMode.isEnabled() ? 0.1 : 0.12;

	        if (!charging) {
	        	InventoryUtils.move().from(arrowSlots.get(0)).to(9);
	            mc.options.useKey.setPressed(true);
	        } else {
	            if (BowItem.getPullProgress(mc.player.getItemUseTime()) >= charge) {
	                int targetSlot = arrowSlots.get(0);
	                arrowSlots.remove(0);

	                mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(mc.player.getYaw(), -90, mc.player.isOnGround()));
	                mc.options.useKey.setPressed(false);
	                mc.interactionManager.stopUsingItem(mc.player);
	                if (targetSlot != 9) InventoryUtils.move().from(9).to(targetSlot);
	                if (arrowSlots.isEmpty() && lowMode.isEnabled()) mc.options.sneakKey.setPressed(true);
	                interval = delay.getValueInt();
	                afterTimer.reset();
	            }
	        }
	    }
	
}
