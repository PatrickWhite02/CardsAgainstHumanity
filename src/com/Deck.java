package com;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Deck{
    public Map<Integer, String> blackDraw = new HashMap<>();
    public Map<Integer, String> blackDiscard = new HashMap<>();
    public Map<Integer, String> currentlyHeld = new HashMap<>();
    public Map<Integer, String> whiteDraw = new HashMap<>();
    private Map<Integer, String> whiteDiscard = new HashMap<>();
    public Deck(){
        setUpLists();
    }
    public void setUpLists(){
        InputStream inputStream = Deck.class.getClassLoader().getResourceAsStream("res/cards/allCards.txt");
        String mass = new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining("\n"));
        String [] rawData = mass.split("\n");
        int blackCount = 0;
        int whiteCount = 0;
        for(String s : rawData){
            String [] split = s.split("`");
            //if it's a black card, add it to my blackMap. Make sure to include special instructions if present
            if(split[0].equals("Prompt")){
                if(split.length == 2){
                    blackDraw.put(blackCount, split[1]);
                }else{
                    blackDraw.put(blackCount, split[1] + split[2]);
                }
                blackCount++;
            }else{
                //if it's a white card, just add it to my white draw pile
                whiteDraw.put(whiteCount, split[1]);
                whiteCount++;
            }
        }
    }
    public String[] drawFiveWhite(){
        String[] r = new String[5];
        for(int i = 0; i < 5; i++){
            //random index in our map
            int randomIndex = (int) (Math.random() * whiteDraw.size());
            //add this object to our return array
            r[i] = whiteDraw.get(randomIndex);
            //move the card into the "Currently held" pile
            currentlyHeld.put(randomIndex, whiteDraw.get(randomIndex));
            whiteDraw.remove(randomIndex);
        }
        if(whiteDraw.size() <= 5){
            shuffleWhite();
        }
        return r;
    }
    public String drawOneWhite(){
        int randomIndex = (int) (Math.random() * whiteDraw.size());
        //move the card to discard pile
        currentlyHeld.put(randomIndex, whiteDraw.get(randomIndex));
        whiteDraw.remove(randomIndex);
        //reshuffle if the deck ever dips below 5
        if(whiteDraw.size() <= 5){
            shuffleWhite();
        }
        //return card
        return(currentlyHeld.get(randomIndex));
    }
    public void discardShowing(){
        whiteDiscard.putAll(currentlyHeld);
        currentlyHeld.clear();
    }
    public void shuffleWhite(){
        //move all the discard cards back into the draw pile
        whiteDraw.putAll(whiteDiscard);
        whiteDiscard.clear();
    }
    public String drawBlackCard(){
        int randomIndex  = (int) (Math.random() * blackDraw.size());
        blackDiscard.put(randomIndex, blackDraw.get(randomIndex));
        blackDraw.remove(randomIndex);
        if(blackDraw.size() <= 5){
            shuffleBlack();
        }
        return blackDraw.get(randomIndex);
    }
    public void shuffleBlack(){
        blackDraw.putAll(blackDiscard);
        blackDiscard.clear();
    }
}
