package game.effect;

import game.core.Battle;
import game.event.GameEvent;
import game.model.Entity;

/**
 * Representa um efeito que aumenta o dano causado por cartas com foco ofensivo.
 */
public class StrengthEffect extends Effect {
    public StrengthEffect(String name, Entity owner, int amount) {
        super(name, owner, amount);
    }

    @Override
    public void receivesNotification(GameEvent event, Battle battle) {
        if (event == GameEvent.BEGINNING_OF_ROUND) {
            battle.getView().displayEffectMessage(
                String.format("\n\n>> %s raised their attack and will deal %d more damage!", this.getOwner().getColoredName(), this.getAmount())
            );
        }
    }
}