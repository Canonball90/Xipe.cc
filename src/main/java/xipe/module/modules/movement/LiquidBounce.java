package xipe.module.modules.movement;

import xipe.module.Mod;
import xipe.module.settings.BooleanSetting;
import xipe.module.settings.NumberSetting;

public class LiquidBounce extends Mod{
	
	BooleanSetting lava = new BooleanSetting("Lava", true);
	BooleanSetting water = new BooleanSetting("Water", true);
	
	NumberSetting factor = new NumberSetting("Factor", 0.5, 3, 1, 0.1);

	public LiquidBounce() {
		super("LiquidBounce", "Allows you to bounce on liquids", Category.MOVEMENT);
		addSettings(lava,water,factor);
	}
	
	@Override
	public void onTick() {
		if(lava.isEnabled()) {
			if (mc.player.isInLava()) {
	            mc.player.setVelocity(0, -1.5, 0);
	        }
		 	if(mc.player.isOnFire()) {
			mc.player.setVelocity(0, factor.getValue(), 0);
			
			}
		}
		if(water.isEnabled()) {
			if(mc.player.isTouchingWater()) {
				mc.player.setVelocity(0, factor.getValue(), 0);
			}
		}
		
		super.onTick();
	}


}
