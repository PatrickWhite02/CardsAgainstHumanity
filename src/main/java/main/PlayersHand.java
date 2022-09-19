package main;

import java.util.HashMap;

public class PlayersHand extends HashMap<Integer, String> {
    Deck deck;

    public PlayersHand(Deck deck){
        this.deck = deck;
        this.putAll(deck.drawTenWhite());
    }
    public void drawCard(){
        this.putAll(deck.drawOneWhite());
    }
    public HashMap<Integer, String> getCards(){
        return this;
    }
}
