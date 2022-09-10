package com;

import vis.VisCard;

import javax.swing.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class PlayersHand extends ArrayList<Card> {
    Deck deck = new Deck();

    public ArrayList<VisCard> getVisCards() {
        return visCards;
    }

    ArrayList<VisCard> visCards = new ArrayList<>();
    public PlayersHand(){
        this.addAll(Arrays.asList(deck.drawFive()));
        for(Card c : this){
            visCards.add(new VisCard(c));
        }
    }
    public void drawCard(){
        Card c = deck.drawOne();
        add(c);
        visCards.add(new VisCard(c));
    }
    public void discard(Card c){
        remove(c);
        visCards.remove(this.indexOf(c));
    }
    public ArrayList<Card> getCards(){
        return this;
    }
}
