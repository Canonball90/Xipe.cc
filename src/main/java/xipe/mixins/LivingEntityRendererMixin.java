package xipe.mixins;

import net.minecraft.entity.LivingEntity;
import xipe.utils.player.RotationUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import org.spongepowered.asm.mixin.injection.At;

@Mixin({ LivingEntityRenderer.class })
public class LivingEntityRendererMixin
{
    private static MinecraftClient mc;
    
    @ModifyVariable(method = { "render" }, ordinal = 5, at = @At(value = "STORE", ordinal = 3))
    public float changePitch(final float oldValue, final LivingEntity entity) {
        if (entity.equals((Object)LivingEntityRendererMixin.mc.player) && RotationUtils.isCustomPitch) {
            return RotationUtils.serverPitch;
        }
        return oldValue;
    }
    
    @ModifyVariable(method = { "render" }, ordinal = 2, at = @At(value = "STORE", ordinal = 0))
    public float changeYaw(final float oldValue, final LivingEntity entity) {
        if (entity.equals((Object)LivingEntityRendererMixin.mc.player) && RotationUtils.isCustomYaw) {
            return RotationUtils.serverYaw;
        }
        return oldValue;
    }
    
    @ModifyVariable(method = { "render" }, ordinal = 3, at = @At(value = "STORE", ordinal = 0))
    public float changeHeadYaw(final float oldValue, final LivingEntity entity) {
        if (entity.equals((Object)LivingEntityRendererMixin.mc.player) && RotationUtils.isCustomYaw) {
            return RotationUtils.serverYaw;
        }
        return oldValue;
    }
    
    static {
        LivingEntityRendererMixin.mc = MinecraftClient.getInstance();
    }
}

