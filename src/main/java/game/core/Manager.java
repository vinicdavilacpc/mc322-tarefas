package game.core;

import game.model.Hero;
import game.model.Enemy;
import game.card.*;
import game.effect.*;
import game.item.EnergyItem;
import game.item.HealingItem;
import game.item.Item;
import game.map.MapNode;
import game.map_event.Battle;
import game.map_event.Choice;
import game.map_event.ChoiceOption;
import game.map_event.MapEvent;
import game.map_event.Shop;
import game.view.GameConsoleView;
import game.view.Colors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
     * Gera uma lista de cartas aleatórias para oferecer como recompensa.
     */
    private List<Card> generateRandomCards(int amount) {
        List<Card> pool = new ArrayList<>();
        pool.add(new DamageCard("Water Gun", "Deals 4 points of damage", 2, 4, Colors.BLUE_BOLD));
        pool.add(new DamageCard("Vine Whip", "Deals 5 points of damage", 3, 5, Colors.GREEN_BOLD));
        pool.add(new DamageCard("Ember", "Deals 5 points of damage", 3, 5, Colors.ORANGE_BOLD));
        pool.add(new ShieldCard("Withdraw", "Grants 4 points of shield", 2, 4, Colors.BLUE2_BOLD));
        pool.add(new ShieldCard("Protect", "Grants 6 points of shield", 3, 6, Colors.GRAY_BOLD));
        pool.add(new EffectCard("Growl", "Increases 1 point of damage", 2, 
            (h, t, b) -> {
                Effect strength = new StrengthEffect("Strength", h, 1);
                boolean isNew = h.applyEffect(strength);
                if (isNew && b != null) { b.subscribe(strength); }
            }, Colors.GRAY_BOLD));
        Collections.shuffle(pool);
        return pool.subList(0, Math.min(amount, pool.size()));
    }

    /**
     * Inicializa os nós do mapa, inimigos presentes em cada nó e define as conexões entre os nós (árvore do mapa).
     */
    private void initializeMap() {
        List<Item> shopItems = new ArrayList<>();
        shopItems.add(new HealingItem("Potion", "Restores 5 HP", 30, 5, Colors.PINK_BOLD));
        shopItems.add(new HealingItem("Super Potion", "Restores 10 HP", 60, 10, Colors.PINK_BOLD));
        shopItems.add(new EnergyItem("Ether", "Restores 3 Energy", 40, 3, Colors.BLUE2_BOLD));

        // Inimigos para cada nó
        Enemy pikachu = new Enemy("Pikachu", 20, 0, 5, 3, 2, Colors.YELLOW_BOLD);
        Enemy geodude = new Enemy("Geodude", 25, 0, 4, 5, 1, Colors.GRAY_BOLD);
        Enemy snorlax = new Enemy("Snorlax", 30, 0, 3, 4, 3, Colors.BLUE_BOLD);
        Enemy clefable = new Enemy("Clefable", 30, 0, 4, 1, 2, Colors.PINK_BOLD);
        Enemy psyduck = new Enemy("Psyduck", 30, 0, 3, 3, 3, Colors.YELLOW2_BOLD);
        Enemy lapras = new Enemy("Lapras", 35, 0, 4, 2, 1, Colors.BLUE2_BOLD);
        Enemy flareon = new Enemy("Flareon", 35, 0, 3, 4, 2, Colors.RED3_BOLD);
        Enemy mewtwo = new Enemy("Mewtwo", 50, 0, 8, 5, 5, Colors.LILAC_BOLD);

        // Escolhas para o nó de escolha
        Choice rocketEvent = new Choice(
            "Team Rocket Ambush", 
            "Jessie and James block your path! They demand your coins.",
            List.of( 
                new ChoiceOption("Pay them " + Colors.YELLOW_BOLD + "(Lose 15 Coins)" + Colors.RESET, (h) -> {
                    h.subtractPokeCoin(15); 
                    System.out.println("They take your coins and run away laughing.");
                }),
                new ChoiceOption("Try to escape " + Colors.RED_BOLD + "(Take 10 damage)" + Colors.RESET, (h) -> {
                    h.takeDirectDamage(10);
                    System.out.println("You barely escaped, but took some hits!");
                })
                )
            );

        Choice pokerusEvent = new Choice(
            "Savage pokemon encounter", 
            "You find a savage pokemon in the wild and he wants to figth!",
            List.of( 
                new ChoiceOption("Figth him " + Colors.YELLOW_BOLD + "(Take 10 damage but gain +2 Max energy)" + Colors.RESET, (h) -> {
                    h.takeDirectDamage(10);
                    h.increaseMaxEnergy(2);
                    System.out.println("You got infected by the virus Pokerus! Strange things happened.");
                }),
                new ChoiceOption("Try to escape " + Colors.RED_BOLD + "(Lose 20 Coins)" + Colors.RESET, (h) -> {
                    h.subtractPokeCoin(20); 
                    System.out.println("You managed to escape, but lost some PokeCoins in the process!");
                })
                )
            );
        
        Shop shopEvent1 = new Shop(shopItems);
        Shop shopEvent2 = new Shop(shopItems);

        // Nós do Mapa
        MapNode startNode = new MapNode("Forest Entrance", new Battle(pikachu, this.view), Colors.GREEN2_BOLD, 15, "ENERGY", 1);
        MapNode rockNode = new MapNode("Rock Tunnel", new Battle(geodude, this.view), Colors.BROWN_BOLD, 20, "HEALTH", 3);
        MapNode woodsNode = new MapNode("Timeless Woods", new Battle(snorlax, this.view), Colors.CYAN_BOLD, 25, "HEALTH", 3);
        MapNode mountNode = new MapNode("Mount Moon", new Battle(clefable, this.view), Colors.LILAC_BOLD, 30, "ENERGY", 1);
        MapNode safariNode = new MapNode("Safari Zone", new Battle(psyduck, this.view), Colors.ORANGE_BOLD, 30, "ENERGY", 1);
        MapNode iceNode = new MapNode("Icefall Cave", new Battle(lapras, this.view), Colors.CYAN_BOLD, 45, "HEALTH", 2);
        MapNode volcanicNode = new MapNode("Volcanic Cave", new Battle(flareon, this.view), Colors.BROWN2_BOLD, 40, "HEALTH", 2);
        MapNode finalNode = new MapNode("Final Cave (Boss)", new Battle(mewtwo, this.view), Colors.PURPLE_BOLD, 50, "HEALTH", 20);

        MapNode rocketNode = new MapNode("Celadon City", rocketEvent, Colors.RED3_BOLD, 0, "NONE", 0);
        MapNode pokerusNode = new MapNode("Eterna Forest", pokerusEvent, Colors.GREEN_BOLD, 0, "NONE", 0);
        MapNode shop1Node = new MapNode("Pokémon Mart", shopEvent1, Colors.YELLOW_BOLD, 0, "NONE", 0);
        MapNode shop2Node = new MapNode("Pokémon Mart", shopEvent2, Colors.YELLOW_BOLD, 0, "NONE",0);

        // Configuração da árvore
        startNode.addNextNode(rockNode);
        startNode.addNextNode(woodsNode);
        rockNode.addNextNode(rocketNode);
        woodsNode.addNextNode(pokerusNode);
        rocketNode.addNextNode(mountNode);
        rocketNode.addNextNode(shop1Node);
        pokerusNode.addNextNode(safariNode);
        pokerusNode.addNextNode(shop1Node);
        shop1Node.addNextNode(iceNode);
        shop1Node.addNextNode(volcanicNode);
        mountNode.addNextNode(iceNode);
        safariNode.addNextNode(volcanicNode);
        iceNode.addNextNode(shop2Node);
        volcanicNode.addNextNode(shop2Node);
        shop2Node.addNextNode(finalNode);

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
            this.hero = new Hero("Charmander", 20, 5, 0, Colors.ORANGE_BOLD, 0);
        } else if (choice == 2) {
            this.hero = new Hero("Squirtle", 20, 5, 0, Colors.BLUE_BOLD, 0);
        } else if (choice == 3) {
            this.hero = new Hero("Bulbasaur", 20, 5, 0, Colors.GREEN_BOLD, 0);
        }

        while (gameRunning && hero.isAlive()) {
            MapEvent currentEvent = currentNode.getEvent();
            boolean victory = currentEvent.start(hero, deck, view);

            if (victory) {
                if (currentNode.isFinalNode()) {
                    System.out.println(Colors.GREEN_BOLD + "\nVICTORY! You defeated the final boss!\n" + Colors.RESET);
                    gameRunning = false;
                } else {
                    int goldGained = currentNode.getPokeCoinReward();
                    String buffGained = currentNode.getRewardType();
                    int amountGained = currentNode.getRewardAmount();
                    
                    hero.addPokeCoin(goldGained);
                    if ("HEALTH".equals(buffGained)) {
                        hero.increaseMaxHealth(amountGained); 
                    } else if ("ENERGY".equals(buffGained)) {
                        hero.increaseMaxEnergy(amountGained); 
                    }
                    
                    view.displayRewardReceived(goldGained, buffGained, amountGained);

                    if (currentEvent instanceof Battle) {
                        List<Card> rewardCards = generateRandomCards(3);
                        int cardChoice = view.getCardRewardChoice(rewardCards);
                        
                        if (cardChoice <= rewardCards.size()) {
                            Card chosenCard = rewardCards.get(cardChoice - 1);
                            deck.getBuyStack().push(chosenCard); 
                            System.out.println(Colors.GREEN_BOLD + "\n>>> " + chosenCard.getName() + " was added to your deck!" + Colors.RESET);
                        } else {
                            System.out.println("\n>>> You skipped the card reward.");
                        }
                    }

                    view.displayMapChoices(currentNode.getNextNodes());
                    int nextPath = view.getMapChoice(currentNode.getNextNodes().size());
                    currentNode = currentNode.getNextNodes().get(nextPath - 1);
                }
            }
        }
    }
}