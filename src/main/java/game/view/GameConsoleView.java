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
 * Contém funções para exibir telas de batalha, gerenciar inputs de escolhas dos usuários e desenhar dados estatísticos.
 */
public class GameConsoleView {
    /** Objeto Scanner utilizado para captar inserções de escolhas do jogador na CLI. */
    private Scanner scanner;

    /**
     * Construtor da View do Jogo de Console.
     */
    public GameConsoleView() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Exibe o encontro surpresa com um novo inimigo.
     *
     * @param enemy O inimigo encontrado.
     */
    public void displayWildEncounter(Enemy enemy) {
        System.out.printf(Colors.YELLOW_BOLD + "A wild %s has appeared!\n" + Colors.RESET, enemy.getName());
    }

    /**
     * Limpa o console de execução em sistemas compatíveis utilizando ANSI escapes.
     */
    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Exibe a interface textual contendo as barras de vida, escudo, energia, status de heróis e do inimigo.
     *
     * @param hero  Herói do jogador atual.
     * @param enemy Inimigo oponente.
     */
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

    /**
     * Cria e preenche textualmente uma representação baseada em barra para a vida atual.
     *
     * @param currentHealth A saúde base atual (numérica).
     * @param maxHealth     A saúde total máxima (numérica).
     * @param barSize       A largura base (em caracteres) que fará a barra cheia.
     * @return Uma String contendo a barra desenhada (ex: [████░░]).
     */
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

    /**
     * Imprime na tela qual é a provável intenção ou ação que o inimigo tomará no seu turno.
     *
     * @param enemy O inimigo sendo observado.
     * @param turn  O estado do turno atual do inimigo (1 - Attack, 2 - Defense, 3 - Buff).
     */
    public void showIntent(Enemy enemy, int turn) {
        if (turn == 1) {
            System.out.printf("%s is powering up! (Damage: %s)\n", enemy.getColoredName(), enemy.getTotalDamage());
        } else if (turn == 2) {
            System.out.printf("%s is raising their defense! (Shield: %s)\n", enemy.getColoredName(), enemy.getDefense());
        } else if (turn == 3) {
            System.out.printf("%s is getting stronger (Damage increase: %s)\n", enemy.getColoredName(), enemy.getStrength());
        }
    }

    /**
     * Exibe o menu com as opções de cartas disponíveis para uso baseadas na mão em curso do jogador.
     *
     * @param hero       O herói (utilizado para pegar o status atual).
     * @param deck       O baralho em uso.
     * @param fullEnergy O total de energia na barra (apenas para exibição da capacidade total).
     */
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

    /**
     * Obtém do console o número da ação que o jogador selecionou, tratando como inteiro (via Scanner).
     *
     * @return O número da jogada ou escolha no menu.
     */
    public int getPlayerMove() {
        System.out.print(Colors.BOLD + "Your choice: " + Colors.RESET);
        int choice = scanner.nextInt();
        System.out.flush();
        return choice;
    }

    /**
     * Imprime a notificação de que o jogador gastou uma carta.
     *
     * @param hero O herói jogando.
     * @param card A carta sendo utilizada.
     */
    public void displayCardUsage(Hero hero, Card card) {
        System.out.printf("\n>>> %s used %s!\n", hero.getColoredName(), card.getColoredName());
    }

    /** Exibe o alerta de falta de energia de que a ação requer. */
    public void displayNotEnoughEnergy() {
        System.out.println("\n>>> Not enough energy!\n");
    }

    /** Exibe a mensagem sinalizando a decisão voluntária de acabar a rodada de escolhas. */
    public void displayTurnEnding() {
        System.out.println("\n>>> Ending turn...");
    }

    /** Imprime o alerta de que o usuário digitou uma opção fora do escopo ou inválida. */
    public void displayInvalidChoice() {
        System.out.println("\n>>> Invalid choice!\n");
    }

    /**
     * Desenha o momento e resolução final da batalha em tela.
     *
     * @param hero  O Herói desafiante.
     * @param enemy O Inimigo desafiado.
     */
    public void displayGameEnd(Hero hero, Enemy enemy) {
        System.out.println(Colors.RED_BOLD + "\nEnd of the game!" + Colors.RESET);
        if (hero.isAlive()) {
            System.out.printf("%s rises victorious defeating %s!\n", hero.getColoredName(), enemy.getColoredName());
        } else {
            System.out.printf("%s triumphed over %s!\n", enemy.getColoredName(), hero.getColoredName());
        }
    }

    /**
     * Interface para que o inimigo print as suas ações narrativas na tela.
     *
     * @param actionText O texto provido pela IA do monstro.
     */
    public void displayEnemyAction(String actionText) {
        System.out.println(actionText);
    }

    /**
     * Usado por Efeitos/Subscribers para narrar ocorrências causadas por status do jogo no console.
     *
     * @param message A string notificando a ocorrência.
     */
    public void displayEffectMessage(String message) {
        System.out.println(message);
    }

    /**
     * Exibe os caminhos (nós vizinhos) disponíveis no mapa após vencer um encontro.
     * * @param choices A lista dos próximos Nodes elegíveis na árvore.
     */
    public void displayMapChoices(List<MapNode> choices) {
        System.out.println("\n===========================================");
        System.out.println(Colors.BLUE_BOLD + "   MAP   " + Colors.RESET);
        System.out.println("===========================================");
        System.out.println("Choose your next opponent:");
        
        for (int i = 0; i < choices.size(); i++) {
            MapNode node = choices.get(i);

            String buffText = node.getRewardType().equals("HEALTH") ? 
                "+" + node.getRewardAmount() + " Max Health" : 
                "+" + node.getRewardAmount() + " Max Energy";

            String eventInfo = node.getEvent().getDescription();
            
            System.out.printf(Colors.BOLD + "%d: [%s]" + Colors.RESET + " -> " + eventInfo + "| Reward: " + Colors.RESET + "%d Poke Coins & %s%n",
                    (i + 1), node.getColoredLocationName(), node.getPokeCoinReward(), buffText);
            
        }
    }

    public void displayRewardReceived(int gold, String buffReward, int buffAmount) {
        String buffText = buffReward.equals("HEALTH") ? 
            "+" + buffAmount + " Max Health" : 
            "+" + buffAmount + " Max Energy";
        System.out.println(Colors.GREEN_BOLD + "\n>>> Rewards claimed: " + gold + " Poke Coins and " + buffText + "!" + Colors.RESET);
    }

    /**
     * Lê a escolha do caminho no mapa feita pelo jogador de modo interativo validado.
     *
     * @param maxOption A quantidade máxima de caminhos listados disponíveis (O tamanho do List de MapNode provido).
     * @return O id (index-based 1,2,3) da decisão validada.
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

    /**
     * Renderiza o menu de seleção inicial do jogo e coleta a entrada.
     *
     * @return O número 1, 2 ou 3 correspondente ao inicial escolhido.
     */
    public int getHeroChoice() {
        System.out.printf(Colors.BOLD + "\nChoose your pokemon! \n\n" + Colors.RESET);
        System.out.println("1: " + Colors.ORANGE_BOLD + "Charmander" + Colors.RESET);
        System.out.println("2: " + Colors.BLUE_BOLD + "Squirtle" + Colors.RESET);
        System.out.println("3: " + Colors.GREEN_BOLD + "Bulbasaur" + Colors.RESET);
        
        return getPlayerMove();
    }
}