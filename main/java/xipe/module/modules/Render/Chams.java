package xipe.module.modules.Render;

import xipe.module.Mod;
import xipe.module.settings.BooleanSetting;

public class Chams extends Mod{
	
	private static Chams INSTANCE = new Chams();
	
	 public final BooleanSetting rainbow = new BooleanSetting("Rainbow", true);
	    public final BooleanSetting colored = new BooleanSetting("Colored", true);
	    public final BooleanSetting textured = new BooleanSetting("textured", true);

	public Chams() {
		super("Chams", "See shit through walls", Category.RENDER);
		this.setInstance();
	}
	
	 public static Chams getInstance() {
	        if (INSTANCE == null) {
	            INSTANCE = new Chams();
	        }
	        return INSTANCE;
	    }

	    private void setInstance() {
	        INSTANCE = this;
	    }

}
