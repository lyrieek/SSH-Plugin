package pers.th.idea;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

/**
 * Created by Tianhao on 2018-3-16.
 * {@link RootAction}
 */
public class RootAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        try {
            SSHConnect connect = new SSHConnect();
            connect.session("192.168.2.175", "user", "version", 22);
            connect.send("ll");
            connect.send("cd /var/lib/tomcat8/webapps/EmrApplication/WEB-INF");
            connect.send("sudo chown user -R ./");
            connect.send("sudo chmod 777 -R ./");
            connect.send("exit");
            connect.close();
            Notification notification = new Notification("pers.th.idea.sshRoot", "SSH Root", "Success", NotificationType.INFORMATION);
            Notifications.Bus.notify(notification);
            if (notification.getBalloon() != null) notification.getBalloon().hide();
//            Messages.showMessageDialog("Success", "Info", IconLoader.findIcon("/pers/th/idea/icons/key.png"));
        } catch (Exception ex) {
            Notification notification = new Notification("pers.th.idea.sshRoot", "SSH Root", "ex:" + ex.getMessage(), NotificationType.ERROR);
            Notifications.Bus.notify(notification);
            if (notification.getBalloon() != null) notification.getBalloon().hide();
        }
    }
}
