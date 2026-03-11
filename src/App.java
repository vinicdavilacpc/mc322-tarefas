import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        int turn = 1;
        Scanner scanner = new Scanner(System.in);

        Hero hero = new Hero("Charmander", 20, 5, 0);
        Enemy enemy = new Enemy("Pikachu", 20, 0);

        // Cartas de dano para o Charmander
        DamageCard flamethrower = new DamageCard("Flamethrower", 4, 8);
        DamageCard scratch = new DamageCard("Scratch", 2, 4);

        // Cartas de escudo para o Charmander
        ShieldCard shellArmor = new ShieldCard("Shell Armor", 3, 5);
        ShieldCard ironDefense = new ShieldCard("Iron Defense", 5, 10);

        // Cartas para o Pikachu
        DamageCard thunderbolt = new DamageCard("Thunderbolt", 0, 5);
        ShieldCard barrier = new ShieldCard("Barrier", 0, 3);

        System.out.printf("A wild %s has appeared!\n", enemy.getName());

        int fullEnergy = hero.getEnergy();

        while (hero.isAlive() && enemy.isAlive()) {
            
            hero.restoreEnergy(fullEnergy);
            hero.resetShield();
            
            int move;

            System.out.println("\n-------------------------------------------");
            System.out.printf("%s\n(Health: %d | Energy: %d | Shield: %d)\n", hero.getName(), hero.getHealth(), hero.getEnergy(), hero.getShield());
            System.out.printf("VS.\n%s\n(Health: %d | Shield: %d)\n", enemy.getName(), enemy.getHealth(), enemy.getShield());
            System.out.println("-------------------------------------------\n");

            while (true) {
                System.out.printf("%s, you're up! Choose your next move.\n", hero.getName());
                System.out.printf("Energy remaining: %d/%d\n", hero.getEnergy(), fullEnergy);
                System.out.printf("1: Use %s (Cost: %d, Damage: %d)\n", scratch.getName(), scratch.getEnergyCost(), scratch.getDamage());
                System.out.printf("2: Use %s (Cost: %d, Damage: %d)\n", flamethrower.getName(), flamethrower.getEnergyCost(), flamethrower.getDamage());
                System.out.printf("3: Use %s (Cost: %d, Defense: %d)\n", shellArmor.getName(), shellArmor.getEnergyCost(), shellArmor.getDefense());
                System.out.printf("4: Use %s (Cost: %d, Defense: %d)\n", ironDefense.getName(), ironDefense.getEnergyCost(), ironDefense.getDefense());
                System.out.printf("5: End round\n");
                System.out.print("Your choice: ");
                
                move = scanner.nextInt();

                if (move == 1) {
                    scratch.attack(hero, enemy);
                    
                } else if (move == 2) {
                    flamethrower.attack(hero, enemy);
                
                } else if (move == 3) {
                    shellArmor.defense(hero);

                } else if (move == 4) {
                    ironDefense.defense(hero);
                    
                } else if (move == 5) {
                    System.out.println("\n>>> Ending turn...");
                    break;
                } else {
                    System.out.println("\n>>> Invalid choice!\n");
                }

                if (!enemy.isAlive()) {
                    break;
                }
            }

            // Turno do inimigo
            enemy.resetShield();
            if (enemy.isAlive()) {
                turn = enemy.enemyTurn(hero, thunderbolt, barrier, turn);
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