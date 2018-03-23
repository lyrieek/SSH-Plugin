package pers.th.idea;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

/**
 * Created by Tianhao on 2018-3-16.
 * {@link SSHConnect}
 */
public class SSHConnect {

    private Channel channel;
    private Session session;

    private InputStream input;
    private OutputStream output;

//    public static void main(String[] args) throws Exception {
//        SSHConnect connect = new SSHConnect();
//        connect.session("versionhk.asuscomm.com", "user", "version", 22);
//        connect.send("cal");
//        connect.send("ll");
//        connect.send("service tomcat8 restart");
//        connect.send("exit");
//        connect.close();
//    }

    public void authorization(String user) throws Exception {
        send("sudo chown " + user + " * -R");
        send("sudo chmod 777 * -R");
    }

    public String send(String cmd) throws Exception {
        output.write(cmd.concat("\n").getBytes());
        output.flush();
        byte[] buffer = new byte[1024];
        int length = -1;
        Thread.sleep(500);
        StringBuilder response = new StringBuilder();
        while (input.available() != 0 && (length = input.read(buffer)) != -1) {
            response.append(new String(buffer, 0, length, "utf-8"));
        }
        if (response.toString().trim().endsWith("[sudo] password for user:")
                || response.toString().trim().endsWith("Password:")) {
            send("version");
        }
        Notif.alert("Tomcat Restart",response.toString());
        return response.toString();
    }

    public void call() throws Exception {
        Scanner scanner = new Scanner(System.in);
        do {
            byte[] buffer = new byte[1024];
            int length = -1;
            Thread.sleep(300);
            StringBuffer response = new StringBuffer();
            while (input.available() != 0 && (length = input.read(buffer)) != -1) {
                response.append(new String(buffer, 0, length, "utf-8"));
            }
            System.out.print(response);
            if (response.toString().trim().endsWith("[sudo] password for user:")
                    || response.toString().trim().endsWith("Password:")) {
                output.write("version\n".getBytes());
            } else {
                output.write(scanner.nextLine().concat("\n").getBytes());
            }
            output.flush();
            Thread.sleep(300);
        } while (input.available() != 0);
        scanner.close();
    }

    public void close() throws IOException {
        session.disconnect();
        channel.disconnect();
        close(input);
        close(output);
    }

    public void close(Closeable closeable) throws IOException {
        if (closeable != null) {
            closeable.close();
        }
    }

    public void session(String host, String user, String pwd, int port) throws Exception {
        session = new JSch().getSession(user, host, port);
        session.setPassword(pwd);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect(30000);
        channel = session.openChannel("shell");
        channel.connect(1000);
        input = channel.getInputStream();
        output = channel.getOutputStream();
    }

}
