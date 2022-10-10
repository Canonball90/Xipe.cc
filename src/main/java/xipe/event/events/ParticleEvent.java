package xipe.event.events;

import net.minecraft.particle.ParticleEffect;
import xipe.event.Event;
import net.minecraft.client.particle.Particle;

import net.minecraft.particle.ParticleEffect;

public class ParticleEvent extends Event {
    public ParticleEffect particle;

    public ParticleEvent(ParticleEffect particle) {
        this.setCancelled(false);
        this.particle = particle;
    }
}