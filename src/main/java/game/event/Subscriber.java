package game.event;

import game.core.Manager;

/**
 * Interface base para o padrão de projeto Observer.
 */
public interface Subscriber {
    void receivesNotification(GameEvent event, Manager manager);
}