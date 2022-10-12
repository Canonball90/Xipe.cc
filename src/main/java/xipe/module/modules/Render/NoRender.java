package xipe.module.modules.Render;

import com.google.common.eventbus.Subscribe;

import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import xipe.event.events.ParticleEvent;
import xipe.module.Mod;
import xipe.module.settings.BooleanSetting;

public class NoRender extends Mod{

	public BooleanSetting explosions = new BooleanSetting("Players", true);
	public BooleanSetting rockets = new BooleanSetting("Crystals", true);
	public BooleanSetting fog = new BooleanSetting("Players", true);
	public BooleanSetting hurt = new BooleanSetting("Crystals", true);
	public BooleanSetting pop = new BooleanSetting("Players", true);
	public BooleanSetting particles = new BooleanSetting("Crystals", true);

	public NoRender() {
		super("NoRender", "Prevent stuff from Rendering", Category.RENDER);
	}
	

    @Subscribe
    public void onParticle(ParticleEvent event) {
        if (explosions.isEnabled() && event.particle == ParticleTypes.EXPLOSION) event.cancel();
        if (rockets.isEnabled() && event.particle == ParticleTypes.FIREWORK) event.cancel();
        if (particles.isEnabled()) event.cancel();
    }

    @Override
    public void onTick() {
        if (mc.player != null && mc.player.hasStatusEffect(StatusEffects.BLINDNESS) || mc.player.hasStatusEffect(StatusEffects.NAUSEA)) {
            mc.player.removeStatusEffectInternal(StatusEffects.NAUSEA);
            mc.player.removeStatusEffectInternal(StatusEffects.BLINDNESS);

        }
    }

    public static NoRender get;


}
