package xipe.module.modules.movement;

import xipe.module.Mod;
import xipe.module.settings.BooleanSetting;

public class FastStop extends Mod{
	
	BooleanSetting airStop;

	public FastStop() {
		super("FastStop", "Makes you stop instantly", Category.MOVEMENT);
		this.airStop = new BooleanSetting("Air-Stop", true);
	}
	
	 @Override
	public void onTick() {
	        assert mc.player != null;
	        if(!mc.options.forwardKey.isPressed() && !mc.options.backKey.isPressed() && !mc.options.leftKey.isPressed() && !mc.options.rightKey.isPressed()) {
	            if(mc.player.isOnGround() || airStop.isEnabled()){
	                mc.player.setVelocity(0.0, mc.player.getVelocity().y, 0.0);
	            }
	        }
	    }

}
