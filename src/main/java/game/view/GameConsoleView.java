package game.view;

import java.util.List;
import java.util.Scanner;

import game.card.Card;
import game.card.CardStack;
import game.effect.Effect;
import game.map.MapNode;
import game.model.Enemy;
import game.model.Hero;

/**
 * Classe responsável por gerenciar toda a entrada e saída via console.
 */
public class GameConsoleView {
    private Scanner scanner;

    public GameConsoleView() {
        this.scanner = new Scanner(System.in);
    }

    public void displayWildEncounter(Enemy enemy) {
        System.out.printf(Colors.YELLOW_BOLD + "A wild %s has appeared!\n" + Colors.RESET, enemy.getName());
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void showBattle(Hero hero, Enemy enemy) {
        System.out.println("\n-------------------------------------------");
        System.out.printf("%s\n", hero.getColoredName());
        System.out.printf("HP: %s | Energy: %d | Shield: %d\n",  
            createHealthBar(hero.getHealth(), hero.getMaxHealth(), 20),
            hero.getEnergy(), 
            hero.getShield());

        if (!hero.getEffects().isEmpty()) {
            System.out.println("Active effects: ");
            for (Effect e : hero.getEffects()) {
                System.out.printf("%s ", e.getString());
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

    private String createHealthBar(int currentHealth, int maxHealth, int barSize) {
        if (maxHealth <= 0) return "[ Error ]";

        currentHealth = Math.max(0, Math.min(currentHealth, maxHealth));
        double percentage = (double) currentHealth / maxHealth;
        int healthBlocks = (int) Math.round(percentage * barSize);

        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < barSize; i++) {
            bar.append(i < healthBlocks ? "█" : "░");
        }
        bar.append("] ").append(currentHealth).append("/").append(maxHealth);
        return bar.toString();
    }

    public void showIntent(Enemy enemy, int turn) {
        if (turn == 1) {
            System.out.printf("%s is powering up! (Damage: %s)\n", enemy.getColoredName(), enemy.getDamage());
        } else if (turn == 2) {
            System.out.printf("%s is raising their defense! (Shield: %s)\n", enemy.getColoredName(), enemy.getDefense());
        } else if (turn == 3) {
            System.out.printf("%s is getting stronger (Damage increase: %s)\n", enemy.getColoredName(), enemy.getStrength());
        }
    }

    public void showPlayerOptions(Hero hero, CardStack deck, int fullEnergy) {
        System.out.printf("\n%s, you're up! Choose your next move.\n", hero.getColoredName());
        System.out.printf("Energy remaining: %d/%d\n", hero.getEnergy(), fullEnergy);

        for (int i = 0; i < deck.getPlayerHand().size(); i++) {
            Card card = deck.getPlayerHand().get(i);
            System.out.printf("%d: Use %s (Cost: %d) - %s\n", 
                i + 1, card.getColoredName(), card.getEnergyCost(), card.getDescription());
        }

        int endRoundOptionNumber = deck.getPlayerHand().size() + 1;
        System.out.printf("%d. End Round\n", endRoundOptionNumber);
    }

    public int getPlayerMove() {
        System.out.print(Colors.BOLD + "Your choice: " + Colors.RESET);
        int choice = scanner.nextInt();
        System.out.flush();
        return choice;
    }

    public void displayCardUsage(Hero hero, Card card) {
        System.out.printf("\n>>> %s used %s!\n", hero.getColoredName(), card.getColoredName());
    }

    public void displayNotEnoughEnergy() {
        System.out.println("\n>>> Not enough energy!\n");
    }

    public void displayTurnEnding() {
        System.out.println("\n>>> Ending turn...");
    }

    public void displayInvalidChoice() {
        System.out.println("\n>>> Invalid choice!\n");
    }

    public void displayGameEnd(Hero hero, Enemy enemy) {
        System.out.println(Colors.RED_BOLD + "\nEnd of the game!" + Colors.RESET);
        if (hero.isAlive()) {
            System.out.printf("%s rises victorious defeating %s!\n", hero.getColoredName(), enemy.getColoredName());
        } else {
            System.out.printf("%s triumphed over %s!\n", enemy.getColoredName(), hero.getColoredName());
        }
    }

    public void displayEnemyAction(String actionText) {
        System.out.println(actionText);
    }

    public void displayEffectMessage(String message) {
        System.out.println(message);
    }

    /**
     * Exibe os caminhos disponíveis no mapa.
     */
    public void displayMapChoices(List<MapNode> choices) {
        System.out.println("\n===========================================");
        System.out.println(Colors.BLUE_BOLD + "   MAP   " + Colors.RESET);
        System.out.println("===========================================");
        System.out.println("Choose your next opponent:");
        
        for (int i = 0; i < choices.size(); i++) {
            MapNode node = choices.get(i);
            System.out.printf("%d: Go to %s (Enemy: %s)\n", 
                i + 1, node.getLocationName(), node.getEnemy().getColoredName());
        }
    }

    /**
     * Lê a escolha do caminho feita pelo jogador.
     */
    public int getMapChoice(int maxOption) {
        int choice = -1;
        while (choice < 1 || choice > maxOption) {
            System.out.print(Colors.BOLD + "Your path choice: " + Colors.RESET);
            choice = scanner.nextInt();
            System.out.flush();
            if (choice < 1 || choice > maxOption) {
                System.out.println("\n>>> Invalid path! Try again.\n");
            }
        }
        return choice;
    }
}