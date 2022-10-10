package xipe.mixins;

import net.minecraft.client.particle.FireworksSparkParticle;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import xipe.module.ModuleManager;
import xipe.module.modules.Render.NoRender;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {FireworksSparkParticle.Flash.class})
public class MixinFireworksSparkParticle {
    @Inject(method = "buildGeometry", at = @At("HEAD"), cancellable = true)
    private void buildExplosionGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta, CallbackInfo info) {
        if (ModuleManager.INSTANCE.getModule(NoRender.class).isEnabled() && NoRender.get.rockets.isEnabled()) info.cancel();
    }
}
