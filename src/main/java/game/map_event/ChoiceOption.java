package game.map_event;

import game.model.Hero;

public class ChoiceOption {
    private String text;
    private ChoiceAction action; 

    public ChoiceOption(String text, ChoiceAction action) {
        this.text = text;
        this.action = action;
    }

    public String getText() { return text; }
    
    public void executeAction(Hero hero) { 
        action.apply(hero); 
    }
}