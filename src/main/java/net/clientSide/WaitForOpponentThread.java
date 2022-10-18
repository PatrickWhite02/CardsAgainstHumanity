package net.clientSide;

import main.Main;

import java.io.*;
import java.net.Socket;

public class WaitForOpponentThread extends Thread{
    private Socket socket;
    private OutputStream output;
    private InputStream input;
    private BufferedReader reader;
    private PrintWriter writer;
    private Client client;
    int numOpponents = 1;
    boolean keepRunning;
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
                System.out.println("WaitThread waiting for response");
                String response = reader.readLine();
                System.out.println("WaitForOpponentThread got response: " + response);
                if(response.length() == 3){
                    numOpponents = Integer.parseInt(response.substring(2));
                    Main.setMaxTurn(numOpponents);
                    System.out.println(numOpponents);
                    if(Main.getMyTurn() == 1 && !client.isHost()){
                        Main.setMyTurn(numOpponents);
                    }
                    if(numOpponents >= 2){
                        client.setEnoughToStart();
                    }
                }
                //the user has 9 opponents, game full
                if(response.equals("0:10") || response.equals("S") || response.equals("HS")){
                    client.getReadThread().start();
                    break;
                }
                //signal that the host started the game, I'm going to need to pass that back into host so that I can break this though
                //client.getWriter().println("HS");
                //start reading for input
                //start reading for input
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
