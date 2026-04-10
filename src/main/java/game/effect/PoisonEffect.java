package game.effect;

import game.core.Manager;
import game.event.GameEvent;
import game.model.Entity;

/**
 * Representa um efeito que causa dano no alvo progressivamente ao longo das rodadas, 
 * finalizando após o acúmulo zerar.
 */
public class PoisonEffect extends Effect {
    public PoisonEffect(String name, Entity owner, int amount) {
        super(name, owner, amount);
    }

    @Override
    public void receivesNotification(GameEvent event, Manager manager) {
        if (event == GameEvent.END_OF_ROUND) {
            manager.getView().displayEffectMessage(
                String.format(">> %s was poisoned and lost %d health points!", this.getOwner().getColoredName(), getAmount())
            );
            
            this.getOwner().takeDamage(this.getAmount());
            this.addAmount(-1);

            if (this.getAmount() <= 0) {
                this.getOwner().getEffects().remove(this);
                manager.unsubscribe(this);
                manager.getView().displayEffectMessage(
                    String.format("%s is cured from the poison!", this.getOwner().getColoredName())
                );
            } else {
                manager.getView().displayEffectMessage(
                    String.format("Poison was reduced to %d.", getAmount())
                );
            }
        }
    }
}