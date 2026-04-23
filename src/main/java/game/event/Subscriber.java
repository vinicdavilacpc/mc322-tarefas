package game.event;

import game.map_event.Battle;

/**
 * Interface base para o padrão de projeto Observer.
 * Qualquer classe que a implemente pode ser inscrita em uma Batalha para
 * reagir aos eventos lançados.
 */
public interface Subscriber {
    /**
     * Método invocado quando o evento subscrito é notificado.
     *
     * @param event  O evento de ciclo de vida que ocorreu.
     * @param battle A batalha sobre a qual o evento atua.
     */
    void receivesNotification(GameEvent event, Battle battle);
}