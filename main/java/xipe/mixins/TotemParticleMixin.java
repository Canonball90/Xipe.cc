package xipe.mixins;

import net.minecraft.client.particle.AnimatedParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.particle.TotemParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;
import xipe.module.ModuleManager;
import xipe.module.modules.Render.Confetti;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TotemParticle.class)
public abstract class TotemParticleMixin extends AnimatedParticle {
    //Thank you Walaryne for cool module

    protected TotemParticleMixin(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider, float upwardsAcceleration) {
        super(world, x, y, z, spriteProvider, upwardsAcceleration);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onConfettiConstructor(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider, CallbackInfo ci) {
        TotemParticle totemParticle = ((TotemParticle)(Object) this);
        if(ModuleManager.INSTANCE.getModule(Confetti.class).isEnabled()) {
            Vec3d rainbowColor = Confetti.getRainbowColor();

            if (this.random.nextInt(4) == 0) {
                totemParticle.setColor(
                        Confetti.isRainbow() ? (float) rainbowColor.x : (float) Confetti.red.getValueFloat(),
                        Confetti.isRainbow() ? (float) rainbowColor.y : (float) Confetti.green.getValueFloat(),
                        Confetti.isRainbow() ? (float) rainbowColor.x : (float) Confetti.blue.getValueFloat()
                );
            } else {
                totemParticle.setColor(
                        Confetti.isRainbow() ? (float) rainbowColor.x : (float) Confetti.red.getValueFloat(),
                        Confetti.isRainbow() ? (float) rainbowColor.y : (float) Confetti.green.getValueFloat(),
                        Confetti.isRainbow() ? (float) rainbowColor.z : (float) Confetti.blue.getValueFloat()
                );
            }
        }
    }

}
