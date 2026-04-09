import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;

public class Manager {
    private Hero hero;
    private Enemy enemy;
    private CardStack deck;
    private Scanner scanner;
    private ArrayList<Subscriber> subscribers;

    public Manager() {
        this.scanner = new Scanner(System.in);

        // Entidades (Jogador e Inimigo)
        this.hero = new Hero("Charmander", 20, 5, 0, Colors.ORANGE_BOLD);
        this.enemy = new Enemy("Pikachu", 20, 0, 5, 3, 2, Colors.YELLOW_BOLD);

        // Cartas de dano
        DamageCard scratch = new DamageCard("Scratch", "Deals 3 points of damage", 1, 3, Colors.BLUE_BOLD);
        DamageCard pound = new DamageCard("Pound", "Deals 4 points of damage", 2, 4, Colors.BROWN_BOLD);
        DamageCard flareBlitz = new DamageCard("Flare Blitz", "Deals 6 points of damage", 4, 6, Colors.RED_BOLD);
        DamageCard thunderbolt = new DamageCard("Thunderbolt", "Deals 5 points of damage", 4, 5, Colors.YELLOW2_BOLD);
        DamageCard flamethrower = new DamageCard("Flamethrower", "Deals 8 points of damage", 5, 8, Colors.PINK_BOLD);
        // NEW!!!
        DamageCard heatBlast = new DamageCard("Heat Blast", "Deals 4 points of damage", 3, 4, Colors.GREEN2_BOLD);
        DamageCard tailWhip = new DamageCard("Tail Whap", "Deals 3 points of damage", 2, 3, Colors.CYAN_BOLD);
        
        // Cartas de escudo
        ShieldCard harden = new ShieldCard("Harden", "Grants 2 points of shield", 1, 2, Colors.ORANGE2_BOLD);
        ShieldCard barrier = new ShieldCard("Barrier", "Grants 3 points of shield", 2, 3, Colors.PURPLE_BOLD);
        ShieldCard shellArmor = new ShieldCard("Shell Armor", "Grants 5 points of shield",3, 5, Colors.BLUE2_BOLD);
        ShieldCard acidArmor = new ShieldCard("Acid Armor", "Grants 7 points of shield", 4, 7, Colors.GREEN_BOLD);
        ShieldCard ironDefense = new ShieldCard("Iron Defense", "Grants 10 points of shield", 5, 10, Colors.RED2_BOLD);
        // NEW!!!
        ShieldCard cosmicPower = new ShieldCard("Cosmic Power", "Grants 6 points of shield", 3, 6, Colors.MAGENTA_BOLD);
        ShieldCard bulkUp = new ShieldCard("Bulk Up", "Grants 9 points of shield", 5, 9, Colors.LILAC_BOLD);

        // Cartas de efeito
        EffectCard poisonJab = new EffectCard("Poison Jab", "Triggers poison and causes 3 points of damage", 3, "Poison", 3, Colors.GRAY_BOLD);
        EffectCard lightBall = new EffectCard("Light Ball", "Increases 2 points of damage", 5, "Strength", 2, Colors.YELLOW3_BOLD);
        // EffectCard de destreza (mesma coisa que efeito Strength, mas para as cartas de escudo)
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

        this.subscribers = new ArrayList<>();
    }

    // Métodos do subscriber
    public void subscribe(Subscriber sub) {
        subscribers.add(sub);
    }

    public void unsubscribe(Subscriber sub) {
        subscribers.remove(sub);
    }

    public void launchesNotification(String event) {
        ArrayList<Subscriber> copy = new ArrayList<>(subscribers);
        for (Subscriber sub : copy) {
            sub.receivesNotification(event, this);
        }
    }

