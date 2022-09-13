package com;

import java.util.ArrayList;
import java.util.Arrays;

public class PlayersHand extends ArrayList<String> {
    Deck deck = new Deck();

    public PlayersHand(){
        this.addAll(Arrays.asList(deck.drawFiveWhite()));
    }
    public void drawCard(){
        String c = deck.drawOneWhite();
        add(c);
    }
    public void discard(Card c){
        remove(c);
    }
    public ArrayList<String> getCards(){
        return this;
    }
}
