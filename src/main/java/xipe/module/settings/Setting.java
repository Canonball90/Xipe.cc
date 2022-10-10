package xipe.module.settings;

import xipe.module.Mod;

public class Setting {

	public String name;
	protected String type;
	protected Mod mod;
	private boolean visible = true;
	protected int x;
	protected int y;
	protected int y2;
	protected int x2;
	
	 public void setPos(int x, int y) {
	        this.x = x;
	        this.y = y;
	    }

	    public void setSliderPos(int x, int y, int x2, int y2) {
	        this.x = x;
	        this.y = y;
	        this.x2 = x2;
	        this.y2 = y2;
	    }

	    public int getX() {
	        return this.x;
	    }

	    public int getY2() {
	        return this.y2;
	    }

	    public int getX2() {
	        return this.x2;
	    }

	    public int getY() {
	        return this.y;
	    }

	
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
	
	public Mod getMod() {
        return this.mod;
    }

	public void setMod(Mod mod) {
		this.mod = mod;
	}

}
