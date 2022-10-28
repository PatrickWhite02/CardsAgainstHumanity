package main;

import net.clientSide.Client;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main extends JPanel {
    public static PlayersHand getMyHand() {
        return myHand;
    }

    private static PlayersHand myHand;

    public static VisibleHand getVisibleHand() {
        return visibleHand;
    }

    private static Deck deck = new Deck();
    private static VisibleHand visibleHand = new VisibleHand(deck);

    public static int[] getScoreBoard() {
        return scoreBoard;
    }

    private static int [] scoreBoard;
    public static void increaseScore(int whoWon){
        scoreBoard[whoWon]++;
    }
    public static int getWhoTurn() {
        return whoTurn;
    }

    private static int whoTurn = 0;
    public static void increaseWhoTurn(){
        whoTurn++;
        if(whoTurn == maxTurn + 1){
            whoTurn = 0;
        }
    }
    private static int myTurn = 0;
    private static HashMap<Integer, String> newCard;
    public static int getMaxTurn() {
        return maxTurn;
    }

    public static void setMaxTurn(int maxTurn) {
        Main.maxTurn = maxTurn;
    }

    private static int maxTurn = 0;
    private static Client client;
    private static int tag;

    public static void setMyTurn(int i) {
        System.out.println("My turn: " + i);
        myTurn = i;
    }

    public static int getMyTurn() {
        return myTurn;
    }

    private static boolean isHost = false;

    public Main() {
        deck = new Deck();
        client = new Client(deck);
        if (isHost) {
            createGame();
        } else {
            try {
                client.startWaitForOpponentThread();
                client.joinGame(tag);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(tag);
    }

    public void guestGame() throws IOException {
        client.joinGame(tag);
    }
    public static void drawTenWhite(){
        System.out.println("WhoTurn = " + whoTurn);
        myHand = new PlayersHand(deck);
        for(int i : myHand.keySet()){
            client.sendTookCard(i);
            System.out.println(i + " " + myHand.get(i));
        }
        System.out.println("Main is sending D");
        client.sendTurnDone();
        increaseWhoTurn();
        System.out.println("myTurn: " + myTurn);
        System.out.println("maxTurn:" + maxTurn);
        if(myTurn == maxTurn){
            client.sendAllPlayersDealt();
        }
    }
    public static void takeBlackCard(){
        int prompt = deck.drawBlackCard();
        System.out.println("prompt: " + prompt + " " + deck.getBlackCard(prompt));
        client.sendBlackDrawn(prompt);
    }
    public static void allPlayersIn(){
        System.out.println("All PLayers in!");
        scoreBoard = new int[maxTurn + 1];
        for(int i = 0; i < scoreBoard.length; i++){
            scoreBoard[i] = 0;
        }
    }
    public void takeTurn(){
        visibleHand.clear();
    }
    public static void askUserToPick(int i){
        visibleHand.clear();
        System.out.println("black card: " + deck.getBlackCard(i));
        for(int c : myHand.keySet()){
            System.out.println(c + " " + myHand.get(c));
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number you want to play");
        int selection = scanner.nextInt();
        System.out.println("Confirmed");
        //remove the card they played from their hand
        myHand.remove(selection);
        //draw a new one in its place
        newCard = deck.drawOneWhite();
        //add that card to the hand
        myHand.putAll(newCard);
        //transmit that integer value. Not the most efficient way of accessing the int value, but it works
        for(int j : newCard.keySet()){
            client.sendTookCard(j);
        }
        visibleHand.put(myTurn, myHand.get(selection));
        client.sendMove(selection);
    }
    public void createGame() {
        System.out.println(client.createGame());
    }
    public static void pickWinner(){
        System.out.println("Pick a winner from the following submissions: ");
        for(int i : visibleHand.keySet()){
            System.out.println(i + " " + visibleHand.get(i));
        }
        Scanner scanner = new Scanner(System.in);
        int winner = scanner.nextInt();
        client.sendWinner(winner);
        increaseWhoTurn();
        clearVisibleHand();
        increaseScore(winner);
    }
    public static void clearVisibleHand(){
        visibleHand.clear();
    }
    public static void main(String[] args) throws IOException {
        System.out.println("Join or host?");
        Scanner scanner = new Scanner(System.in);
        client = new Client(deck);
        if(scanner.nextLine().equals("h")){
            int tag = client.createGame();
            System.out.println("tag = " + tag);
            client.startWaitForOpponentThread();
            String s = scanner.nextLine();
            client.startGame();
        }
        else{
            int tag = scanner.nextInt();
            client.joinGame(tag);
        }
    }
}