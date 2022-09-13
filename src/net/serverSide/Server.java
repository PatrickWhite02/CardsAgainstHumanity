package net.serverSide;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {

    private HashMap <Integer, ArrayList<UserThread>> allUsersHashMap = new HashMap<>();
    public HashMap<Integer, ArrayList<UserThread>> getAllUsersHashMap() {
        return allUsersHashMap;
    }

    public void execute(){
        try {
            ServerSocket serverSocket = new ServerSocket(8282);
            while(true){
                Socket socket = null;
                try{
                    socket = serverSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }UserThread userThread = new UserThread(socket, this);
                userThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Server server = new Server();
        server.execute();
    }
}
