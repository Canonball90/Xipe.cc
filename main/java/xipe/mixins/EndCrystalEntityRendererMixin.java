package xipe.mixins;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EndCrystalEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.decoration.EndCrystalEntity;
import xipe.module.modules.Render.customcrystal.CustomCrystal;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndCrystalEntityRenderer.class)
public abstract class EndCrystalEntityRendererMixin {

    private CustomCrystal rubicsCubeRenderer;

    private EndCrystalEntity endCrystalEntity;

    @Inject(method = "render", at = @At(value = "HEAD"))
    public void render(EndCrystalEntity endCrystalEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        this.endCrystalEntity = endCrystalEntity;
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "net/minecraft/client/util/math/MatrixStack.translate(DDD)V", ordinal = 1))
    public void translate(MatrixStack matrixStack, double x, double y, double z) {
        matrixStack.translate(x, this.endCrystalEntity != null && this.endCrystalEntity.shouldShowBottom() ? 1.2D : 1D, z);
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "net/minecraft/client/model/ModelPart.render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;II)V", ordinal = 1))
    public void renderFrame(ModelPart modelPart, MatrixStack matrices, VertexConsumer vertices, int light, int overlay) {
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "net/minecraft/client/model/ModelPart.render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;II)V", ordinal = 3))
    public void renderCore(ModelPart core, MatrixStack matrices, VertexConsumer vertices, int light, int overlay) {
        if (rubicsCubeRenderer == null) {
            rubicsCubeRenderer = new CustomCrystal();
        }

        rubicsCubeRenderer.render(core, matrices, vertices, light, overlay);
    }
}
