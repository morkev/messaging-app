package network;

import application.Main;
import application.Message;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ServerHandler extends Listener {
    static Server server;
    static int udpPort = 54555, tcpPort = 54777;
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

    public static void start(){
        try {
            server = new Server();
            Register.register(server.getKryo());
            server.bind(tcpPort, udpPort);
            server.start();
            server.addListener(new ServerHandler());
            Main.g.taChat.append("["+ LocalTime.now().format(dtf) + " Connected as Host]\n");
            Main.isConnected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void send(Object o){
        if(o instanceof Message){
            Message m = (Message) o;
            Main.g.taChat.append(m.getMessage());
            server.sendToAllTCP(m);
        }

    }

    public void connected(Connection c){
        Main.g.taChat.append("["+c.getRemoteAddressTCP().getHostString()+ " Connected]\n");
    }

    public void received(Connection c, Object o){
        if(o instanceof Message){
            Message m = (Message) o;
            Main.g.taChat.append(m.getMessage());
            server.sendToAllExceptTCP(c.getID(), m);
        }
    }

    public void disconnected(Connection c){
        Main.g.taChat.append("[Connection "+c.getID() + " Disconnected]\n");
    }

    public static void close(){
        Main.g.taChat.append("[Disconnected]\n");
    }

}
