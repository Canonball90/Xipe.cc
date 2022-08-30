package xipe.module.modules.movement;

import xipe.module.Mod;
import xipe.module.settings.NumberSetting;

public class Step extends Mod{

	NumberSetting height = new NumberSetting("Height", 1, 3, 2, 1.0);
	
	public Step() {
		super("Step", "Makes you step heigher", Category.MOVEMENT);
		addSetting(height);
	}

	@Override
	public void onTick() {
		mc.player.stepHeight = height.getValueInt();
		super.onTick();
	}
	
	@Override
	public void onDisable() {
		mc.player.stepHeight = 0.6f;
		super.onDisable();
	}
}
