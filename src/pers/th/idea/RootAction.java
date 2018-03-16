package pers.th.idea;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

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
            Messages.showInfoMessage("Success", "Info");
//            Messages.showMessageDialog("Success", "Info", IconLoader.findIcon("/pers/th/idea/icons/key.png"));
        } catch (Exception ex){
            Messages.showErrorDialog("ex:"+ex.getMessage(), "Error");
        }
    }
}
