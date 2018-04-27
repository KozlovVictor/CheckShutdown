package ru.myprojects.client;

public class Main {
    public static void main(String[] args) {
        if (args.length != 0)new Client(args[0]);
        else new Client();
    }
}
