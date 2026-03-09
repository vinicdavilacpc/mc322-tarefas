public class Enemy {
    private String name;
    private int health;
    private int shield;

    public Enemy(String name, int health, int shield) {
        this.name = name;
        this.health = health;
        this.shield = shield;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getShield() {
        return shield;
    }

    public void takeDamage(int damage) {
        if (this.shield >= damage) {
            this.shield -= damage;
        } else {
            int remainingDamage = damage - this.shield;
            this.shield = 0;
            this.health -= remainingDamage;
        }
    }

    public void attack(Hero hero, int damage) {
        hero.takeDamage(damage);
    }
    
    public boolean isAlive() {
        return this.health > 0;
    }
}