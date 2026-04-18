package game.card;

import game.core.Manager;
import game.model.Entity;
import game.model.Hero;

/**
 * Representa uma carta com foco em efeitos (Buffs ou Debuffs).
 * Utiliza o padrão Command (através da interface EffectAction) para definir o efeito.
 */
public class EffectCard extends Card {
    public interface EffectAction {
        void apply(Hero hero, Entity target, Manager manager);
    }

    private EffectAction action;

    public EffectCard(String name, String description, int energyCost, EffectAction action, String color) {
        super(name, description, energyCost, color);
        this.action = action;
    }

    @Override
    public void use(Hero hero, Entity target, Manager manager) {
        this.action.apply(hero, target, manager);
        hero.drainEnergy(this.getEnergyCost());
    }
}