package xipe.mixins;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Quaternion;
import net.minecraft.world.World;
import xipe.module.modules.hud.IItemRenderer;
import xipe.event.events.EventRenderItem;


@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin implements IItemRenderer {

	@Shadow
	public float zOffset;
	@Shadow
	@Final
	private TextureManager textureManager;

	@Shadow
	public abstract void renderItem(ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model);
	
	@Shadow
	public abstract BakedModel getModel(ItemStack stack, @Nullable World world, @Nullable LivingEntity entity, int i);
	
	
	@Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At("RETURN"), cancellable = true)
	public void postRenderItem(ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model, CallbackInfo ci) {
		EventRenderItem event = new EventRenderItem(matrices, stack, renderMode, EventRenderItem.RenderTime.POST, leftHanded);
		event.call();
		if (event.isCancelled()) ci.cancel();
	}

	@Inject(method = "renderGuiItemOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", at = @At("RETURN"), cancellable = true)
	public void renderGuiItemOverlay(TextRenderer renderer, ItemStack stack, int x, int y, String countLabel, CallbackInfo ci) {
	}

	@Override
	public void renderItemIntoGUI(ItemStack itemStack, float x, float y, float scale) {
		renderGuiItemModel(itemStack, x, y, this.getModel(itemStack, (World) null, null, 0), scale);
	}

	@SuppressWarnings("deprecation")
	protected void renderGuiItemModel(ItemStack stack, float x, float y, BakedModel model, float scale) {
		this.textureManager.getTexture(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).setFilter(false, false);
		RenderSystem.setShaderTexture(0, SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);
		RenderSystem.enableBlend();
		RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		MatrixStack matrixStack = RenderSystem.getModelViewStack();
		matrixStack.push();
		matrixStack.translate((double) x, (double) y, (double) (100.0F + this.zOffset));
		matrixStack.translate(8.0D, 8.0D, 0.0D);
		matrixStack.scale(scale, -scale, 1.0F);
		matrixStack.scale(16.0F, 16.0F, 16.0F);
		RenderSystem.applyModelViewMatrix();
		MatrixStack matrixStack2 = new MatrixStack();
		VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
		boolean bl = !model.isSideLit();
		if (bl) {
			DiffuseLighting.disableGuiDepthLighting();
		}

		this.renderItem(stack, ModelTransformation.Mode.GUI, false, matrixStack2, immediate, 15728880, OverlayTexture.DEFAULT_UV, model);
		immediate.draw();
		RenderSystem.enableDepthTest();
		if (bl) {
			DiffuseLighting.enableGuiDepthLighting();
		}

		matrixStack.pop();
		RenderSystem.applyModelViewMatrix();
	}
}
