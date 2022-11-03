package main;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Deck{
    public String getBlackCard(int i) {
        if(blackDraw.containsKey(i)){
            return blackDraw.get(i);
        }return currentBlackCard.get(i);
    }
    public String getWhiteCard(int i){
        if(whiteDraw.containsKey(i)){
            return whiteDraw.get(i);
        }return currentlyHeld.get(i);
    }
    public Map<Integer, String> blackDraw = new HashMap<>();
    public ArrayList<Integer> blackKeysAvailable = new ArrayList<>();
    public ArrayList<Integer> blackKeysUnavailable = new ArrayList<>();

    public Map<Integer, String> blackDiscard = new HashMap<>();
    public Map<Integer, String> currentlyHeld = new HashMap<>();
    public Map<Integer, String> whiteDraw = new HashMap<>();
    public ArrayList<Integer> whiteKeysAvailable = new ArrayList<>();
    public ArrayList<Integer> whiteKeysUnavailable = new ArrayList<>();
    public Map <Integer, String> currentBlackCard = new HashMap<>();

    private Map<Integer, String> whiteDiscard = new HashMap<>();
    public Deck(){
        setUpLists();
    }
    public void setUpLists(){
        InputStream inputStream = Deck.class.getClassLoader().getResourceAsStream("allCards.txt");
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
                    blackDraw.put(blackCount, split[1] + "`" + split[2]);
                }
                blackKeysAvailable.add(blackCount);
                blackCount++;
            }else{
                //if it's a white card, just add it to my white draw pile
                whiteDraw.put(whiteCount, split[1]);
                whiteKeysAvailable.add(whiteCount);
                whiteCount++;
            }
        }
    }
    public HashMap<Integer, String> drawTenWhite(){
        HashMap<Integer, String> r = new HashMap<>();
        for(int i = 0; i < 10; i++){
            //random index in our keys available
            int randomIndex = (int) (Math.random() * whiteKeysAvailable.size());
            //set our random index to that entry from our keys available
            randomIndex = whiteKeysAvailable.get(randomIndex);
            //add this object to our return map
            r.put(randomIndex, whiteDraw.get(randomIndex));
            //move the card into the "Currently held" pile
            whiteKeysUnavailable.add(randomIndex);
            currentlyHeld.put(randomIndex, whiteDraw.get(randomIndex));
            int finalRandomIndex = randomIndex;
            whiteKeysAvailable.removeIf(t -> t == finalRandomIndex);
            whiteDraw.remove(randomIndex);
        }
        if(whiteDraw.size() <= 10){
            shuffleWhite();
        }
        return r;
    }
    public HashMap<Integer, String> drawOneWhite(){
        HashMap<Integer, String> r = new HashMap<>();
        int randomIndex = (int) (Math.random() * whiteDraw.size());
        randomIndex = whiteKeysAvailable.get(randomIndex);
        //move the card to discard pile
        whiteKeysUnavailable.add(randomIndex);
        whiteKeysAvailable.remove(randomIndex);
        currentlyHeld.put(randomIndex, whiteDraw.get(randomIndex));
        whiteDraw.remove(randomIndex);
        //reshuffle if the deck ever dips below 10
        if(whiteDraw.size() <= 10){
            shuffleWhite();
        }
        //return card
        r.put(randomIndex, currentlyHeld.get(randomIndex));
        return r;
    }
    public void discardShowing(){
        whiteDiscard.putAll(currentlyHeld);
        currentlyHeld.clear();
    }
    public void shuffleWhite(){
        //move all the discard cards back into the draw pile
        whiteDraw.putAll(whiteDiscard);
        whiteDiscard.clear();
        whiteKeysAvailable.addAll(whiteKeysUnavailable);
        whiteKeysUnavailable.clear();
    }
    public int drawBlackCard(){
        int randomIndex  = (int) (Math.random() * blackDraw.size());
        randomIndex = blackKeysAvailable.get(randomIndex);
        blackKeysUnavailable.add(randomIndex);
        blackKeysAvailable.remove(randomIndex);
        currentBlackCard.put(randomIndex, blackDraw.get(randomIndex));
        blackDraw.remove(randomIndex);
        if(blackDraw.size() <= 5){
            shuffleBlack();
        }
        return randomIndex;
    }
    public void shuffleBlack(){
        blackDraw.putAll(blackDiscard);
        blackDiscard.clear();
        blackKeysAvailable.addAll(blackKeysUnavailable);
        blackKeysUnavailable.clear();
    }
    public void opponentTookWhiteCard(int i){
        currentlyHeld.put(i, whiteDraw.get(i));
        whiteDraw.remove(i);
        //reshuffle if the deck ever dips below 10
        if(whiteDraw.size() <= 10){
            shuffleWhite();
        }
    }
    public void opponentTookBlackCard(int i){
        currentBlackCard.put(i, blackDraw.get(i));
        blackDraw.remove(i);
        if(blackDraw.size() <= 5){
            shuffleBlack();
        }
    }
    public void playWhiteCard(int i){
        whiteDiscard.put(i, currentlyHeld.get(i));
        currentlyHeld.remove(i);
    }
}
