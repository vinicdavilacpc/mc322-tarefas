package game.core;

import game.view.GameConsoleView;

public class App {
    public static void main(String[] args) throws InterruptedException {
        GameConsoleView view = new GameConsoleView();
        Manager manager = new Manager(view);
        manager.startGame(); 
    }
}