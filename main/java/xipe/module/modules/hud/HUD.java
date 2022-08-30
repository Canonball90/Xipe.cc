package xipe.module.modules.hud;

import xipe.module.Mod;
import xipe.module.settings.BooleanSetting;
import xipe.module.settings.ModeSetting;
import xipe.module.settings.NumberSetting;

public class HUD extends Mod{
	
	public ModeSetting theme = new ModeSetting("Mode", "Normal", "Line", "Rainbow", "Rainbow & Line");

	public ModeSetting markTheme = new ModeSetting("WaterMark", "Normal", "Normal", "Background");
	
	public BooleanSetting custom = new BooleanSetting("Custom Color", false);
	
	public NumberSetting red = new NumberSetting("Watermark Red", 0,255,160,0.1);
	public NumberSetting green = new NumberSetting("Watermark Green", 0,255,160,0.1);
	public NumberSetting blue = new NumberSetting("Watermark Blue", 0,255,160,0.1);
	
	public BooleanSetting crystalCount = new BooleanSetting("Crystal Count", false);
	public BooleanSetting totemCount = new BooleanSetting("Totem Count", false);
	public BooleanSetting obsidianCount = new BooleanSetting("Obsidian Count", false);
	public BooleanSetting gappleCount = new BooleanSetting("Gapple Count", false);
	
	
	public static final HUD INSTANCE = new HUD();
	
	public HUD() {
		super("Hud", "Displays the hud", Category.RENDER);
		addSettings(theme,markTheme,custom,red,green,blue,crystalCount,totemCount,obsidianCount,gappleCount);
	}

}
