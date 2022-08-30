package xipe.module.modules.movement;

import xipe.module.Mod;
import xipe.module.settings.ModeSetting;

public class Dolphin extends Mod{
	
	ModeSetting mode = new ModeSetting("Mode", "Legit", "Legit", "Dolphin");

	public Dolphin() {
		super("Dolphin", "Makes you water go boom", Category.MOVEMENT);
		addSetting(mode);
	}
	
	@Override
	public void onTick() {
		if(mode.isMode("Dolphin")) {
			if(mc.player.isSwimming() && mc.player.isSubmergedInWater()) {
				mc.player.jump();
			}
		}else if(mode.isMode("Legit")){
			if (mc.player.isTouchingWater()) {
				mc.player.setVelocity(0, 0.1, 0);
			}
		}
		super.onTick();
	}

}
