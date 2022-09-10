package com;

import javax.swing.*;
import java.util.Arrays;

public class Card{
    private final boolean blackCard;
    private final String text;
    private String instructions;
    public Card(String type, String text, String instructions){
        this.blackCard=type.equals("Prompt");
        this.text = text;
        this.instructions = instructions;
    }
    public Card(String type, String text){
        this.blackCard=type.equals("Prompt");
        this.text = text;
    }
    public String getCard(){
        return Arrays.toString(new String[]{text, blackCard + "", instructions});
    }
    public String getText(){
        return text;
    }
    public boolean getBlackCard(){
        return blackCard;
    }
    public String getInstructions(){
        return instructions;
    }
}
