package ru.myprojects.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class Server {
    private final int PORT = 8189;
    private final int TIME_DELAY = 5000;

    private ServerSocket serverSocket;
    private Socket socket;
    /*private DataInputStream in = null;
    private DataOutputStream out = null;*/
    private Timer delay;
    private ArrayList<Socket> sockets;


    public Server() {
        sockets = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started! Wait a clients.");
            delay = new Timer();
            TimerTask sorting = new TimerTask() {
                @Override
                public void run() {
                    System.out.println(Arrays.asList(sockets));
                    //if (sockets.get(0).isConnected()) System.out.println(true);
                    for (Socket element : sockets) {
                        if (element.isBound()) {
                            System.out.println(element.getInetAddress().getHostName());
                        } else {
                            sockets.remove(element);
                        }
                        /*if (!element.isConnected()){
                            System.out.println("Client " + socket.getInetAddress().getHostName() + " disconnected!");
                            sockets.remove(element);
                        }*/
                    }
                }
            };
            delay.schedule(sorting, TIME_DELAY, TIME_DELAY);
            while (true) {
                socket = serverSocket.accept();
                sockets.add(socket);
                System.out.println("Client " + socket.getInetAddress().getHostName() + " connected successfully.");
            }

            /*delay.schedule(new TimerTask() {
                @Override
                public void run() {

                }
            }, TIME_DELAY, TIME_DELAY);*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*private void disconnect(DataInputStream in, DataOutputStream out, Socket socket) {
        try {
            in.close();
            out.close();
            socket.close();
            System.out.println("Streams and socket are closed!");
        } catch (IOException e) {
            System.out.println("Error in closing streams and sockets!");
        }
    }*/
}
