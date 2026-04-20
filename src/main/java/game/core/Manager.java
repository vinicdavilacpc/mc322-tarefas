package game.core;

import game.model.Hero;
import game.model.Enemy;
import game.card.*;
import game.effect.*;
import game.map.MapNode;
import game.view.GameConsoleView;
import game.view.Colors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 * Gerencia a progressão da campanha, o mapa e o estado persistente do jogador.
 */
public class Manager {
    private Hero hero;
    private CardStack deck;
    private GameConsoleView view;
    private MapNode rootNode;

    public Manager(GameConsoleView view) {
        this.view = view;
        initializeHeroAndDeck();
        initializeMap();
    }

    public GameConsoleView getView() {
        return this.view;
    }

    private void initializeHeroAndDeck() {
        // Heroi
        this.hero = new Hero("Charmander", 20, 5, 0, Colors.ORANGE_BOLD);
        
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
        ShieldCard shellArmor = new ShieldCard("Shell Armor", "Grants 5 points of shield", 3, 5, Colors.BLUE2_BOLD);
        ShieldCard acidArmor = new ShieldCard("Acid Armor", "Grants 7 points of shield", 4, 7, Colors.GREEN_BOLD);
        ShieldCard ironDefense = new ShieldCard("Iron Defense", "Grants 10 points of shield", 5, 10, Colors.RED2_BOLD);
        ShieldCard cosmicPower = new ShieldCard("Cosmic Power", "Grants 6 points of shield", 3, 6, Colors.MAGENTA_BOLD);
        ShieldCard bulkUp = new ShieldCard("Bulk Up", "Grants 9 points of shield", 5, 9, Colors.LILAC_BOLD);

        // Cartas de efeito
        EffectCard poisonJab = new EffectCard("Poison Jab", "Triggers poison and causes 3 points of damage", 3, 
            (h, t, b) -> {
                Effect poison = new PoisonEffect("Poison", t, 3);
                t.applyEffect(poison);
                b.subscribe(poison);
            }, Colors.GRAY_BOLD);

        EffectCard lightBall = new EffectCard("Light Ball", "Increases 2 points of damage", 5, 
            (h, t, b) -> {
                Effect strength = new StrengthEffect("Strength", h, 2);
                h.applyEffect(strength);
                b.subscribe(strength);
            }, Colors.YELLOW3_BOLD);

        EffectCard obstruct = new EffectCard("Obstruct", "Increases 2 points of shield", 5, 
            (h, t, b) -> {
                Effect dexterity = new DexterityEffect("Dexterity", h, 2);
                h.applyEffect(dexterity);
                b.subscribe(dexterity);
            }, Colors.CORAL_BOLD);

        // Pilha de Cartas
        Stack<Card> cards = new Stack<>();
        cards.push(scratch); cards.push(pound); cards.push(flareBlitz);
        cards.push(thunderbolt); cards.push(flamethrower); cards.push(heatBlast);
        cards.push(tailWhip); cards.push(harden); cards.push(barrier);
        cards.push(shellArmor); cards.push(acidArmor); cards.push(ironDefense);
        cards.push(cosmicPower); cards.push(bulkUp); cards.push(poisonJab);
        cards.push(lightBall); cards.push(obstruct);

        Collections.shuffle(cards);
        this.deck = new CardStack(cards, new Stack<Card>());
    }

    private void initializeMap() {
        // Inimigos para cada nó
        Enemy pikachu = new Enemy("Pikachu", 20, 0, 5, 3, 2, Colors.YELLOW_BOLD);
        Enemy squirtle = new Enemy("Squirtle", 25, 0, 4, 5, 1, Colors.BLUE_BOLD);
        Enemy bulbasaur = new Enemy("Bulbasaur", 25, 0, 3, 4, 3, Colors.GREEN_BOLD);
        Enemy mewtwo = new Enemy("Mewtwo", 50, 0, 8, 5, 5, Colors.PURPLE_BOLD);

        // Nós do Mapa
        MapNode startNode = new MapNode("Forest Entrance", pikachu);
        MapNode riverNode = new MapNode("Cerulean River", squirtle);
        MapNode woodsNode = new MapNode("Deep Woods", bulbasaur);
        MapNode caveNode = new MapNode("Final Cave (Boss)", mewtwo);

        // Configuração da Árvore
        startNode.addNextNode(riverNode);
        startNode.addNextNode(woodsNode);
        riverNode.addNextNode(caveNode);
        woodsNode.addNextNode(caveNode);

        this.rootNode = startNode;
    }

    public void startGame() throws InterruptedException {
        MapNode currentNode = rootNode;
        boolean gameRunning = true;

        while (gameRunning && hero.isAlive()) {
            Battle battle = new Battle(hero, currentNode.getEnemy(), deck, view);
            boolean victory = battle.start();

            if (victory) {
                if (currentNode.isFinalNode()) {
                    System.out.println(Colors.GREEN_BOLD + "\nVICTORY! You defeated the final boss!\n" + Colors.RESET);
                    gameRunning = false;
                } else {
                    view.displayMapChoices(currentNode.getNextNodes());
                    int choice = view.getMapChoice(currentNode.getNextNodes().size());
                    currentNode = currentNode.getNextNodes().get(choice - 1);
                }
            } else {
                System.out.println(Colors.RED_BOLD + "\nDEFEAT! Your journey ends here.\n" + Colors.RESET);
                gameRunning = false;
            }
        }
    }
}