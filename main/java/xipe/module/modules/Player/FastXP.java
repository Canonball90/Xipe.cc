package xipe.module.modules.Player;

import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.TimeHelper;
import xipe.module.Mod;
import xipe.module.settings.BooleanSetting;
import xipe.module.settings.NumberSetting;

public class FastXP extends Mod {
	
	public NumberSetting delay = new NumberSetting("Delay", 0, 1, 0, 0.1);
	public BooleanSetting swap = new BooleanSetting("Switch Item", true);
	
	TimeHelper timer = new TimeHelper();
	
	public FastXP() {
		super("FastXP", "Throw exp pots at the speed of sound", Category.WORLD);
		addSettings(delay, swap);
	}
	
	@Override
	public void onTick() {
		if (mc.player.getMainHandStack().getItem() == Items.EXPERIENCE_BOTTLE && mc.options.useKey.isPressed()) {
			mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
		} else if (mc.player.getMainHandStack().getItem() != Items.EXPERIENCE_BOTTLE && swap.isEnabled()) {
			int xpSlot = mc.player.getInventory().getSlotWithStack(Items.EXPERIENCE_BOTTLE.getDefaultStack());
			if (xpSlot != -1) mc.player.getInventory().selectedSlot = xpSlot;
		}
		super.onTick();
	}
}
