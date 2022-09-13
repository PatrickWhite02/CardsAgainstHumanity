package com;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class Main extends JPanel {
    private static PlayersHand myHand;
    private static VisibleHand visibleHand;
    private static Deck deck;
    private static int whoTurn = 0;
    private static int myTurn = 0;
    public Main (){
        deck = new Deck();
        myHand = new PlayersHand(deck);
        visibleHand = new VisibleHand(deck);
    }
    public static void main(String[] args) {
        Main main = new Main();
        if(whoTurn == myTurn){
            visibleHand.generateBlackCard();
        }else{
            System.out.println("Your cards: ");
            for(Integer i : myHand.keySet()){
                System.out.println(i + " " + myHand.get(i));
            }
        }
    }
}
