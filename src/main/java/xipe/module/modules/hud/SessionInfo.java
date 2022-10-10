package xipe.module.modules.hud;

import xipe.module.Mod;
import xipe.module.settings.ModeSetting;
import xipe.module.settings.NumberSetting;

public class SessionInfo extends Mod{
	
	public NumberSetting x = new NumberSetting("X", -555,555,160,0.1);
	public NumberSetting y = new NumberSetting("Y", -555,555,160,0.1);

	public SessionInfo() {
		super("Session Info", "Shows info on your current session", Category.HUD);
		addSettings(x,y);
	}

}
