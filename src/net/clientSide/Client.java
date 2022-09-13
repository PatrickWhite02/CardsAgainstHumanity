package net.clientSide;

import java.io.*;
import java.net.Socket;

public class Client {
    private String host = "localhost";
    private int port = 8282;
    private Socket socket;
    private OutputStream output;
    private InputStream input;
    private BufferedReader reader;
    private PrintWriter writer;

    public Client(){
        try {
            socket = new Socket(host, port);
            output = socket.getOutputStream();
            input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
            writer = new PrintWriter(output, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public int hostGame(){
        return generateKey();
    }
    private int generateKey(){
        for(int i = 1000; i < 10000; i++){
            writer.println(i);
            try {
                //error code for key is taken
                if(!reader.readLine().equals("0")){
                    return i;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //I don't think I'll ever have this many people playing lol
        return -1;
    }
    public void joinGame(){

    }
}
