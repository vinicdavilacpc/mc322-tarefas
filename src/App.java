import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Hero hero = new Hero("Warrior", 20, 15, 0);
        Enemy enemy = new Enemy("Goblin", 15, 0);

        DamageCard sword = new DamageCard("Sword", 4, 6);
        ShieldCard shield = new ShieldCard("Shield", 3, 5);

        int fullEnergy = hero.getEnergy();

        while (hero.isAlive() && enemy.isAlive()) {
            
            hero.restoreEnergy(fullEnergy);
            hero.resetShield();
            
            int move = 0;

            System.out.println("\n-------------------------------------------");
            System.out.printf("Hero: %s\n(Health: %d | Energy: %d | Shield: %d)\n", hero.getName(), hero.getHealth(), hero.getEnergy(), hero.getShield());
            System.out.printf("VS.\nEnemy: %s\n(Health: %d | Shield: %d)\n", enemy.getName(), enemy.getHealth(), enemy.getShield());
            System.out.println("-------------------------------------------\n");

            while (true) {
                System.out.printf("%s, you're up! Choose your next move.\n", hero.getName());
                System.out.printf("Energy remaining: %d/%d\n", hero.getEnergy(), fullEnergy);
                System.out.printf("1: Take the Sword (Cost: %d, Damage: %d)\n", sword.getEnergyCost(), sword.getDamage());
                System.out.printf("2: Use the Shield (Cost: %d, Defense: %d)\n", shield.getEnergyCost(), shield.getDefense());
                System.out.printf("3: End round\n");
                System.out.print("Your choice: ");
                
                move = scanner.nextInt();

                // Se o jogador escolher a opção 1 (Usa a carta de dano)
                if (move == 1) {
                    if (hero.getEnergy() >= sword.getEnergyCost()) {
                        System.out.printf("\n>>> %s drawed the Sword!\n", hero.getName());
                        sword.use(enemy, hero);
                        System.out.printf("Enemy injured! %s's health is now %d.\n\n", enemy.getName(), enemy.getHealth());
                    } else {
                        System.out.println("\n>>> Not enough energy!\n");
                    }
                
                // Se o jogador escolher a opção 2 (Usa a carta de escudo)
                } else if (move == 2) {
                    if (hero.getEnergy() >= shield.getEnergyCost()) {
                        System.out.printf("\n>>> %s activated the Shield!\n", hero.getName());
                        shield.use(hero);
                        System.out.printf("%s gained defense! Shield is now %d.\n\n", hero.getName(), hero.getShield());
                    } else {
                        System.out.println("\n>>> Not enough energy!\n");
                    }

                // Se o jogador escolher a opção 3 (Encerrar turno)
                } else if (move == 3) {
                    System.out.println("\n>>> Ending turn...");
                    break;
                } else {
                    System.out.println("\n>>> Invalid choice!\n");
                }

                if (!enemy.isAlive()) {
                    break;
                }
            }

            if (enemy.isAlive()) {
                System.out.println("\n===========================================");
                System.out.printf("It's the enemy's turn! %s is choosing their move.\n", enemy.getName());
                int enemyAttackDamage = 5;
                System.out.printf("%s attacks for %d damage!\n", enemy.getName(), enemyAttackDamage);
                enemy.attack(hero, enemyAttackDamage);
                System.out.printf("%s's health is now %d.\n", hero.getName(), hero.getHealth());
                System.out.println("===========================================\n");
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