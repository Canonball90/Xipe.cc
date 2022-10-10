package xipe.module.modules.hud;

import xipe.module.Mod;
import xipe.module.settings.BooleanSetting;
import xipe.module.settings.ModeSetting;
import xipe.module.settings.NumberSetting;
import xipe.utils.player.Notification;
import xipe.utils.player.NotificationUtil;

public class Notifications  extends Mod{
	
	public NumberSetting y = new NumberSetting("Y", -555,555,80,0.1);
	public NumberSetting maxNotif = new NumberSetting("Max Notifications", 1,7,7,1);
	public BooleanSetting background = new BooleanSetting("BackGround", true);
	public ModeSetting animationMode = new ModeSetting("Animation", "Y", "Y", "X", "End Slide");
	
	public Notifications() {
		super("Notifications", "Shows notifications", Category.HUD);
		addSettings(y,maxNotif,background,animationMode);
	}

	@Override
	public void onEnable() {
		NotificationUtil.send_notification(new Notification("Some modules require this to be enabled", 250, 253, 15));
		NotificationUtil.send_notification(new Notification("This is enable", 0, 255, 0));
		NotificationUtil.send_notification(new Notification("This is disable", 255, 0, 0));
		NotificationUtil.send_notification(new Notification("This is warning", 250, 253, 15));
		super.onEnable();
	}
}
