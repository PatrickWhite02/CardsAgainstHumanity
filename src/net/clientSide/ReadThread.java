package net.clientSide;

import common.Deck;

import java.io.*;
import java.net.Socket;

public class ReadThread extends Thread{
    private Deck deck;
    Socket socket;
    OutputStream output;
    InputStream input;
    BufferedReader reader;
    PrintWriter writer;
    public ReadThread(Deck deck, Socket socket){
        try {
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
                int response = Integer.parseInt(reader.readLine());
                System.out.println(response);
                deck.opponentTookWhiteCard(response);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
