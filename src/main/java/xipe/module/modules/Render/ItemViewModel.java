package xipe.module.modules.Render;

import xipe.module.Mod;
import xipe.module.Mod.Category;
import xipe.module.settings.BooleanSetting;
import xipe.module.settings.NumberSetting;

public class ItemViewModel extends Mod {

    public static ItemViewModel INSTANCE;

    public ItemViewModel() {
    	super("ItemViewModel", "Changes the color of totem particles", Category.RENDER); 
    	addSettings(x,y,z,swingLeft,swingRight);
    	 INSTANCE = this;
    }

    public static final NumberSetting x = new NumberSetting("X", -4,4,0,0.01);
	public static final NumberSetting y = new NumberSetting("Y", -4,4,0,0.01);
	public static final NumberSetting z = new NumberSetting("Z", -4,4,0,0.01);
	
	public static final NumberSetting swingLeft = new NumberSetting("Swing Left", 0,1,1,0.1);
	public static final NumberSetting swingRight = new NumberSetting("Swing Right", 0,1,1,0.1);
	

}