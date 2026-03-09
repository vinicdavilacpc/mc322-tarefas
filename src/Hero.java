public class Hero {
    private String name;
    private int health;
    private int energy;
    private int shield;

    public Hero(String name, int health, int energy, int shield) {
        this.name = name;
        this.health = health;
        this.energy = energy;
        this.shield = shield;
    }

    public String getName() {
        return name;
    }
    
    public int getHealth() {
        return health;
    }

    public int getEnergy() {
        return energy;
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

    public void drainEnergy(int drainedEnergy) {
        this.energy -= drainedEnergy;
    }

    public void restoreEnergy(int fullEnergy) {
        this.energy = fullEnergy;
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