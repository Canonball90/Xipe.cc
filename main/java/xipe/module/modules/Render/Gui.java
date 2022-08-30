package xipe.module.modules.Render;

import org.lwjgl.glfw.GLFW;

import xipe.module.Mod;
import xipe.module.ModuleManager;
import xipe.module.settings.BooleanSetting;
import xipe.module.settings.ModeSetting;
import xipe.module.settings.NumberSetting;
import xipe.ui.screens.clickgui.ClickGUI;

public class Gui extends Mod{

	public ModeSetting theme = new ModeSetting("Theme", "Original", "Original", "OldSchool", "Win98", "Akuna", "Test");
	//public NumberSetting opacity = new NumberSetting("Opacity", 0,255,160,1);
	public NumberSetting red = new NumberSetting("Red", 0,255,160,0.1);
	public NumberSetting green = new NumberSetting("Green", 0,255,160,0.1);
	public NumberSetting blue = new NumberSetting("Blue", 0,255,160,0.1);
	public BooleanSetting snow = new BooleanSetting("Snow", false);
	public BooleanSetting topBar = new BooleanSetting("TopBar", false);
	public BooleanSetting outline = new BooleanSetting("Outline", true);
	public BooleanSetting cFont = new BooleanSetting("Custom Font", false);
	public BooleanSetting modNum= new BooleanSetting("Module Count", false);
	public BooleanSetting round= new BooleanSetting("Round Frame", false);
	public BooleanSetting custom = new BooleanSetting("Custom Color", false);
	public BooleanSetting rainbow = new BooleanSetting("Rainbow", false);
	public BooleanSetting bottomOutline = new BooleanSetting("Bottom Line", true);
	public NumberSetting scrollSpeed = new NumberSetting("Scroll Speed", 1,15,5,1);
	
	public static final Gui INSTANCE = new Gui();
	
	public Gui() {
		super("ClickGui", "Clicks the Gui", Category.RENDER);
		addSettings(theme,red,green,blue,snow,topBar,outline,cFont,modNum,round,custom,rainbow,scrollSpeed);
	}
	
}
