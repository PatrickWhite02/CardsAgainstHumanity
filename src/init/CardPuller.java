package init;

import com.Card;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class CardPuller {

    public static ArrayList<Card> getCards(){
        InputStream inputStream = CardPuller.class.getClassLoader().getResourceAsStream("res/cards/allCards.txt");
        String mass = new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining("\n"));
        String [] rawData = mass.split("\n");
        ArrayList<Card> cards = new ArrayList<>();
        for(String s : rawData){
            String [] split = s.split("`");
            if(split.length == 2){
                cards.add(new Card(split[0], split[1]));
            }
            //only add the third parameter if it has one
            else{
                cards.add(new Card(split[0], split[1], split[2]));
            }
        }
        return cards;
    }

    public static void main(String[] args) {
        for(Card c : getCards()){
            System.out.println(c.getCard());
        }
    }
}
