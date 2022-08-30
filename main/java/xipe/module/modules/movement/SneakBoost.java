package xipe.module.modules.movement;

import xipe.module.Mod;
import xipe.module.settings.BooleanSetting;

public class SneakBoost extends Mod{

	BooleanSetting up = new BooleanSetting("Up", false);
	BooleanSetting down = new BooleanSetting("Down", false);
	BooleanSetting side = new BooleanSetting("One Side", false);
	BooleanSetting side1 = new BooleanSetting("The Other Side", false);
	
	public SneakBoost() {
		super("Sneak Boost", "Somewhat broken module but either boosts your sneak or flys you backwards", Category.MOVEMENT);
		addSettings(up,down,side,side1);
	}
	
	@Override
	public void onTick() {
		if(up.isEnabled()) {
			if(mc.player.isSneaking()) {
				mc.player.setMovementSpeed(50);
				mc.player.setVelocity(0, 1, 0);
			}
		}else if(down.isEnabled()) {
			if(mc.player.isSneaking()) {
				mc.player.setMovementSpeed(50);
				mc.player.setVelocity(0, -1,0);
			}
		}else if(side.isEnabled()) {
			if(mc.player.isSneaking()) {
				mc.player.setMovementSpeed(50);
				mc.player.setVelocity(0, 0,1);
			}
		}else if(side1.isEnabled()) {
			if(mc.player.isSneaking()) {
				mc.player.setMovementSpeed(50);
				mc.player.setVelocity(1, 0,0);
			}
		}
		
		super.onTick();
	}


}
