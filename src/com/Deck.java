package com;

import init.CardPuller;
import java.util.ArrayList;

public class Deck extends ArrayList<Card> {
    public ArrayList<Card> blackCards = new ArrayList<>();
    public ArrayList<Card> whiteCards = new ArrayList<>();
    public Deck(){
        for(Card c : CardPuller.getCards()){
            if(c.getBlackCard()){
                blackCards.add(c);
            }else{
                whiteCards.add(c);
            }
        }
    }
    public Card[] drawFive(){
        Card [] r = new Card[5];
        for(int i = 0; i < 5; i++){
            //will generate a random index of this list
            int index = (int) (Math.random() * whiteCards.size());
            r[i] = whiteCards.get(index);
            //remove it from the deck so we don't end up with multiple people with the same cards
            whiteCards.remove(index);
        }
        return r;
    }
    public Card drawOne(){
        int index = (int) (Math.random() * whiteCards.size());
        Card r = whiteCards.get(index);
        System.out.println("Selected card: " + r.getCard());
        whiteCards.remove(index);
        return r;
    }
}
