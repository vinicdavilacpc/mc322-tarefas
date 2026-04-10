import game.core.Manager;
import game.model.Entity;
import game.model.Hero;

/**
 * Representa uma carta com foco defensivo. 
 * Ao ser usada, consome a energia do herói e aplica escudo direto ao alvo (quem usou a carta).
 */
public class ShieldCard extends Card {
    private int defense;

    public ShieldCard(String name, String description, int energyCost, int defense, String color) {
        super(name, description, energyCost, color);
        this.defense = defense;
    }

    public int getDefense() {
        return defense;
    }

    public void use(Hero hero, Entity target, Manager manager) {
        hero.gainShield(this.defense);
        hero.drainEnergy(this.getEnergyCost());
    }
}