public class DamageCard {
    private String name;
    private int energyCost;

    public DamageCard(String name, int energyCost) {
        this.name = name;
        this.energyCost = energyCost;
    }

    public String getName() {
        return name;
    }

    public void activateDamageCard(Enemy enemy, Hero hero, int damage, int energyCost) {
        enemy.takeDamage(damage);
        hero.drainsEnergy(energyCost);
    }
    
}
