package xipe.module.modules.movement;

import org.lwjgl.glfw.GLFW;

import xipe.module.Mod;

public class Sprint extends Mod{

	public Sprint() {
		super("Sprint", "Makes you sprint constently", Category.MOVEMENT);
		//this.setKey(GLFW.GLFW_KEY_V);
	}

	@Override
	public void onTick() {
		mc.player.setSprinting(true);
		super.onTick();
	}
}
