package game.event;

/**
 * Representa os eventos do ciclo de vida de um turno no jogo.
 */
public enum GameEvent {
    /** Evento deflagrado quando uma nova rodada começa, antes da vez do jogador. */
    BEGINNING_OF_ROUND,
    /** Evento deflagrado após a finalização do turno do oponente, encerrando a rodada. */
    END_OF_ROUND
}