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
                } else if (response.contains("w: ")){
                    int winningPlayer = Integer.parseInt(response.substring(3));
                    System.out.println("Winning card: " + Main.getVisibleHand().get(winningPlayer) + ", Played by: " + winningPlayer);
                    Main.increaseScore(winningPlayer);
                }
                else if (!response.equals("S")){
                    System.out.println("Received play: " + response);
                    int i = Integer.parseInt(response.substring(3));
                    System.out.println("Player number : " + response.substring(0,1));
                    Main.getVisibleHand().put(Integer.valueOf(response.substring(0,1)), deck.getWhiteCard(i));
                    //if it's our turn and all cards are in, then prompt them to pick a winner
                    if((Main.getVisibleHand().size() == Main.getMaxTurn()) && (Main.getMyTurn() == Main.getWhoTurn())){
                        Main.pickWinner();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