    public void startCombat() throws InterruptedException {
        int turn = 1;
        int fullEnergy = hero.getEnergy();
        System.out.printf(Colors.YELLOW_BOLD + "A wild %s has appeared!\n" + Colors.RESET, enemy.getName());

        while (hero.isAlive() && enemy.isAlive()) {
            
            hero.restoreEnergy(fullEnergy);
            hero.resetShield();
            
            int heroNumberOfCards = 5;
            for (int i = 0; i < heroNumberOfCards; i++) {
                deck.buy();
            }

            int move;

            clearScreen();

            launchesNotification("beginningOfRound");

            while (true) {
                showBattle(hero, enemy);
                enemy.intent(turn);

                System.out.printf("\n%s, you're up! Choose your next move.\n", hero.getColoredName());
                System.out.printf("Energy remaining: %d/%d\n", hero.getEnergy(), fullEnergy);

                for (int i = 0; i < deck.getPlayerHand().size(); i++) {
                    System.out.printf("%d: Use %s (Cost: %d) - %s\n", i+1, deck.getPlayerHand().get(i).getColoredName(), deck.getPlayerHand().get(i).getEnergyCost(), deck.getPlayerHand().get(i).getDescription());
                }

                int endRoundOptionNumber = deck.getPlayerHand().size() + 1;
                System.out.printf("%d. End Round\n", endRoundOptionNumber);

                System.out.print(Colors.BOLD + "Your choice: " + Colors.RESET);
                
                move = scanner.nextInt();

                System.out.flush();

                if (move > 0 && move < endRoundOptionNumber) {
                int index = move - 1;
                
                Card selectedCard = deck.getPlayerHand().get(index);
                
                    if (hero.getEnergy() >= selectedCard.getEnergyCost()) {
                        deck.getPlayerHand().remove(index); 
                        selectedCard.use(hero, enemy, this);
                        deck.discard(selectedCard);
                        
                        System.out.printf("\n>>> %s used %s!\n", hero.getColoredName(), selectedCard.getColoredName());
                        
                    } else {
                        System.out.println("\n>>> Not enough energy!\n");
                    }

                } else if (move == endRoundOptionNumber) {
                System.out.println("\n>>> Ending turn...");
                break;

                } else {
                System.out.println("\n>>> Invalid choice!\n");
                }

                if (!enemy.isAlive()) {
                    break;
                }

                Thread.sleep(3000);
                clearScreen();
            }

            deck.discardHand();

            // Turno do inimigo
            enemy.resetShield();
            if (enemy.isAlive()) {
                clearScreen();
                turn = enemy.enemyTurn(hero, turn, this);

                Thread.sleep(3000);
            }

            launchesNotification("endOfRound");
        }

        System.out.println(Colors.RED_BOLD + "\nEnd of the game!" + Colors.RESET);
        if (hero.isAlive()) {
            System.out.printf("%s rises victorious defeating %s!\n", hero.getColoredName(), enemy.getColoredName());
        } else {
            System.out.printf("%s triumphed over %s!\n", enemy.getColoredName(), hero.getColoredName());
        }

        scanner.close();
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void showBattle(Hero hero, Enemy enemy) {
        System.out.println("\n-------------------------------------------");
        System.out.printf("%s\n", hero.getColoredName());
        System.out.printf("HP: %s | Energy: %d | Shield: %d\n",  
            createHealthBar(hero.getHealth(), hero.getMaxHealth(), 20),
            hero.getEnergy(), 
            hero.getShield());

        if (!hero.getEffects().isEmpty()) {
            System.out.println("Active effects: ");
            for (Effect e : hero.getEffects()) {
                System.out.printf(e.getString());
            }
            System.out.println();
        }
        
        System.out.printf(Colors.BOLD + "VS.\n" + Colors.RESET);
        System.out.printf("%s\n", enemy.getColoredName());
        System.out.printf("HP: %s | Shield: %d\n",  
            createHealthBar(enemy.getHealth(), enemy.getMaxHealth(), 20),
            enemy.getShield());

        if (!enemy.getEffects().isEmpty()) {
            System.out.println("Active effects: ");
            for (Effect e : enemy.getEffects()) {
                System.out.printf("%s (%d) ", e.getName(), e.getAmount());
            }
            System.out.println();

        }
        System.out.println("-------------------------------------------\n");
    }

    public static String createHealthBar(int currentHealth, int maxHealth, int barSize) {
        if (maxHealth <= 0) {
            return "[ Error ]";
        }

        currentHealth = Math.max(0, Math.min(currentHealth, maxHealth));

        double percentage = (double) currentHealth / maxHealth;
        int healthBlocks = (int) Math.round(percentage * barSize);

        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < barSize; i++) {
            if (i < healthBlocks) {
                bar.append("█");
            } else {
                bar.append("░");
            }
        }
        bar.append("] ").append(currentHealth).append("/").append(maxHealth);
        return bar.toString();
    }
}
