package game.map_event;

import java.util.List;

import game.card.CardStack;
import game.model.Hero;
import game.view.Colors;
import game.view.GameConsoleView;

/**
 * Representa um evento narrativo no mapa que exige uma escolha do jogador,
 * resultando em diferentes consequências (boas ou ruins).
 */
public class Choice extends MapEvent {
    /** O título ou nome do evento surpresa. */
    private String eventName;
    /** A descrição narrativa do que está acontecendo. */
    private String description;
    /** A lista de opções disponíveis para o jogador escolher. */
    private List<ChoiceOption> options;

    /**
     * Construtor para o evento de Escolha.
     * 
     * @param eventName O nome do evento.
     * @param description A narrativa exibida ao jogador.
     * @param options As alternativas de ação oferecidas.
     */
    public Choice(String eventName, String description, List<ChoiceOption> options) {
        this.eventName = eventName;
        this.description = description;
        this.options = options;
    }

    public boolean start(Hero hero, CardStack deck, GameConsoleView view) throws InterruptedException {
        view.clearScreen();
        
        System.out.println(Colors.BOLD + "\n=== " + eventName + " ===" + Colors.RESET);
        System.out.println(description);
        System.out.println("What will you do?");

        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ": " + options.get(i).getText());
        }

        boolean validChoice = false;

        while (!validChoice) {
            int choiceIndex = view.getPlayerMove() - 1;
            
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
