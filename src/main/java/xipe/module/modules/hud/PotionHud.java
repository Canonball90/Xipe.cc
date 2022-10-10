package xipe.module.modules.hud;

import xipe.module.Mod;
import xipe.module.Mod.Category;
import xipe.module.settings.NumberSetting;

public class PotionHud extends Mod{
	
	public NumberSetting x = new NumberSetting("X", -555,555,160,0.1);
	public NumberSetting y = new NumberSetting("Y", -555,555,160,0.1);

	public PotionHud() {
		super("PotionHud", "Shows player potion effects", Category.HUD);
		addSettings(x,y);
	}

}
