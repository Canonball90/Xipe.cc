package xipe.module.modules.movement;

import xipe.module.Mod;

public class MoonGravity extends Mod{

	public MoonGravity() {
		super("MoonGravity", "Simulates moon gravity", Category.MOVEMENT);
	}
	
	@Override
	public void onTick() {
		nullCheck();
		mc.player.addVelocity(0, 0.0568000030517578, 0);
		super.onTick();
	}

}
