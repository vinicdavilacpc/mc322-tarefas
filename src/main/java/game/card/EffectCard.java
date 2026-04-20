package game.card;

import game.core.Battle;
import game.model.Entity;
import game.model.Hero;

/**
 * Representa uma carta com foco em efeitos (Buffs ou Debuffs).
 * Utiliza o padrão Command (através da interface EffectAction) para definir o efeito.
 */
public class EffectCard extends Card {
    public interface EffectAction {
        void apply(Hero hero, Entity target, Battle battle);
    }

    private EffectAction action;

    public EffectCard(String name, String description, int energyCost, EffectAction action, String color) {
        super(name, description, energyCost, color);
        this.action = action;
    }

    @Override
    public void use(Hero hero, Entity target, Battle battle) {
        this.action.apply(hero, target, battle);
        hero.drainEnergy(this.getEnergyCost());
    }
}