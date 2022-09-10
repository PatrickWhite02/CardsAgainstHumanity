package com;

import javax.swing.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class PlayersHand extends ArrayList<Card> {
    Deck deck = new Deck();
    public PlayersHand(){
        this.addAll(Arrays.asList(deck.drawFive()));
    }
    public void drawCard(){
        add(deck.drawOne());
    }
    public void discard(Card c){
        remove(c);
    }
    public ArrayList<Card> getCards(){
        return this;
    }
}
