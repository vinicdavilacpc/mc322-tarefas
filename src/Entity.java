abstract public class Entity {
    private String name;
    private int health;
    private int shield;
    private int maxHealth;

    public Entity(String name, int health, int shield) {
        this.name = name;
        this.health = health;
        this.shield = shield;
        this.maxHealth = health;
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

    public int getMaxHealth() {
        return maxHealth;
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

    public void gainShield(int shieldValue) {
        this.shield += shieldValue;
    }

    public void resetShield() {
        this.shield = 0;
    }

    public boolean isAlive() {
        return this.health > 0;
    }
}
