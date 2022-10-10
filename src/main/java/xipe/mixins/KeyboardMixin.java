package xipe.mixins;

import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Keyboard;
import xipe.Client;

@Mixin(Keyboard.class)
public class KeyboardMixin {
	
	private static boolean[] keys = new boolean[350];

	@Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
	public void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo info) {
		Client.INSTANCE.onKeyPress(key, action);
	        if (action == GLFW.GLFW_PRESS) {
	            keys[key] = true;
	        } else if (action == GLFW.GLFW_RELEASE) {
	            keys[key] = false;
	        }
	}

	public boolean isKeyPressed(int key) {
	        if (key == GLFW.GLFW_KEY_UNKNOWN) return false;
	        return key < keys.length && keys[key];
	}
}
