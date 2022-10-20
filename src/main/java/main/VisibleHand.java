package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VisibleHand extends HashMap<Integer, String> {
    //Integer = Player Tag
    //String = Card Text
    private Deck deck;


    private int blackCardIndex;
    private String blackCardText;
    public VisibleHand(Deck deck){
        this.deck = deck;
    }
    public void setBlackCard(int index, String text){
        blackCardIndex = index;
        blackCardText = text;
    }
}
