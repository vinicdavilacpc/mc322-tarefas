package game.core;

import game.model.Hero;
import game.model.Enemy;
import game.card.*;
import game.effect.*;
import game.event.GameEvent;
import game.event.Subscriber;
import game.view.GameConsoleView;
import game.view.Colors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 * Classe responsável por gerenciar o estado global da partida, 
 * configurar o baralho e controlar o loop principal de turnos do combate.
 * Atua também como o publicador (Publisher) no padrão Observer para eventos de jogo.
 */
public class Manager {
    private Hero hero;
    private Enemy enemy;
    private CardStack deck;
    private GameConsoleView view;
    private ArrayList<Subscriber> subscribers;

    public Manager(GameConsoleView view) {
        this.view = view;
        this.subscribers = new ArrayList<>();
        initializeGameEntities();
    }

    private void initializeGameEntities() {
        // Entidades (Jogador e Inimigo)
        this.hero = new Hero("Charmander", 20, 5, 0, Colors.ORANGE_BOLD);
        this.enemy = new Enemy("Pikachu", 20, 0, 5, 3, 2, Colors.YELLOW_BOLD);

        // Cartas de dano
        DamageCard scratch = new DamageCard("Scratch", "Deals 3 points of damage", 1, 3, Colors.BLUE_BOLD);
        DamageCard pound = new DamageCard("Pound", "Deals 4 points of damage", 2, 4, Colors.BROWN_BOLD);
        DamageCard flareBlitz = new DamageCard("Flare Blitz", "Deals 6 points of damage", 4, 6, Colors.RED_BOLD);
        DamageCard thunderbolt = new DamageCard("Thunderbolt", "Deals 5 points of damage", 4, 5, Colors.YELLOW2_BOLD);
        DamageCard flamethrower = new DamageCard("Flamethrower", "Deals 8 points of damage", 5, 8, Colors.PINK_BOLD);
        DamageCard heatBlast = new DamageCard("Heat Blast", "Deals 4 points of damage", 3, 4, Colors.GREEN2_BOLD);
        DamageCard tailWhip = new DamageCard("Tail Whap", "Deals 3 points of damage", 2, 3, Colors.CYAN_BOLD);
        
        // Cartas de escudo
        ShieldCard harden = new ShieldCard("Harden", "Grants 2 points of shield", 1, 2, Colors.ORANGE2_BOLD);
        ShieldCard barrier = new ShieldCard("Barrier", "Grants 3 points of shield", 2, 3, Colors.PURPLE_BOLD);
        ShieldCard shellArmor = new ShieldCard("Shell Armor", "Grants 5 points of shield",3, 5, Colors.BLUE2_BOLD);
        ShieldCard acidArmor = new ShieldCard("Acid Armor", "Grants 7 points of shield", 4, 7, Colors.GREEN_BOLD);
        ShieldCard ironDefense = new ShieldCard("Iron Defense", "Grants 10 points of shield", 5, 10, Colors.RED2_BOLD);
        ShieldCard cosmicPower = new ShieldCard("Cosmic Power", "Grants 6 points of shield", 3, 6, Colors.MAGENTA_BOLD);
        ShieldCard bulkUp = new ShieldCard("Bulk Up", "Grants 9 points of shield", 5, 9, Colors.LILAC_BOLD);

        // Cartas de efeito
        EffectCard poisonJab = new EffectCard("Poison Jab", "Triggers poison and causes 3 points of damage", 3, "Poison", 3, Colors.GRAY_BOLD);
        EffectCard lightBall = new EffectCard("Light Ball", "Increases 2 points of damage", 5, "Strength", 2, Colors.YELLOW3_BOLD);
        EffectCard obstruct = new EffectCard("Obstruct", "Increases 2 points of shield", 5, "Dexterity", 2, Colors.CORAL_BOLD);

        // Pilhas de Cartas
        Stack<Card> cards = new Stack<>();
        cards.push(scratch);
        cards.push(pound);
        cards.push(flamethrower);
        cards.push(thunderbolt);
        cards.push(flareBlitz);
        cards.push(shellArmor);
        cards.push(acidArmor);
        cards.push(ironDefense);
        cards.push(barrier);
        cards.push(harden);
        cards.push(poisonJab);
        cards.push(lightBall);
        cards.push(heatBlast);
        cards.push(tailWhip);
        cards.push(cosmicPower);
        cards.push(bulkUp);
        cards.push(obstruct);

        Collections.shuffle(cards);
        Stack<Card> discardCards = new Stack<>();
        this.deck = new CardStack(cards, discardCards);
    }

    public void subscribe(Subscriber sub) {
        subscribers.add(sub);
    }

    public void unsubscribe(Subscriber sub) {
        subscribers.remove(sub);
    }

    public void launchesNotification(GameEvent event) {
        ArrayList<Subscriber> copy = new ArrayList<>(subscribers);
        for (Subscriber sub : copy) {
            sub.receivesNotification(event, this);
        }
    }

    public void startCombat() throws InterruptedException {
        int turn = 1;
        int fullEnergy = hero.getEnergy();
        
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
                        selectedCard.use(hero, enemy, this);
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
    }
}