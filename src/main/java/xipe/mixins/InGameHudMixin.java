package xipe.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import xipe.module.ModuleManager;
import xipe.module.modules.Render.DamageTint;
import xipe.ui.Hud;
import xipe.utils.render.RenderUtils;

@Mixin(InGameHud.class)
public class InGameHudMixin {

	@Inject(method = "render", at = @At("RETURN"), cancellable = true)
	public void renderHud(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
		Hud.render(matrices, tickDelta);
	}
	
	 @Inject(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;interactionManager:Lnet/minecraft/client/network/ClientPlayerInteractionManager;", ordinal = 0))
	    private void onRender(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
	        if (MinecraftClient.getInstance().player == null && MinecraftClient.getInstance().interactionManager == null) return;

	        DamageTint module = ModuleManager.INSTANCE.getModule(DamageTint.class);
	        float threshold = (float) 11;
	        float power = (float) 1;
	        if (MinecraftClient.getInstance().interactionManager.getCurrentGameMode().isSurvivalLike() && module.isEnabled())
	            RenderUtils.drawVignette(threshold, power);
	    }
}