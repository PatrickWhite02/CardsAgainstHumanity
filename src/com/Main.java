package com;

import javax.swing.*;
import java.awt.*;

public class Main extends JPanel {
    PlayersHand playersHand = new PlayersHand();
    public Main (){
        this.setLayout(new GridLayout(10, 2));
        int i =0;
        for(String c : playersHand){
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
