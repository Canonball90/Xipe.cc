package xipe.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.Mouse;
import xipe.module.ModuleManager;
import xipe.module.modules.exploit.ClickTP;

import org.spongepowered.asm.mixin.Mixin;

@Mixin({ Mouse.class })
public class MouseMixin
{
    @Inject(method = { "onMouseButton" }, at = { @At("HEAD") }, cancellable = true)
    public void onMouseButton(final long window, final int button, final int action, final int mods, final CallbackInfo ci) {
        if (action == 1 && button == 1 && ModuleManager.INSTANCE.getModule(ClickTP.class).isEnabled()) {
            ClickTP.clickTPaction();
        }
    }
}