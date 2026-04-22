package game.core;

import game.view.GameConsoleView;

/**
 * Classe principal da aplicação que inicia o jogo.
 */
public class App {
    /**
     * Método principal que serve como ponto de entrada do programa.
     *
     * @param args Argumentos de linha de comando (não utilizados).
     * @throws InterruptedException Caso a thread seja interrompida durante os atrasos de tempo do jogo.
     */
    public static void main(String[] args) throws InterruptedException {
        GameConsoleView view = new GameConsoleView();
        Manager manager = new Manager(view);
        manager.startGame(); 
    }
}