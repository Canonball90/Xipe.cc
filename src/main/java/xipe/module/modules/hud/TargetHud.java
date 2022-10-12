package xipe.module.modules.hud;

import xipe.module.Mod;
import xipe.module.settings.NumberSetting;

public class TargetHud extends Mod{
	
	public NumberSetting color = new NumberSetting("Color", 0.1, 255.0, 200.0, 0.1);
	public NumberSetting color1 = new NumberSetting("Color2", 0.1, 255.0, 200.0, 0.1);
	public NumberSetting color2 = new NumberSetting("Color3", 0.1, 255.0, 20.0, 0.1);
	public NumberSetting range = new NumberSetting("Range", 1, 50.0, 35.0, 1);

	public TargetHud() {
		super("TargetHud", "Displays information about player target", Category.HUD);
		addSettings(color,color1,color2,range);
	}

}
