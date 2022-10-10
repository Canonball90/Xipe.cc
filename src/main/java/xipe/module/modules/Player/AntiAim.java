package xipe.module.modules.Player;

import xipe.module.Mod;
import xipe.module.settings.ModeSetting;

public class AntiAim extends Mod{
	
	public ModeSetting mode = new ModeSetting("Mode", "Silent", "Silent", "Normal");

	public AntiAim() {
		super("Anti-Aim", "Spinny Spinny", Category.WORLD);
		addSetting(mode);
	}
	
	@Override
	public void onTick() {
		if(mode.isMode("Normal")) {
			if (mc.currentScreen == null) {
				mc.player.setYaw(mc.player.getYaw() * 2);
			}
		}
		super.onTick();
	}

}
