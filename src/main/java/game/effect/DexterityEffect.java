package game.effect;

import game.core.Battle;
import game.event.GameEvent;
import game.model.Entity;

/**
 * Representa um efeito que aumenta o escudo recebido ao usar uma carta defensiva.
 */
public class DexterityEffect extends Effect {
    public DexterityEffect(String name, Entity owner, int amount) {
        super(name, owner, amount);
    }

    @Override
    public void receivesNotification(GameEvent event, Battle battle) {
        if (event == GameEvent.BEGINNING_OF_ROUND) {
            battle.getView().displayEffectMessage(
                String.format("\n\n>> %s raised their defense and will block %d more damage!", this.getOwner().getColoredName(), this.getAmount())
            );
        }
    }
}