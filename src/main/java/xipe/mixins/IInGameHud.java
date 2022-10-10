package xipe.mixins;

import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(InGameHud.class)
public interface IInGameHud {
    @Accessor("VIGNETTE_TEXTURE")
    Identifier getVignette();
}