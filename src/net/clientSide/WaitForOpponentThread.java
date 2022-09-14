package net.clientSide;

import java.io.*;
import java.net.Socket;

public class WaitForOpponentThread extends Thread{
    private Socket socket;
    private OutputStream output;
    private InputStream input;
    private BufferedReader reader;
    private PrintWriter writer;
    private Client client;
    int numOpponents = 0;

    public WaitForOpponentThread(Socket socket, Client client){
        try {
            this.socket= socket;
            this.client = client;
            output = socket.getOutputStream();
            input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
            writer = new PrintWriter(output, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void run(){
        while(true){
            try{
                String response = reader.readLine();
                System.out.println("WaitForOpponentThread got response: " + response);
                numOpponents ++;
                if(numOpponents >= 2){
                    client.setEnoughToStart();
                }
                //the user has 9 opponents, game full
                if(response.equals("0:9")){
                    break;
                }
                //signal that the host started the game, I'm going to need to pass that back into host so that I can break this though
                else if(response.equals("S")){
                    //client.getWriter().println("HS");
                    //start reading for input
                    client.getReadThread().start();
                    break;
                }else if(response.equals("HS")){
                    //start reading for input
                    client.getReadThread().start();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
