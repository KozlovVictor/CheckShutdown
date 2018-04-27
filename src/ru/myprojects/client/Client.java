package ru.myprojects.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    private final int PORT = 8189;
    private String serverAddress;
    private Socket socket;
    /*private DataInputStream in = null;
    private DataOutputStream out = null;
    private String serverMessage = null;*/

    public Client() {
        try {
            serverAddress = "localhost";
            socket = new Socket(serverAddress, PORT);
            System.out.println("Connected with " + serverAddress);
            while(true){
            }
            /*in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            while (true) {
                serverMessage = in.readUTF();
                if (serverMessage.equals("info")) {
                    getInfo();
                }
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Client(String serverAddress) {
        this.serverAddress = serverAddress;
        try {
            socket = new Socket(serverAddress, PORT);
            System.out.println("Connected with " + serverAddress);
            /*in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            while (true) {
                serverMessage = in.readUTF();
                if (serverMessage.equals("info")) {
                    getInfo();
                }
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*private void getInfo() {
        try {
            out.writeUTF(InetAddress.getLocalHost().getCanonicalHostName());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
