package xipe.module.modules.hud;

import xipe.module.Mod;
import xipe.module.settings.NumberSetting;

public class Indicators extends Mod{
	
	public NumberSetting x = new NumberSetting("X", -555,555,160,0.1);
	public NumberSetting y = new NumberSetting("Y", -555,555,160,0.1);

	public Indicators() {
		super("Indicators", "Shows player info", Category.HUD);
		addSettings(x,y);
	}

}
