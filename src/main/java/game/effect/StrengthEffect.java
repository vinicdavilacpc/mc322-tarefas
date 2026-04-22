package game.effect;

import game.core.Battle;
import game.event.GameEvent;
import game.model.Entity;

/**
 * Representa um efeito que aumenta o dano causado por cartas com foco ofensivo.
 */
public class StrengthEffect extends Effect {
    /**
     * Construtor do efeito de Força.
     *
     * @param name   Nome do efeito.
     * @param owner  A entidade dona deste efeito.
     * @param amount Quantidade de aumento de dano concedido.
     */
    public StrengthEffect(String name, Entity owner, int amount) {
        super(name, owner, amount);
    }

    /**
     * Executa a resposta à notificação do evento, exibindo uma mensagem de fortalecimento 
     * do ataque no início da rodada.
     *
     * @param event  O evento propagado.
     * @param battle O contexto da batalha que disparou o evento.
     */
    @Override
    public void receivesNotification(GameEvent event, Battle battle) {
        if (event == GameEvent.BEGINNING_OF_ROUND) {
            battle.getView().displayEffectMessage(
                String.format("\n\n>> %s raised their attack and will deal %d more damage!", this.getOwner().getColoredName(), this.getAmount())
            );
        }
    }
}