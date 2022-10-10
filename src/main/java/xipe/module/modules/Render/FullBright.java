package xipe.module.modules.Render;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import xipe.module.Mod;
import xipe.module.settings.*;

public class FullBright extends Mod {
	
	ModeSetting mode = new ModeSetting("Mode", "Gamma", "Gamma", "NightVision");
	private StatusEffectInstance nightV;

    public FullBright() {
        super("FullBright", "Allows you to see dark spaces", Category.RENDER);
        addSetting(mode);
    }

    Double OldGamma;
    @SuppressWarnings("unchecked")

    @Override
    public void onTick() {
    	if(mode.isMode("Gamma")) {
    		if (this.isEnabled()) {
            	((ISimpleOption<Double>) (Object) mc.options.getGamma()).setValueUnrestricted(100.0d);
        	}
    	}
    	if (mode.isMode("NightVision")) mc.player.addStatusEffect(nightV);
    }
    @Override
    public void onEnable() {
    	nightV = new StatusEffectInstance(StatusEffects.NIGHT_VISION, Integer.MAX_VALUE, (int) 3 - 1);
        OldGamma = mc.options.getGamma().getValue();

        super.onEnable();
    }
    @SuppressWarnings("unchecked")
	@Override
    public void onDisable() {
        ((ISimpleOption<Double>) (Object) mc.options.getGamma()).setValueUnrestricted(OldGamma);
        mc.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
        super.onDisable();
    }
}
