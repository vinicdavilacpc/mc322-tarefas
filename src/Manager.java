import java.util.Scanner;
import java.util.Stack;
import java.util.ArrayList;

public class Manager {
    private Hero hero;
    private Enemy enemy;
    private CardStack deck;
    private Scanner scanner;
    private ArrayList<Subscriber> subscribers;

    public Manager() {
        this.scanner = new Scanner(System.in);

        // Entidades (Jogador e Inimigo)
        this.hero = new Hero("Charmander", 20, 5, 0);
        this.enemy = new Enemy("Pikachu", 20, 0, 5, 3);

        // Cartas de dano
        DamageCard scratch = new DamageCard("Scratch", "Deals 3 points of damage", 1, 3);
        DamageCard pound = new DamageCard("Pound", "Deals 4 points of damage", 2, 4);
        DamageCard flareBlitz = new DamageCard("Flare Blitz", "Deals 6 points of damage", 4, 6);
        DamageCard thunderbolt = new DamageCard("Thunderbolt", "Deals 5 points of damage", 4, 5);
        DamageCard flamethrower = new DamageCard("Flamethrower", "Deals 8 points of damage", 5, 8);
        
        // Cartas de escudo
        ShieldCard harden = new ShieldCard("Harden", "Grants 2 points of shield", 1, 2);
        ShieldCard barrier = new ShieldCard("Barrier", "Grants 3 points of shield", 2, 3);
        ShieldCard shellArmor = new ShieldCard("Shell Armor", "Grants 5 points of shield",3, 5);
        ShieldCard acidArmor = new ShieldCard("Acid Armor", "Grants 7 points of shield", 4, 7);
        ShieldCard ironDefense = new ShieldCard("Iron Defense", "Grants 10 points of shield", 5, 10);

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
        for (Subscriber sub : subscribers) {
            sub.receivesNotification(event, this);
        }
    }

    // Não esquecer de fazer as chamadas do launchesNotification!!!
    public void startCombat() throws InterruptedException {
        int turn = 1;
        int fullEnergy = hero.getEnergy();
        System.out.println("A wild Pikachu has appeared!\n");

        while (hero.isAlive() && enemy.isAlive()) {
            
            hero.restoreEnergy(fullEnergy);
            hero.resetShield();
            
            int heroNumberOfCards = 5;
            for (int i = 0; i < heroNumberOfCards; i++) {
                deck.buy();
            }

            int move;

            clearScreen();

            while (true) {
                showBattle(hero, enemy);
                enemy.intent(turn);

                System.out.printf("\n%s, you're up! Choose your next move.\n", hero.getName());
                System.out.printf("Energy remaining: %d/%d\n", hero.getEnergy(), fullEnergy);

                for (int i = 0; i < deck.getPlayerHand().size(); i++) {
                    System.out.printf("%d: Use %s (Cost: %d) - %s\n", i+1, deck.getPlayerHand().get(i).getName(), deck.getPlayerHand().get(i).getEnergyCost(), deck.getPlayerHand().get(i).getDescription());
                }

                int endRoundOptionNumber = deck.getPlayerHand().size() + 1;
                System.out.printf("%d. End Round\n", endRoundOptionNumber);

                System.out.print("Your choice: ");
                
                move = scanner.nextInt();

                System.out.flush();

                if (move > 0 && move < endRoundOptionNumber) {
                int index = move - 1;
                
                Card selectedCard = deck.getPlayerHand().get(index);
                
                    if (hero.getEnergy() >= selectedCard.getEnergyCost()) {
                        deck.getPlayerHand().remove(index); 
                        selectedCard.use(hero, enemy, this);
                        deck.discard(selectedCard);
                        
                        System.out.printf("\n>>> %s used %s!\n", hero.getName(), selectedCard.getName());
                        
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
                turn = enemy.enemyTurn(hero, turn);

                Thread.sleep(3000);
            }
        }

        System.out.println("\nEnd of the game!");
        if (hero.isAlive()) {
            System.out.printf("%s rises victorious defeating %s!\n", hero.getName(), enemy.getName());
        } else {
            System.out.printf("%s triumphed over %s!\n", enemy.getName(), hero.getName());
        }

        scanner.close();
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void showBattle(Hero hero, Enemy enemy) {
        System.out.println("\n-------------------------------------------");
        System.out.printf("%s\nHP: %s | Energy: %d | Shield: %d\n", 
            hero.getName(), 
            createHealthBar(hero.getHealth(), hero.getMaxHealth(), 20),
            hero.getEnergy(), 
            hero.getShield());
        
        System.out.printf("VS.\n%s\nHP: %s | Shield: %d\n", 
            enemy.getName(), 
            createHealthBar(enemy.getHealth(), enemy.getMaxHealth(), 20),
            enemy.getShield());
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
