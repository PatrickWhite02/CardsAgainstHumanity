package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VisibleHand extends HashMap<Integer, String> {
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
    public void add(String text){
        for(int i = 0; i < 10; i++){
            if(!this.containsKey(i)){
                this.put(i, text);
                break;
            }
        }
    }
}
