package xipe.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.SplashOverlay;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xipe.ui.screens.mainmenu.LoadingScreen;

@Mixin(SplashOverlay.class)
public class SplashScreenMixin {

    @Shadow
    @Final
    private static Identifier LOGO;

    @Inject(method = "init(Lnet/minecraft/client/MinecraftClient;)V", at = @At("TAIL"), cancellable=true)
    private static void init(MinecraftClient client, CallbackInfo ci) {client.getTextureManager().registerTexture(LOGO, new LoadingScreen(LOGO));

    }
}