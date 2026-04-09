/**
 * Interface/Classe base para o padrão de projeto Observer.
 * Define o contrato para objetos que precisam ser notificados sobre eventos
 * que ocorrem durante o loop principal do jogo.
 */
public abstract class Subscriber {
    /**
     * Método chamado pelo Manager (Publisher) quando um evento específico ocorre.
     *
     * @param event O nome do evento disparado (ex: "beginningOfRound", "endOfRound").
     * @param manager A instância do gerenciador que disparou o evento.
     */
    public abstract void receivesNotification(String event, Manager manager);
}
