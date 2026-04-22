package game.core;

import game.model.Hero;
import game.model.Enemy;
import game.card.*;
import game.effect.*;
import game.map.MapNode;
import game.view.GameConsoleView;
import game.view.Colors;

import java.util.Collections;
import java.util.Stack;

/**
 * Gerencia a progressão da campanha, o mapa e o estado persistente do jogador.
 */
public class Manager {
    /** Herói controlado pelo jogador. */
    private Hero hero;
    /** Baralho de cartas do jogador. */
    private CardStack deck;
    /** Objeto responsável pela interface com o usuário no console. */
    private GameConsoleView view;
    /** Nó inicial do mapa do jogo. */
    private MapNode rootNode;

    /**
     * Construtor da classe Manager. Inicializa o herói, o baralho e o mapa do jogo.
     *
     * @param view A interface de visualização do jogo no console.
     */
    public Manager(GameConsoleView view) {
        this.view = view;
        initializeHeroAndDeck();
        initializeMap();
    }

    /**
     * Obtém a interface de visualização atual.
     *
     * @return O objeto GameConsoleView utilizado pelo gerenciador.
     */
    public GameConsoleView getView() {
        return this.view;
    }

    /**
     * Inicializa as cartas de dano, escudo e efeitos, montando o baralho inicial do jogador.
     */
    private void initializeHeroAndDeck() {
        // Cartas de dano
        DamageCard scratch = new DamageCard("Scratch", "Deals 3 points of damage", 1, 3, Colors.BLUE_BOLD);
        // ... (restante da inicialização omitida aqui por brevidade se não for alterar, mas manterei o original)
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
                boolean isNew = t.applyEffect(poison);
                if (isNew) {
                    b.subscribe(poison);
                }
            }, Colors.GRAY_BOLD);

        EffectCard lightBall = new EffectCard("Light Ball", "Increases 2 points of damage", 5, 
            (h, t, b) -> {
                Effect strength = new StrengthEffect("Strength", h, 2);
                boolean isNew = h.applyEffect(strength);
                if (isNew) {
                    b.subscribe(strength);
                }
            }, Colors.YELLOW3_BOLD);

        EffectCard obstruct = new EffectCard("Obstruct", "Increases 2 points of shield", 5, 
            (h, t, b) -> {
                Effect dexterity = new DexterityEffect("Dexterity", h, 2);
                boolean isNew = h.applyEffect(dexterity);
                if (isNew) {
                    b.subscribe(dexterity);
                }
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

    /**
     * Inicializa os nós do mapa, inimigos presentes em cada nó e define as conexões entre os nós (árvore do mapa).
     */
    private void initializeMap() {
        // Inimigos para cada nó
        Enemy pikachu = new Enemy("Pikachu", 20, 0, 5, 3, 2, Colors.YELLOW_BOLD);
        Enemy geodude = new Enemy("Geodude", 25, 0, 4, 5, 1, Colors.GRAY_BOLD);
        Enemy snorlax = new Enemy("Snorlax", 25, 0, 3, 4, 3, Colors.BLUE_BOLD);
        Enemy clefable = new Enemy("Clefable", 30, 0, 4, 1, 2, Colors.PINK_BOLD);
        Enemy psyduck = new Enemy("Psyduck", 30, 0, 3, 3, 3, Colors.YELLOW2_BOLD);
        Enemy lapras = new Enemy("Lapras", 35, 0, 4, 2, 1, Colors.BLUE2_BOLD);
        Enemy flareon = new Enemy("Flareon", 35, 0, 3, 4, 2, Colors.RED3_BOLD);
        Enemy mewtwo = new Enemy("Mewtwo", 50, 0, 8, 5, 5, Colors.LILAC_BOLD);

        // Nós do Mapa
        MapNode startNode = new MapNode("Forest Entrance", pikachu, Colors.GREEN2_BOLD);
        MapNode rockNode = new MapNode("Rock Tunnel", geodude, Colors.BROWN_BOLD);
        MapNode woodsNode = new MapNode("Timeless Woods", snorlax, Colors.CYAN_BOLD);
        MapNode mountNode = new MapNode("Mount Moon", clefable, Colors.LILAC_BOLD);
        MapNode safariNode = new MapNode("Safari Zone", psyduck, Colors.ORANGE_BOLD);
        MapNode iceNode = new MapNode("Icefall Cave", lapras, Colors.CYAN_BOLD);
        MapNode volcanicNode = new MapNode("Volcanic Cave", flareon, Colors.BROWN2_BOLD);
        MapNode finalNode = new MapNode("Final Cave (Boss)", mewtwo, Colors.PURPLE_BOLD);

        // Configuração da Árvore
        startNode.addNextNode(rockNode);
        startNode.addNextNode(woodsNode);
        rockNode.addNextNode(mountNode);
        rockNode.addNextNode(volcanicNode);
        woodsNode.addNextNode(safariNode);
        woodsNode.addNextNode(iceNode);
        mountNode.addNextNode(finalNode);
        safariNode.addNextNode(finalNode);
        iceNode.addNextNode(finalNode);
        volcanicNode.addNextNode(finalNode);

        this.rootNode = startNode;
    }

    /**
     * Inicia o laço principal do jogo, permitindo a seleção do herói e a progressão pelo mapa.
     *
     * @throws InterruptedException Caso a thread seja interrompida durante os atrasos das batalhas.
     */
    public void startGame() throws InterruptedException {
        MapNode currentNode = rootNode;
        boolean gameRunning = true;

        int choice = view.getHeroChoice();

        if (choice == 1) {
            this.hero = new Hero("Charmander", 20, 5, 0, Colors.ORANGE_BOLD);
        } else if (choice == 2) {
            this.hero = new Hero("Squirtle", 20, 5, 0, Colors.BLUE_BOLD);
        } else if (choice == 3) {
            this.hero = new Hero("Bulbasaur", 20, 5, 0, Colors.GREEN_BOLD);
        }

        while (gameRunning && hero.isAlive()) {
            Battle battle = new Battle(hero, currentNode.getEnemy(), deck, view);
            boolean victory = battle.start();

            if (victory) {
                if (currentNode.isFinalNode()) {
                    System.out.println(Colors.GREEN_BOLD + "\nVICTORY! You defeated the final boss!\n" + Colors.RESET);
                    gameRunning = false;
                } else {
                    view.displayMapChoices(currentNode.getNextNodes());
                    int choice_1 = view.getMapChoice(currentNode.getNextNodes().size());
                    currentNode = currentNode.getNextNodes().get(choice_1 - 1);
                }
            } else {
                System.out.println(Colors.RED_BOLD + "\nDEFEAT! Your journey ends here.\n" + Colors.RESET);
                gameRunning = false;
            }
        }
    }
}