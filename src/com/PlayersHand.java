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
    public ArrayList<Card> getCards(){
        return this;
    }
}
