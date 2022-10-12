package xipe.module.modules.Player;

import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.TimeHelper;
import xipe.module.Mod;
import xipe.module.settings.BooleanSetting;
import xipe.module.settings.NumberSetting;

public class FastUse extends Mod {
	
	public NumberSetting delay = new NumberSetting("Delay", 0, 1, 0, 0.1);
	
	
	public BooleanSetting crystal = new BooleanSetting("Crystal", true);
	public BooleanSetting xp = new BooleanSetting("Xp Bottle", true);
	public BooleanSetting swap = new BooleanSetting("Switch Item", false);
	
	TimeHelper timer = new TimeHelper();
	
	public FastUse() {
		super("FastUse", "Makes you use item faster", Category.WORLD);
		addSettings(delay,swap,xp,crystal);
	}
	
	@Override
	public void onTick() {
		if(xp.isEnabled()) {
			if (mc.player.getMainHandStack().getItem() == Items.EXPERIENCE_BOTTLE && mc.options.useKey.isPressed()) {
				mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
			}
		} 
		if(crystal.isEnabled()) {
			if (mc.player.getMainHandStack().getItem() == Items.END_CRYSTAL && mc.options.useKey.isPressed()) {
				mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
			} 
		}
		if (mc.player.getMainHandStack().getItem() != Items.EXPERIENCE_BOTTLE && swap.isEnabled()) {
			int xpSlot = mc.player.getInventory().getSlotWithStack(Items.EXPERIENCE_BOTTLE.getDefaultStack());
			if (xpSlot != -1) mc.player.getInventory().selectedSlot = xpSlot;
		}
		super.onTick();
	}
}
