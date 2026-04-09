/**
 * Representa uma carta com foco ofensivo. 
 * Ao ser usada, consome a energia do herói e aplica dano direto à saúde do alvo.
 */
public class DamageCard extends Card {
    private int damage;

    public DamageCard(String name, String description, int energyCost, int damage, String color) {
        super(name, description, energyCost, color);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public void use(Hero hero, Entity target, Manager manager) {
        int totalDamage = this.damage;

        for (Effect effect : hero.getEffects()) {
            if (effect.getName() == "Strength") {
                totalDamage += effect.getAmount();
            }
        }
        target.takeDamage(totalDamage);
        hero.drainEnergy(this.getEnergyCost());
    }
}