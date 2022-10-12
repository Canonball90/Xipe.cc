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

}
