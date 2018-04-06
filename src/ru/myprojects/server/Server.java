package ru.myprojects.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class Server {
    private final int PORT = 8189;
    private ServerSocket serverSocket = null;
    private Socket socket = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;
    private Timer delay;

    public Server() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started! Wait a clients.");
            socket = serverSocket.accept();
            System.out.println("Client connected succesfully.");
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            delay = new Timer();
            delay.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        out.writeUTF("info");
                        out.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            },1000, 2000);
            new Thread(()->{
                while (true) {
                    String clientMessage = null;
                    try {
                        clientMessage = in.readUTF();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println(clientMessage);
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
