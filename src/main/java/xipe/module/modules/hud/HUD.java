package xipe.module.modules.hud;

import net.minecraft.util.Formatting;
import xipe.module.Mod;
import xipe.module.settings.BooleanSetting;
import xipe.module.settings.ModeSetting;
import xipe.module.settings.NumberSetting;

public class HUD extends Mod{
	
	final Formatting gre = Formatting.GREEN;
	final Formatting gold = Formatting.GOLD;
	final Formatting r = Formatting.RESET;
	public BooleanSetting customFont = new BooleanSetting("Custom Font", false);
	public ModeSetting theme = new ModeSetting("Mode", "Normal", "Line", "Rainbow", "Rainbow & Line", "Normal", "B&W");

	public ModeSetting markTheme = new ModeSetting("WaterMark", "Normal", "Normal", "Background");
	public ModeSetting markName = new ModeSetting("WaterMark Name", "Xipe.cc", "Xipe.cc", "xipe.cc", "Nigga.cc", "SafePoint.club", "Hi", "Coffee", "Impact", "Skidd.ed", "Hypnotic", "BedTrap", "Meteor", "711.club", "Moloch.su", "FencingF+2", "Dream", "Sync", "Xenon", gold + "Prestige" +r+ "Client", "Firework", "Neon", "Wurst", "BigRat", "ToastClient", gre + "N" + r + "Hack", "Nodus", "JexClient", "Tensor", "catbot.il", "void.club", "PerryPhobos", "CocoClient", "WeebWare");
	
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
		super("Hud", "Displays the hud", Category.HUD);
		addSettings(theme,markTheme,markName,custom,customFont,red,green,blue,crystalCount,totemCount,obsidianCount,gappleCount);
	}

}
