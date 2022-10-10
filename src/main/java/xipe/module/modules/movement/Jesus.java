package xipe.module.modules.movement;

import net.minecraft.client.option.GameOptions;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
import xipe.module.Mod;
import xipe.module.settings.ModeSetting;
import xipe.module.settings.NumberSetting;

public class Jesus extends Mod{
	
	ModeSetting mode = new ModeSetting("Mode", "Static", "Static", "Dolphin", "Bypass", "Velocity");
	NumberSetting speed = new NumberSetting("Factor", 1, 10, 2, 1);
	NumberSetting velStrength = new NumberSetting("Velocity Strength", 0.0003, 0.3, 0.1, 0.0001);

	public Jesus() {
		super("Jesus", "Makes you water go boom", Category.MOVEMENT);
		addSettings(mode,speed,velStrength);
	}
	
	private static final Formatting Gray = Formatting.GRAY;
	
	@Override
	public void onTick() {
		this.setDisplayName("Jesus" + Gray + "["+mode.getMode()+"]");
		if(mode.isMode("Dolphin")) {
			if(mc.player.isSwimming() && mc.player.isSubmergedInWater()) {
				mc.player.jump();
			}
		}else if(mode.isMode("Static")){
			if (mc.player.isTouchingWater()) {
				mc.player.setVelocity(0, 0.1, 0);
				GameOptions go = mc.options;
	        	float y = mc.player.getYaw();
	        	int mx = 0, mz = 0;
	        	if (go.backKey.isPressed()) {
	        		mz++;
	        	}
	        	if (go.leftKey.isPressed()) {
	        		mx--;
	        	}
	        	if (go.rightKey.isPressed()) {
	        		mx++;
	        	}
	        	if (go.forwardKey.isPressed()) {
	        		mz--;
	        	}
	        	double ts = speed.getValueFloat() / 2;
	            double s = Math.sin(Math.toRadians(y));
	            double c = Math.cos(Math.toRadians(y));
	            double nx = ts * mz * s;
	            double nz = ts * mz * -c;
	            nx += ts * mx * -c;
	            nz += ts * mx * -s;
	            Vec3d nv3 = new Vec3d(nx, mc.player.getVelocity().y, nz);
	            mc.player.setVelocity(nv3);
	        	
			}
		}else if(mode.isMode("Bypass")){
			if (mc.player.isTouchingWater()) {
				mc.player.setVelocity(0, 0.1, 0);
			}
		}else if(mode.isMode("Velocity")) {
			if(mc.player.isTouchingWater()) {
				mc.player.setVelocity(mc.player.getVelocity().x, velStrength.getValue(), mc.player.getVelocity().z);
			}
		}
		super.onTick();
	}

}
