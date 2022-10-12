package xipe.module.modules.Render;

import xipe.module.Mod;
import xipe.module.settings.ISimpleOption;
import xipe.module.settings.NumberSetting;

public class Zoom extends Mod{

    NumberSetting zoom = new NumberSetting("Zoom", 1, 10, 10, 1);

    public Zoom() {
        super("Zoom", "zoom", Category.RENDER);
    }

    Double oldFOV;

    @Override
    public void onEnable() {
        oldFOV = mc.options.getGamma().getValue();
    }

    @Override
    public void onTick() {
        ((ISimpleOption<Double>) (Object) mc.options.getFov()).setValueUnrestricted(10.0d);
        super.onTick();
    }

    @Override
    public void onDisable() {
        ((ISimpleOption<Double>) (Object) mc.options.getGamma()).setValueUnrestricted(oldFOV);
    }

}
