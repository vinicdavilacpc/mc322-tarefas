public class DamageCard extends Card {
    private int damage;

    public DamageCard(String name, String description, int energyCost, int damage) {
        super(name, description, energyCost);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public void use(Hero hero, Entity target) {
        target.takeDamage(damage);
        hero.drainEnergy(this.getEnergyCost());
    }

    public void attack(Hero hero, Entity target) {
        if (hero.getEnergy() >= this.getEnergyCost()) {
            System.out.printf("\n>>> %s used %s!\n", hero.getName(), this.getName());
            this.use(hero, target);
            if (!target.isAlive()) {
                System.out.printf("\nEnemy fainted! %s health is now 0\n", target.getName());
            } else {
                System.out.printf("\nEnemy hit! %s health is now %d\n", target.getName(), target.getHealth());
            }

            } else {
                System.out.println("\n>>> Not enough energy!\n");
            }
    }
}