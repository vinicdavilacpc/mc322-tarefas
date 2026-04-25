package game.map_event;

import java.util.List;

import game.card.CardStack;
import game.model.Hero;
import game.view.Colors;
import game.view.GameConsoleView;

public class Choice extends MapEvent {
    private String eventName;
    private String description;
    private List<ChoiceOption> options;

    public Choice(String eventName, String description, List<ChoiceOption> options) {
        this.eventName = eventName;
        this.description = description;
        this.options = options;
    }

    public String getEventDescription() {
        return description;
    }

    public String getEventName() {
        return eventName;
    }
    
    public String getDescription() {
        return Colors.BOLD + "Surprise Event: " + Colors.RESET + eventName;
    }

    public List<ChoiceOption> getOptions() {
        return options;
    }

    public boolean start(Hero hero, CardStack deck, GameConsoleView view) throws InterruptedException {
        view.clearScreen();
        
        System.out.println(Colors.PURPLE_BOLD + "\n=== " + eventName + " ===" + Colors.RESET);
        System.out.println(description);
        System.out.println("What will you do?");

        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ": " + options.get(i).getText());
        }

        boolean validChoice = false;

        while (!validChoice) {
            int choiceIndex = view.getPlayerMove() - 1; // Subtrai 1 para virar índice de lista (0, 1, 2...)
            
            if (choiceIndex >= 0 && choiceIndex < options.size()) {
                ChoiceOption selectedOption = options.get(choiceIndex);
                selectedOption.executeAction(hero);
                
                validChoice = true;
            } else {
                view.displayInvalidChoice();
            }
        }
        
        Thread.sleep(3000); 
        return hero.isAlive();
    }
}
