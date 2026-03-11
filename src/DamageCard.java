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

    public void attack(Hero hero, Enemy enemy) {
        if (hero.getEnergy() >= this.getEnergyCost()) {
            System.out.printf("\n>>> %s used %s!\n", hero.getName(), this.getName());
            this.use(enemy, hero);
            if (!enemy.isAlive()) {
                System.out.printf("\nEnemy fainted! %s health is now 0\n", enemy.getName());
            } else {
                System.out.printf("\nEnemy hit! %s health is now %d\n", enemy.getName(), enemy.getHealth());
            }

            } else {
                System.out.println("\n>>> Not enough energy!\n");
            }
    }
}