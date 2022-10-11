package xipe.mixins;

import java.io.IOException;
import java.io.InputStream;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import xipe.Client;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	public void onTick(CallbackInfo ci) {
		Client.INSTANCE.onTick();
	}
	
	 @Inject(method = "getWindowTitle", at = @At("HEAD"), cancellable = true)
	    public void getWindowTitle(CallbackInfoReturnable<String> ci) {
	        ci.setReturnValue("Xipe.cc +|+ By: CanonBall90,Nitaki_,xracer");
	    }
	
	@Inject(at = @At("TAIL"), method = "scheduleStop")
	public void onShutdown(CallbackInfo ci) {
		Client.INSTANCE.stopped();
	}
}

