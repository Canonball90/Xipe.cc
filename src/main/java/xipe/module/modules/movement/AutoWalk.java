package xipe.module.modules.movement;

import net.minecraft.client.option.KeyBinding;
import xipe.module.Mod;

public class AutoWalk extends Mod{

	public AutoWalk() {
		super("AutoWalk", "Automatically makes you walk", Category.MOVEMENT);
	}
	
	@Override
	public void onTick() {
		KeyBinding.setKeyPressed(mc.options.forwardKey.getDefaultKey(), true);
		super.onTick();
	}
	
	@Override
	public void onDisable() {
		KeyBinding.setKeyPressed(mc.options.forwardKey.getDefaultKey(), false);
		super.onDisable();
	}

}
