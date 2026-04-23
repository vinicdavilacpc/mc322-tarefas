package game.effect;

import game.event.GameEvent;
import game.map_event.Battle;
import game.model.Entity;

/**
 * Representa um efeito que causa dano no alvo progressivamente ao longo das rodadas, 
 * finalizando após o acúmulo zerar.
 */
public class PoisonEffect extends Effect {
    /**
     * Construtor do Efeito de Veneno.
     *
     * @param name   Nome do efeito.
     * @param owner  A entidade envenenada.
     * @param amount Dano de veneno acumulado e turnos restantes de duração (se 1 dano / turno).
     */
    public PoisonEffect(String name, Entity owner, int amount) {
        super(name, owner, amount);
    }

    /**
     * Reage ao encerramento de rodada. Aplica dano direto pelo valor contido no efeito,
     * diminui a quantidade em 1. Se a quantidade chegar a zero, remove e cancela a inscrição do efeito.
     *
     * @param event  O evento recebido.
     * @param battle A instância de batalha que fornece contexto e exibe mensagens na view.
     */
    @Override
    public void receivesNotification(GameEvent event, Battle battle) {
        if (event == GameEvent.END_OF_ROUND) {
            battle.getView().displayEffectMessage(
                String.format(">> %s was poisoned and lost %d health points!", this.getOwner().getColoredName(), getAmount())
            );
            
            this.getOwner().takeDirectDamage(this.getAmount());
            this.addAmount(-1);

            if (this.getAmount() <= 0) {
                this.getOwner().getEffects().remove(this);
                battle.unsubscribe(this);
                battle.getView().displayEffectMessage(
                    String.format("%s is cured from the poison!", this.getOwner().getColoredName())
                );
            } else {
                battle.getView().displayEffectMessage(
                    String.format("Poison was reduced to %d.", getAmount())
                );
            }
        }
    }
}