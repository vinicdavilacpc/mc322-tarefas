package game.core;

import game.view.GameConsoleView;

/**
 * Classe principal que executa o jogo.
 * Ponto de entrada da aplicação.
 */
public class App {
    public static void main(String[] args) throws InterruptedException {
        GameConsoleView view = new GameConsoleView();
        Manager manager = new Manager(view);
        manager.startCombat();
    }
}