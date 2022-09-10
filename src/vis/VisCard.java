package vis;

import com.Card;

import javax.swing.*;
import java.awt.event.ActionListener;

public class VisCard extends JButton{
    private Card card;
    public VisCard(Card card){
        this.card = card;
        if(card.getInstructions() != null){
            this.setText(card.getText() + "\n<b>" + card.getInstructions() + "</b");
        }else{
            this.setText(card.getText());
        }
        this.setSize(192, 816);
    }
    ActionListener actionListener = e -> {

    };
}
