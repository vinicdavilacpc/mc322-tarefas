package game.map_event;

import game.card.CardStack;
import game.model.Hero;
import game.view.GameConsoleView;

/**
 * Classe base abstrata para todos os eventos que podem ocorrer em um nó do mapa.
 * Pode representar batalhas, lojas, escolhas ou outros encontros.
 */
public abstract class MapEvent {
    
    /**
     * Inicia e executa o evento do mapa.
     * 
     * @param hero O herói controlado pelo jogador.
     * @param deck O baralho atual de cartas do jogador.
     * @param view A interface responsável pela exibição e coleta de inputs no console.
     * @return true se o herói sobreviveu ao evento, false caso tenha sido derrotado.
     * @throws InterruptedException Caso a thread seja interrompida durante os atrasos (Thread.sleep).
     */
    public abstract boolean start(Hero hero, CardStack deck, GameConsoleView view) throws InterruptedException;

    /**
     * Retorna a descrição do evento para ser exibida nas opções do mapa.
     * 
     * @return A string formatada com a descrição do evento.
     */
    public abstract String getDescription();
}
