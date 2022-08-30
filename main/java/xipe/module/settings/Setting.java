package xipe.module.settings;

import xipe.module.Mod;

public class Setting {

	protected String name;
	protected String type;
	protected Mod mod;
	private boolean visible = true;
	
	public Setting(String name) {
		this.name = name;
	}
	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public String getName() {
		return name;
	}
	
	public String getType(){
		return type;
	}

	public void setType(String t){
		this.type = t;
	}

	public void setMod(Mod mod) {
		this.mod = mod;
	}

}
