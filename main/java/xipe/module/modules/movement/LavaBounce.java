package xipe.module.modules.movement;

import xipe.module.Mod;

public class LavaBounce extends Mod{

	public LavaBounce() {
		super("Lava Bounce", "Lava is now bouncy", Category.MOVEMENT);
	}
	
	@Override
	public void onTick() {
		 if (mc.player.isInLava()) {
	            mc.player.setVelocity(0, -1.5, 0);
	        }
		if(mc.player.isOnFire()) {
			mc.player.setVelocity(0, 0.5, 0);
			
		}
		
		super.onTick();
	}


}
