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
                    System.out.println("APD");
                    if(Main.getMyTurn() == Main.getWhoTurn()){
                        Main.takeBlackCard();
                    }
                }
                else if(response.equals("D")){
                    Main.increaseWhoTurn();
                    if(Main.getMyHand() == null){
                        System.out.println("HIT");
                        if(Main.getMyTurn() == Main.getWhoTurn()){
                            Main.drawTenWhite();
                        }
                        if(Main.getMyTurn() == Main.getMaxTurn()){
                            System.out.println("Everyone has Cards now");
                        }
                    }
                }else if(response.contains("bd:")){
                    int card = Integer.parseInt(response.substring(4));
                    deck.opponentTookBlackCard(card);
                    Main.getVisibleHand().setBlackCard(card, deck.getBlackCard(card));
                    Main.askUserToPick(card);
                }else if (!response.equals("S")){
                    int i = Integer.parseInt(response.substring(2));
                    deck.opponentTookWhiteCard(i);
                    Main.getVisibleHand().put(i, deck.getWhiteCard(i));
                    //
                    if(Main.getVisibleHand().size() == Main.getMaxTurn() -1){

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
