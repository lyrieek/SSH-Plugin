package pers.th.idea;

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
            Notif.alert("Tomcat Restart", "Success");
        } catch (Exception ex) {
            Notif.error("Tomcat Restart Error", ex.getMessage());
        }
    }
}
