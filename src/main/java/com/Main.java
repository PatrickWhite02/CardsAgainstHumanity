package com;

import net.clientSide.Client;

import javax.swing.*;
import java.io.IOException;
import java.util.Scanner;

public class Main extends JPanel {
    private static PlayersHand myHand;
    private static VisibleHand visibleHand;
    private static Deck deck;
    private static int whoTurn = 0;
    private static int myTurn = -1;
    private static Client client;
    private static int tag;
    public static void setMyTurn(int i){
        System.out.println("My turn: " + i);
        myTurn = i;
    }
    public static int getMyTurn(){
        return myTurn;
    }
    private static boolean isHost = false;

    public Main (){
        deck = new Deck();
        client = new Client(deck);
        if(isHost){
            createGame();
        }else{
            try {
                client.startWaitForOpponentThread();
                client.joinGame(tag);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(tag);
        myHand = new PlayersHand(deck);
        visibleHand = new VisibleHand(deck);
    }
    public void guestGame() throws IOException {
        client.joinGame(tag);
    }
    public void createGame(){
        System.out.println(client.createGame());
    }
    private static void sendMove(int i){
        client.sendMove(i);
    }
    public static void main(String[] args) {
        System.out.println("Join or host?");
        Scanner scanner = new Scanner(System.in);
        if(scanner.nextLine().equals("j")){
            tag =scanner.nextInt();
            Main main = new Main();
        }else{
            isHost = true;
            myTurn = 1;
            Main main = new Main();
            int message = Integer.parseInt(scanner.nextLine());
            String start = scanner.nextLine();
            System.out.println("Start");
            client.startGame();
            sendMove(message);
        }
        if(whoTurn == myTurn){
            visibleHand.generateBlackCard();
        }
        System.out.println("Your cards: ");
        for(Integer i : myHand.keySet()){
            System.out.println(i + " " + myHand.get(i));
        }
    }
}
