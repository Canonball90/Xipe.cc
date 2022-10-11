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
	

	//high jump
	@Override
	public void onTick() {
		nullCheck();
		if(mc.player.isOnGround() && mc.options.jumpKey.isPressed()) {
			mc.player.setVelocity(mc.player.getVelocity().x, 1, mc.player.getVelocity().z);
		}

		super.onTick();
	}
}
