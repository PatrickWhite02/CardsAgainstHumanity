package com;

import vis.VisCard;

import javax.swing.*;
import java.awt.*;

public class Main extends JPanel {
    VisCard [] visCards = new VisCard[10];
    PlayersHand playersHand = new PlayersHand();
    public Main (){
        this.setLayout(new GridLayout(10, 2));
        int i =0;
        for(Card c : playersHand){
            visCards[i] = new VisCard(c);
            add(visCards[i]);
            i++;
        }
    }
    public static void buildHand(){
    }
    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(new Main());
        f.setBounds(500, 500, 500, 500);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
