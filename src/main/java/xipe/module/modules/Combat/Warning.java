package xipe.module.modules.Combat;

import xipe.module.Mod;
import xipe.module.ModuleManager;
import xipe.module.modules.hud.Notifications;
import xipe.module.settings.BooleanSetting;
import xipe.module.settings.NumberSetting;
import xipe.utils.player.Notification;
import xipe.utils.player.NotificationUtil;

public class Warning extends Mod{
	
	public BooleanSetting armor = new BooleanSetting("Armor", true);
	public BooleanSetting health = new BooleanSetting("Health", true);
	
	public NumberSetting healthNum = new NumberSetting("Health Percent", 1,36, 10,1);
	public NumberSetting armorNum = new NumberSetting("Armor Percent", 1,100, 10,1);

	public Warning() {
		super("Warning", "Warns you about stuff (Has to have notifications enabled)", Category.COMBAT);
		addSettings(armor,health,healthNum);
	}
	
	@Override
	public void onTick() {
		if(ModuleManager.INSTANCE.getModule(Notifications.class).isEnabled()) {
			if(health.isEnabled()) {
				if(mc.player.getHealth() <= healthNum.getValue()) {
					NotificationUtil.send_notification(new Notification("Health is low", 250, 253, 15));
					NotificationUtil.update();
				}
			}
			if(armor.isEnabled()) {
			
			}
		}
		super.onTick();
	}

}
