package ru.myprojects.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class Server {
    private static final long TIME_PERIOD = 2000;
    private static final long WAITING_ANSWER_TIME = 2000;
    private final int PORT = 8189;
    private final int TIME_DELAY = 0;

    private ServerSocket serverSocket;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Timer delay;
    private ArrayList<Socket> sockets;


    public Server() {
        sockets = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started! Wait a clients.");
            /*TimerTask sorting = new TimerTask() {
                @Override
                public void run() {
                    //System.out.println(Arrays.asList(sockets));
                    for (Socket element : sockets) {
                        try {
                            in = new DataInputStream(element.getInputStream());
                            out = new DataOutputStream(element.getOutputStream());
                            out.writeUTF("info");
                            out.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            delay.schedule(sorting, TIME_DELAY, TIME_DELAY);*/
            while (true) {
                socket = serverSocket.accept();
                String hostname = socket.getRemoteSocketAddress().toString();
                delay = new Timer();
                delay.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            in = new DataInputStream(socket.getInputStream());
                            out = new DataOutputStream(socket.getOutputStream());
                            String clientMessage = null;
                            out.writeUTF("info");
                            out.flush();
                            long startTime = System.currentTimeMillis();
                            while (System.currentTimeMillis() - startTime < WAITING_ANSWER_TIME) {
                                clientMessage = in.readUTF();
                                if (clientMessage != null){
                                    break;
                                }
                            }
                            System.out.println("message: " + clientMessage);
                        } catch (IOException e) {
                            disconnect(in, out, socket);
                            sockets.remove(socket);
                            System.out.println(Arrays.asList(sockets));
                            System.out.println("Client " + hostname + " disconnected!");
                            cancel();
                        }
                    }
                }, TIME_DELAY, TIME_PERIOD);
                sockets.add(socket);
                System.out.println(Arrays.asList(sockets));
                System.out.println("Client " + socket.getInetAddress().getHostName() + " connected successfully.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void disconnect(DataInputStream in, DataOutputStream out, Socket socket) {
        try {
            in.close();
            out.close();
            socket.close();
            System.out.println("Streams and socket are closed!");
        } catch (IOException e) {
            System.out.println("Error in closing streams and sockets!");
        }
    }
}
