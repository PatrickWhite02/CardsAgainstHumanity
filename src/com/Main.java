package com;

import javax.swing.*;
import java.util.Arrays;

public class Main extends JPanel {
    public static void main(String[] args) {
        PlayersHand playersHand = new PlayersHand();
        for(Card c: playersHand.getCards()){
            System.out.println(c.getCard());
        }
    }
}
