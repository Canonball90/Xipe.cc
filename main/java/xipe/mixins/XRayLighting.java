package xipe.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.AbstractBlock;
import xipe.module.ModuleManager;
import xipe.module.modules.Render.XRay;

@Mixin(AbstractBlock.AbstractBlockState.class)
public class XRayLighting {

     @Inject(method = "getLuminance", at = @At("HEAD"), cancellable = true)
        public void getLuminance(CallbackInfoReturnable<Integer> cir) {
            if (ModuleManager.INSTANCE.getModule(XRay.class).isEnabled()) {
                cir.setReturnValue(15);
            }
        }
    
}
