package game.map_event;

import java.util.ArrayList;
import java.util.List;

import game.card.Card;
import game.card.CardStack;
import game.event.GameEvent;
import game.event.Subscriber;
import game.model.Enemy;
import game.model.Hero;
import game.view.Colors;
import game.view.GameConsoleView;

/**
 * Representa e gerencia uma batalha entre um Herói e um Inimigo.
 * Implementa um sistema de notificações baseado no padrão Observer para tratar eventos da rodada.
 */
public class Battle extends MapEvent {
    /** O inimigo participando da batalha. */
    private Enemy enemy;
    /** Objeto responsável pela visualização da batalha no console. */
    private GameConsoleView view;
    /** Lista de inscritos (Subscribers) para os eventos da batalha (ex: efeitos de status). */
    private List<Subscriber> subscribers;

    /**
     * Construtor da classe Battle.
     *
     * @param hero  Herói do jogador.
     * @param enemy Inimigo a ser enfrentado.
     * @param deck  Baralho do herói.
     * @param view  Visualizador do console.
     */
    public Battle(Enemy enemy, GameConsoleView view) {
        this.enemy = enemy;
        this.view = view;
        this.subscribers = new ArrayList<>();
    }

    /**
     * Obtém a view responsável por exibir a batalha.
     *
     * @return O objeto GameConsoleView.
     */
    public GameConsoleView getView() { return this.view; }

    /**
     * Inscreve um novo Subscriber para receber notificações de eventos da batalha.
     *
     * @param sub Objeto que implementa a interface Subscriber.
     */
    public void subscribe(Subscriber sub) { subscribers.add(sub); }

    /**
     * Remove um Subscriber da lista de notificações.
     *
     * @param sub Objeto que implementa a interface Subscriber a ser removido.
     */
    public void unsubscribe(Subscriber sub) { subscribers.remove(sub); }

    /**
     * Notifica todos os Subscribers inscritos sobre um evento específico da batalha.
     *
     * @param event O evento do jogo que acabou de ocorrer (ex: BEGINNING_OF_ROUND, END_OF_ROUND).
     */
    public void launchesNotification(GameEvent event) {
        List<Subscriber> copy = new ArrayList<>(subscribers);
        for (Subscriber sub : copy) {
            sub.receivesNotification(event, this);
        }
    }

    public String getDescription() {
        return Colors.BOLD + "Enemy: " + enemy.getColoredName() + Colors.RESET + " (HP: " + enemy.getHealth() + ")";
    }

    /**
     * Executa o combate até o fim, alternando entre turnos do jogador e do inimigo.
     * * @return true se o herói vencer, false se for derrotado.
     * @throws InterruptedException Caso a thread seja interrompida durante os atrasos (Thread.sleep).
     */
    public boolean start(Hero hero, CardStack deck, GameConsoleView view) throws InterruptedException {

        int turn = 1;
        int fullEnergy = hero.getMaxEnergy();

        hero.getEffects().clear();
        deck.resetBattleDeck();
        
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