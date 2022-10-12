package xipe.module.modules.Player;

import java.util.function.Predicate;

import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import xipe.mixins.MinecraftClientAccessor;
import xipe.module.Mod;
import xipe.module.settings.BooleanSetting;
import xipe.module.settings.NumberSetting;
import xipe.utils.player.InventoryUtils;
import xipe.utils.world.FindItemResult;
import xipe.utils.world.TimerUtil;

public class FastPlace extends Mod{
	
	NumberSetting delay = new NumberSetting("Delay", 0, 4, 0, 0.1);
	NumberSetting startDelay = new NumberSetting("Start Delay", 15, 500, 60, 1);
	
	BooleanSetting autoPlace = new BooleanSetting("Auto Place", false);
	BooleanSetting autoSwitch = new BooleanSetting("Auto Switch", false);
	BooleanSetting onlyXP = new BooleanSetting("Only Xp", false);
	
	public final TimerUtil timer = new TimerUtil();

	public FastPlace() {
		super("FastPlace", "Places block faster", Category.WORLD);
		addSettings(delay,startDelay,autoPlace,autoSwitch,onlyXP);
	}
	
	  @Override
	    public void onTick() {
	        FindItemResult blocks = InventoryUtils.findInHotbar(itemStack -> itemStack.getItem() instanceof BlockItem);
	        FindItemResult exp = InventoryUtils.findInHotbar(Items.EXPERIENCE_BOTTLE);

	        if (!exp.found() && (!(mc.crosshairTarget instanceof BlockHitResult))) return;
	        if (exp.isMainHand() || (!onlyXP.isEnabled() && (mc.player.getMainHandStack().getItem() instanceof BlockItem || blocks.found() && autoSwitch.isEnabled()))) {
	            if (autoSwitch.isEnabled() && blocks.found()) InventoryUtils.swap(blocks.getSlot(), false);
	            if (autoPlace.isEnabled()) mc.options.useKey.setPressed(true);

	            int i = ((MinecraftClientAccessor) mc).getItemUseCooldown();

	            if (!timer.passedMillis(startDelay.getValueInt())) return;
	            if (i == 0) timer.reset();
	            if (!timer.passedMillis(startDelay.getValueInt())) return;

	            if (i > delay.getValue()) i = delay.getValueInt();
	            ((MinecraftClientAccessor) mc).setItemUseCooldown(i);
	        }
	    }

}
