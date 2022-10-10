package xipe.module.modules.Render;

import xipe.module.Mod;
import xipe.module.settings.NumberSetting;

public class Ambience extends Mod{
	
	 public NumberSetting r;
	 public NumberSetting g;
	 public NumberSetting b;

	public Ambience() {
		super("Ambience", "Colorful world", Category.RENDER);
		this.r = new NumberSetting("Red", 0.0, 255.0, 0.0, 1.0);
        this.g = new NumberSetting("Green", 0.0, 255.0, 0.0, 1.0);
        this.b = new NumberSetting("Blue", 0.0, 255.0, 0.0, 1.0);
        addSettings(this.r,this.g,this.b);
	}

}
