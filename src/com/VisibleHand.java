package com;

import java.util.HashMap;

public class VisibleHand extends HashMap<Integer, String> {
    private Deck deck;

    public String getBlackCard() {
        return blackCard;
    }

    private String blackCard;
    public VisibleHand(Deck deck){
        this.deck = deck;
    }
    public void sendWhiteSelection(Integer integer, String string){
        put(integer, string);
    }
    public void receiveWhiteSelection(Integer integer, String string){
        put(integer, string);
    }
    public void generateBlackCard(){
        clear();
        putAll(deck.drawBlackCard());
        for(Integer i : this.keySet()){
            blackCard = this.get(i);
        }
    }
}
