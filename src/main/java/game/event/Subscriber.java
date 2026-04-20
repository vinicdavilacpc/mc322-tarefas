package game.event;

import game.core.Battle;

/**
 * Interface base para o padrão de projeto Observer.
 */
public interface Subscriber {
    void receivesNotification(GameEvent event, Battle battle);
}