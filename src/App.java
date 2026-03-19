import java.util.Scanner;
import java.util.Stack;

public class App {
    public static void main(String[] args) {
        int turn = 1;
        Scanner scanner = new Scanner(System.in);

        // Entidades (Jogador e Inimigo)
        Hero hero = new Hero("Charmander", 20, 5, 0);
        Enemy enemy = new Enemy("Pikachu", 20, 0, 5, 3);

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

        CardStack deck = new CardStack(cards, discardCards);

        System.out.printf("A wild %s has appeared!\n", enemy.getName());

        int fullEnergy = hero.getEnergy();

        while (hero.isAlive() && enemy.isAlive()) {
            
            hero.restoreEnergy(fullEnergy);
            hero.resetShield();
            
            int heroNumberOfCards = 5;
            for (int i = 0; i < heroNumberOfCards; i++) {
                deck.buy();
            }

            int move;

            System.out.println("\n-------------------------------------------");
            System.out.printf("%s\n(Health: %d | Energy: %d | Shield: %d)\n", hero.getName(), hero.getHealth(), hero.getEnergy(), hero.getShield());
            System.out.printf("VS.\n%s\n(Health: %d | Shield: %d)\n", enemy.getName(), enemy.getHealth(), enemy.getShield());
            System.out.println("-------------------------------------------\n");

            enemy.intent(turn);

            while (true) {
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
                // Implementar try-catch para evitar erros do tipo: jogador digitar "a"

                if (move > 0 && move < endRoundOptionNumber) {
                int index = move - 1;
                
                Card selectedCard = deck.getPlayerHand().get(index);
                
                    if (hero.getEnergy() >= selectedCard.getEnergyCost()) {
                        deck.getPlayerHand().remove(index); 
                        selectedCard.use(hero, enemy);
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
            }

            deck.discardHand();

            // Turno do inimigo
            enemy.resetShield();
            if (enemy.isAlive()) {
                turn = enemy.enemyTurn(hero, turn); // passar um valor fixo de defense??
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
}