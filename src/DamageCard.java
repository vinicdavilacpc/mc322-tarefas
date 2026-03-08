public class DamageCard {
    public String name;
    public int energyCost;
    public int damage;

    public DamageCard(String name, int energyCost, int damage) {
        this.name = name;
        this.energyCost = energyCost;
        this.damage = damage;
    }

    public String getName() {
        return name;
    }

    public void heroDamageCard(Enemy enemy, Hero hero) {
        if (enemy.shield > 0) {
            enemy.takeDamage(enemy.shield - damage);
        } else {
            enemy.takeDamage(damage);
        }
        hero.drainsEnergy(energyCost);
    }

    public void enemyDamageCard(Enemy enemy, Hero hero) {
        if (hero.shield > 0) {
            hero.takeDamage(hero.shield - damage);
        } else {
            hero.takeDamage(damage);
        }
    }
    
}
