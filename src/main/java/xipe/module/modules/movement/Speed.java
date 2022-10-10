package xipe.module.modules.movement;

import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.util.math.Vec3d;
import xipe.module.Mod;
import xipe.module.settings.NumberSetting;

public class Speed extends Mod{

	public NumberSetting speed = new NumberSetting("Speed", 0, 10, 2, 1);
	
	public Speed() {
		super("Speed", "Makes you go faster", Category.MOVEMENT);
	}
	
	@Override
	public void onTick() {
		GameOptions go = mc.options;
    	float y = mc.player.getYaw();
    	int mx = 0, my = 0, mz = 0;

    	if (go.jumpKey.isPressed()) {
    		my++;
    	}
    	if (go.backKey.isPressed()) {
    		mz++;
    	}
    	if (go.leftKey.isPressed()) {
    		mx--;
    	}
    	if (go.rightKey.isPressed()) {
    		mx++;
    	}
    	if (go.sneakKey.isPressed()) {
    		my--;
    	}
    	if (go.forwardKey.isPressed()) {
    		mz--;
    	}
    	double ts = speed.getValueFloat() / 2;
        double s = Math.sin(Math.toRadians(y));
        double c = Math.cos(Math.toRadians(y));
        double nx = ts * mz * s;
        double nz = ts * mz * -c;
        double ny = ts * my;
        nx += ts * mx * -c;
        nz += ts * mx * -s;
        Vec3d nv3 = new Vec3d(nx, ny, nz);
        mc.player.setVelocity(nv3);
        KeyBinding.setKeyPressed(mc.options.sneakKey.getDefaultKey(), true);
		super.onTick();
	}

}
