package xipe.mixins;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.util.math.Vec3d;
import xipe.module.ModuleManager;
import xipe.module.modules.movement.NoSlowDown;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author cattyngmd
 */

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity {
    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"))
    private boolean proxy_tickMovement_isUsingItem(ClientPlayerEntity player) {
        if (ModuleManager.INSTANCE.getModule(NoSlowDown.class).isEnabled() && NoSlowDown.INSTANCE.items.isEnabled()) {
            return false;
        }
        return player.isUsingItem();
    }
}
