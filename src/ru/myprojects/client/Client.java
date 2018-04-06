package ru.myprojects.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class Client {

    private final int PORT = 8189;
    private final String SERVER_ADDRESS = "localhost";
    private Socket socket = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;
    private String serverMessage = null;

    public Client() {
        try {
            socket = new Socket(SERVER_ADDRESS, PORT);
            System.out.println("Connected with " + SERVER_ADDRESS);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            while (true) {
                serverMessage = in.readUTF();
                if (serverMessage.equals("info")) {
                    getInfo();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getInfo() {
        try {
            out.writeUTF(InetAddress.getLocalHost().getCanonicalHostName());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
