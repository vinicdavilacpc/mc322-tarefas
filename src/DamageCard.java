public class DamageCard {
    private String name;
    private int energyCost;
    private int damage;

    public DamageCard(String name, int energyCost, int damage) {
        this.name = name;
        this.energyCost = energyCost;
        this.damage = damage;
    }

    public String getName() {
        return name;
    }

    public int getEnergyCost() {
        return energyCost;
    }

    public int getDamage() {
        return damage;
    }

    public void use(Enemy enemy, Hero hero) {
        enemy.takeDamage(damage);
        hero.drainEnergy(energyCost);
    }
}