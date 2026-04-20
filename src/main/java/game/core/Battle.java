package game.core;

import java.util.ArrayList;
import java.util.List;

import game.card.Card;
import game.card.CardStack;
import game.event.GameEvent;
import game.event.Subscriber;
import game.model.Enemy;
import game.model.Hero;
import game.view.GameConsoleView;

public class Battle {
    private Hero hero;
    private Enemy enemy;
    private CardStack deck;
    private GameConsoleView view;
    private List<Subscriber> subscribers;

    public Battle(Hero hero, Enemy enemy, CardStack deck, GameConsoleView view) {
        this.hero = hero;
        this.enemy = enemy;
        this.deck = deck;
        this.view = view;
        this.subscribers = new ArrayList<>();
    }

    public GameConsoleView getView() { return this.view; }
    public void subscribe(Subscriber sub) { subscribers.add(sub); }
    public void unsubscribe(Subscriber sub) { subscribers.remove(sub); }

    public void launchesNotification(GameEvent event) {
        List<Subscriber> copy = new ArrayList<>(subscribers);
        for (Subscriber sub : copy) {
            sub.receivesNotification(event, this);
        }
    }

    /**
     * Executa o combate até o fim.
     * @return true se o herói vencer, false se for derrotado.
     */
    public boolean start() throws InterruptedException {
        int turn = 1;
        int fullEnergy = hero.getMaxEnergy();

        hero.getEffects().clear();
        
        view.displayWildEncounter(enemy);

        while (hero.isAlive() && enemy.isAlive()) {
            hero.restoreEnergy(fullEnergy);
            hero.resetShield();
            
            for (int i = 0; i < 5; i++) {
                deck.buy();
            }

            view.clearScreen();
            launchesNotification(GameEvent.BEGINNING_OF_ROUND);

            while (true) {
                view.showBattle(hero, enemy);
                view.showIntent(enemy, turn);
                view.showPlayerOptions(hero, deck, fullEnergy);

                int move = view.getPlayerMove();
                int endRoundOptionNumber = deck.getPlayerHand().size() + 1;

                if (move > 0 && move < endRoundOptionNumber) {
                    int index = move - 1;
                    Card selectedCard = deck.getPlayerHand().get(index);
                    
                    if (hero.getEnergy() >= selectedCard.getEnergyCost()) {
                        deck.getPlayerHand().remove(index); 
                        selectedCard.use(hero, enemy, this); // Agora passamos a 'Battle'
                        deck.discard(selectedCard);
                        view.displayCardUsage(hero, selectedCard);
                    } else {
                        view.displayNotEnoughEnergy();
                    }
                } else if (move == endRoundOptionNumber) {
                    view.displayTurnEnding();
                    break;
                } else {
                    view.displayInvalidChoice();
                }

                if (!enemy.isAlive()) break;

                Thread.sleep(3000);
                view.clearScreen();
            }

            deck.discardHand();
            enemy.resetShield();
            
            if (enemy.isAlive()) {
                view.clearScreen();
                turn = enemy.enemyTurn(hero, turn, this, view);
                Thread.sleep(3000);
            }

            launchesNotification(GameEvent.END_OF_ROUND);
        }

        view.displayGameEnd(hero, enemy);
        return hero.isAlive();
    }
}