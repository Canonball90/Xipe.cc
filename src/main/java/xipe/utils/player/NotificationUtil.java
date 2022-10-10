package xipe.utils.player;

import java.util.ArrayList;

import xipe.module.ModuleManager;
import xipe.module.modules.hud.Notifications;

public class NotificationUtil {

    private static final ArrayList<Notification> active_notifications = new ArrayList<>();

    public static void send_notification (Notification message) {
        active_notifications.add(0, message);
    }

    public static ArrayList<Notification> get_notifications () {
        return active_notifications;
    }

    public static void update () {
        active_notifications.removeIf(notification -> (System.currentTimeMillis() - notification.getTimeCreated()) > 5000);

        int maxnotifications = ModuleManager.INSTANCE.getModule(Notifications.class).maxNotif.getValueInt();
        if (maxnotifications < active_notifications.size()) {
            active_notifications.remove(maxnotifications - 1);
        }
    }
}
