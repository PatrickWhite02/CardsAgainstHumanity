package net.clientSide;

import main.Deck;
import main.Main;

import java.io.*;
import java.net.Socket;

public class ReadThread extends Thread{
    private Client client;
    private Deck deck;
    Socket socket;
    OutputStream output;
    InputStream input;
    BufferedReader reader;
    PrintWriter writer;
    public ReadThread(Client client, Deck deck, Socket socket){
        try {
            this.client = client;
            this.socket= socket;
            this.deck = deck;
            output = socket.getOutputStream();
            input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
            writer = new PrintWriter(output, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void run(){
        System.out.println("ReadThread started");
        while(true){
            try {
                String response = reader.readLine();
                System.out.println("ReadThread got: " + response);
                if(response.contains("tk:")){
                    int card = Integer.parseInt(response.substring(4));
                    System.out.println("card = " + card);
                    //weird way to convert a char to int
                    int whoDrew = response.charAt(0) - '0';
                    System.out.println("whoDrew = " + whoDrew);
                    deck.opponentTookWhiteCard(card);
                }else if(response.equals("APD")){

                }
                else if(response.equals("D")){
                    Main.increaseWhoTurn();
                    if(Main.getMyHand() == null){
                        System.out.println("HIT");
                        if(Main.getMyTurn() == Main.getWhoTurn()){
                            Main.drawTenWhite();
                            System.out.println("ReadThread sending: D");
                            client.sendTurnDone();
                        }
                        if(Main.getMyTurn() == Main.getMaxTurn()){
                            client.sendAllPlayersDealt();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
