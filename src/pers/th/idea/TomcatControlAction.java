package pers.th.idea;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

/**
 * Created by Tianhao on 2018-3-20.
 * Tomcat server
 */
public class TomcatControlAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        try {
            SSHConnect connect = new SSHConnect();
            connect.session("192.168.2.175", "user", "version", 22);
            connect.send("sudo service tomcat8 restart");
            connect.send("exit");
            connect.close();
            Notification notification = new Notification("pers.th.idea.sshRoot", "Tomcat Restart", "Success", NotificationType.INFORMATION);
            Notifications.Bus.notify(notification);
            if (notification.getBalloon() != null) notification.getBalloon().hide();
        } catch (Exception ex) {
            Notification notification = new Notification("pers.th.idea.sshRoot", "Tomcat Restart", "ex:" + ex.getMessage(), NotificationType.ERROR);
            Notifications.Bus.notify(notification);
            if (notification.getBalloon() != null) notification.getBalloon().hide();
        }
    }
}
