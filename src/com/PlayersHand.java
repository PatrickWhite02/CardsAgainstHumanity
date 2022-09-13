package com;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PlayersHand extends HashMap<Integer, String> {
    Deck deck = new Deck();

    public PlayersHand(){
        this.putAll(deck.drawTenWhite());
    }
    public void drawCard(){
        this.putAll(deck.drawOneWhite());
    }
    public void discard(Card c){
        remove(c);
    }
    public HashMap<Integer, String> getCards(){
        return this;
    }
}
