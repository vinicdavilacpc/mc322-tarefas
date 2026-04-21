package game.card;

import game.core.Battle;
import game.effect.Effect;
import game.model.Entity;
import game.model.Hero;

/**
 * Representa uma carta com foco defensivo. 
 * Ao ser usada, consome a energia do herói e aplica escudo.
 */
public class ShieldCard extends Card {
    private int defense;

    public ShieldCard(String name, String description, int energyCost, int defense, String color) {
        super(name, description, energyCost, color);
        this.defense = defense;
    }

    public int getDefense() { return defense; }

    @Override
    public void use(Hero hero, Entity target, Battle battle) {
        int totalDefense = this.defense;
        
        for (Effect effect : hero.getEffects()) {
            if (effect.getName().equals("Dexterity")) {
                totalDefense += effect.getAmount();
            }
        }

        hero.gainShield(totalDefense);
        hero.drainEnergy(this.getEnergyCost());
    }
}