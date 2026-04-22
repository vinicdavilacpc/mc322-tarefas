package game.effect;

import game.core.Battle;
import game.event.GameEvent;
import game.model.Entity;

/**
 * Representa um efeito que aumenta o escudo recebido ao usar uma carta defensiva.
 */
public class DexterityEffect extends Effect {
    /**
     * Construtor do Efeito de Destreza.
     *
     * @param name   Nome do efeito.
     * @param owner  Entidade dona deste efeito.
     * @param amount Quantidade adicional adicionada na ativação de cartas de escudo.
     */
    public DexterityEffect(String name, Entity owner, int amount) {
        super(name, owner, amount);
    }

    /**
     * Notifica o usuário no início de rodada de que a entidade recebeu buff em seus escudos.
     *
     * @param event  O evento sendo notificado.
     * @param battle A batalha emitindo o evento.
     */
    @Override
    public void receivesNotification(GameEvent event, Battle battle) {
        if (event == GameEvent.BEGINNING_OF_ROUND) {
            battle.getView().displayEffectMessage(
                String.format("\n\n>> %s raised their defense and will block %d more damage!", this.getOwner().getColoredName(), this.getAmount())
            );
        }
    }
}