package game.effect;
import game.card.Card;
import game.core.Manager;
import game.model.Entity;
import game.model.Hero;

/**
 * Representa uma carta com foco em efeitos.
 */
public class EffectCard extends Card {
    private String effectType;
    private int effectAmount;

    public EffectCard(String name, String description, int energyCost, String effectType, int effectAmount, String color) {
        super(name, description, energyCost, color);
        this.effectType = effectType;
        this.effectAmount = effectAmount;
    }

    public void use(Hero hero, Entity target, Manager manager) {
        Effect appliedEffect = null;
        if (effectType == "Poison") {
            appliedEffect = new PoisonEffect("Poison", target, this.effectAmount);
            target.applyEffect(appliedEffect);
            manager.subscribe(appliedEffect);
        } else if (effectType == "Strength") {
            appliedEffect = new StrengthEffect("Strength", target, this.effectAmount);
            hero.applyEffect(appliedEffect);
            manager.subscribe(appliedEffect);
        } else if (effectType == "Dexterity") {
            appliedEffect = new DexterityEffect("Dexterity", target, this.effectAmount);
            hero.gainShield(effectAmount);
            hero.applyEffect(appliedEffect);
            manager.subscribe(appliedEffect);
        }

        hero.drainEnergy(this.getEnergyCost());
    }
}
