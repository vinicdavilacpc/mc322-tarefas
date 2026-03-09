import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        // System.out.print("Enter the hero's name: ");
        // String nameHero = scanner.nextLine();

        // System.out.print("Enter the hero's health: ");
        // int healthHero = scanner.nextInt(); // vida total, se chegar em 0, acaba o jogo

        // System.out.print("Enter the hero's energy: ");
        // int energyHero = scanner.nextInt(); // energia total, é recarregada a cada rodada

        // System.out.print("Enter the enemy's name: ");
        // String nameEnemy = scanner.nextLine();

        // System.out.print("Enter the enemy's health: ");
        // int healthEnemy = scanner.nextInt(); 

        Hero hero = new Hero("Warrior", 20, 15, 0);

        Enemy enemy = new Enemy("Goblin", 15, 0);

        DamageCard sword = new DamageCard("Sword", 4, 6);

        ShieldCard shield = new ShieldCard("Shield", 3, 5);

        int fullEnergy;
        fullEnergy = hero.energy;
        int fimJogo = 0;

        while (fimJogo == 0) { // loop para cada turno 

            hero.energy = fullEnergy; // restaura a energia a cada turno
            int move = 0;

            System.out.printf("Hero: %s\n(Health: %d | Energy: %d | Shield: %d)\n", hero.name, hero.health, hero.energy, hero.shield);
            System.out.printf("VS.\nEnemy: %s\n(Health: %d | Shield: %d)\n", enemy.name, enemy.health, enemy.shield);
            System.out.println();

            while (hero.energy > 0 || move != 3) { // loop para cada ataque em um turno (encerra quando acabar a energia ou forçado)
                System.out.printf("%s, you're up! Choose your next move.\n", hero.name);
                System.out.printf("Energy remaining: %d/%d\n", hero.energy, fullEnergy);
                System.out.printf("1: Take the Sword\n2: Use the Shield\n3: End round\n");
                System.out.print("Your choice: ");
                int choice = scanner.nextInt();
                move = choice;

                if (move == 1) {
                    System.out.printf("%s drawed the Sword!\n", hero.name);
                    if (hero.energy < sword.energyCost) {
                        System.out.printf("%s doesn't have enough energy.\n", hero.name);
                        move = 3;
                    } else {
                        sword.heroDamageCard(enemy, hero);
                        System.out.printf("Enemy injured! %s took %d damage.\n", enemy.name, sword.damage - enemy.shield);
                        fimJogo = enemy.isAlive();
                        if (fimJogo == 1) {
                            break;
                        }
                    }

                } else if (move == 2) {
                    System.out.printf("%s activated the Shield\n", hero.name);
                    if (hero.energy < sword.energyCost) {
                        System.out.printf("%s doesn't have enough energy.\n", hero.name);
                        move = 3;
                    } else {
                        shield.activateShieldCard(hero);
                    }
                }

                System.out.printf("It's the enemy's turn! %s is choosing their move.\n", enemy.name);
                System.out.printf("%s drawed the Sword!\n", enemy.name);
                sword.enemyDamageCard(enemy, hero);
                System.out.printf("%s is wounded and lost %d health points!\n", hero.name, sword.damage - hero.shield);
                fimJogo = hero.isAlive();
                if (fimJogo == 1) {
                    break;
                }
            }
        }

        System.out.println("End of the game!");
        if (enemy.health <= 0) {
            System.out.printf("%s rises victorious defeating %s!\n", hero.name, enemy.name);
        } else {
            System.out.printf("%s triumphed over %s!", hero.name, enemy.name);
        }

        scanner.close();

    }
}
