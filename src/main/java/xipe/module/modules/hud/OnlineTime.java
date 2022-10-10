package xipe.module.modules.hud;

import xipe.Client;
import xipe.module.Mod;
import xipe.module.settings.NumberSetting;

public class OnlineTime extends Mod{
	
	 private long timeOnline;
	
	public NumberSetting x = new NumberSetting("X", -555,555,160,0.1);
	public NumberSetting y = new NumberSetting("Y", -555,555,160,0.1);

	public OnlineTime() {
		super("Online Time", "Shows how long you have been online", Category.HUD);
		addSettings(x,y);
	}
	
	
	 public void reset() {
	        timeOnline = 0;
	    }

	    public void start() {
	        Client.EventBus.register(this);
	        timeOnline = System.currentTimeMillis();
	    }
	
	
	 public long getTimeOnline() {
	        return (System.currentTimeMillis() - timeOnline) / 1000;
	    }

	    public String convertTime(long time) {
	        return String.format("%s hours, %s minutes, %s seconds", time / 3600, (time - (time / 3600 * 3600) - (time % 60)) / 60, time % 60);
	    }


}
