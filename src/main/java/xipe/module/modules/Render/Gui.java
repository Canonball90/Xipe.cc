package xipe.module.modules.Render;

import org.lwjgl.glfw.GLFW;

import xipe.module.Mod;
import xipe.module.ModuleManager;
import xipe.module.settings.BooleanSetting;
import xipe.module.settings.ModeSetting;
import xipe.module.settings.NumberSetting;
import xipe.ui.screens.clickgui.ClickGUI;

public class Gui extends Mod{
	
	//BooleanSettings
	public BooleanSetting topBar = new BooleanSetting("TopBar", false);
	public BooleanSetting outline = new BooleanSetting("Outline", true);
	public BooleanSetting cFont = new BooleanSetting("Custom Font", false);
	public BooleanSetting modNum= new BooleanSetting("Module Count", true);
	public BooleanSetting custom = new BooleanSetting("Custom Color", false);
	public BooleanSetting rainbow = new BooleanSetting("Rainbow", false);
	public BooleanSetting bottomOutline = new BooleanSetting("Bottom Line", true);
	public BooleanSetting transparent = new BooleanSetting("Transparent", false);
	public BooleanSetting Nicons = new BooleanSetting("New Icons", true);
	public BooleanSetting rParticals = new BooleanSetting("Rainbow particles", true);


	//NumberSettings
	//public NumberSetting opacity = new NumberSetting("Opacity", 0,255,160,1);
	public NumberSetting red = new NumberSetting("Red", 0,255,100,0.1);
	public NumberSetting green = new NumberSetting("Green", 0,255,29,0.1);
	public NumberSetting blue = new NumberSetting("Blue", 0,255,231,0.1);
	public NumberSetting pRed = new NumberSetting("P-Red", 0,255,100,0.1);
	public NumberSetting pGreen = new NumberSetting("P-Green", 0,255,29,0.1);
	public NumberSetting pBlue = new NumberSetting("P-Blue", 0,255,231,0.1);
	public NumberSetting scrollSpeed = new NumberSetting("Scroll Speed", 1,15,5,1);
	
	
	//ModeSettings
	public ModeSetting description = new ModeSetting("Description", "Mode1", "Mode1", "Mode2", "Mode3", "Topbar");
	public ModeSetting ke = new ModeSetting("Button", "+", "+", "...", "keybind");
	public ModeSetting theme = new ModeSetting("Theme", "Original", "Original", "Win98", "Sigma Old");
	public ModeSetting sep = new ModeSetting("", " ", " ", " ", " ");
	
	public static final Gui INSTANCE = new Gui();
	
	public Gui() {
		super("ClickGui", "Clicks the Gui", Category.RENDER);
		addSettings(theme,description,ke,red,green,blue,sep,pRed,pGreen,pBlue,topBar,outline,bottomOutline,transparent,cFont,modNum,Nicons,custom,rainbow,rParticals,scrollSpeed);
	}
	
}
