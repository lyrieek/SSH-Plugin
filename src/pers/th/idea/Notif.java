package pers.th.idea;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;

/**
 * Created by Tianhao on 2018-3-23.
 * {@link Notif}
 */
public class Notif {

    public static void alert(String title,String content){
        Notification notification = new Notification("pers.th.idea.sshRoot", title, content, NotificationType.INFORMATION);
        Notifications.Bus.notify(notification);
    }

    public static void error(String title,String content){
        Notification notification = new Notification("pers.th.idea.sshRoot", title, content, NotificationType.ERROR);
        Notifications.Bus.notify(notification);
    }
}
