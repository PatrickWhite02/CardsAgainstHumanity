package com;

public class Card {
    private boolean blackCard = false;
    private String text;
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
}
