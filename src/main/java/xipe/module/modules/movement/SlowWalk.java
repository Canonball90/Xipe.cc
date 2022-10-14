package xipe.module.modules.movement;

import com.google.common.eventbus.Subscribe;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import xipe.event.events.EventBlockBreakingCooldown;
import xipe.module.Mod;

public class SlowWalk extends Mod {
    public SlowWalk() {
        super("SlowWalk", "Slows you down", Category.MOVEMENT);
    }

    private StatusEffectInstance slowness;

    @Override
    public void onEnable() {
        slowness = new StatusEffectInstance(StatusEffects.SLOWNESS, Integer.MAX_VALUE, (int) 3 - 1);
    }

    @Override
    public void onDisable() {
            mc.player.removeStatusEffect(StatusEffects.SLOWNESS);

    }

    @Override
    public void onTick() {
        mc.player.addStatusEffect(slowness);
    }
}
