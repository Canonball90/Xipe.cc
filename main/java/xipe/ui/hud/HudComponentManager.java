package xipe.ui.hud;

import java.util.ArrayList;

import xipe.ui.hud.components.*;

public class HudComponentManager {

    private static final ArrayList<HudComponent> hudComponents = new ArrayList<>();

    public static void init() {
        hudComponents.add(new WelcomerHud());

    }

    public static ArrayList<HudComponent> getHudComponents() { return hudComponents; }

    public static HudComponent getHudComponent (String name) {
        for (HudComponent hc : hudComponents) if (hc.getName().equalsIgnoreCase(name)) return hc;
        return null;
    }

    public static void unDraw(){
        for (HudComponent hc: hudComponents) HudComponent.setDrawn(false);
    }

}
