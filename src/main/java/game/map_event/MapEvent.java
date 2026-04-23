package game.map_event;

import game.card.CardStack;
import game.model.Hero;
import game.view.GameConsoleView;

public abstract class MapEvent {
    public abstract boolean start(Hero hero, CardStack deck, GameConsoleView view) throws InterruptedException;

    public abstract String getDescription();
}
