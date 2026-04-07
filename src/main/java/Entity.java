

import java.util.ArrayList;

abstract public class Entity {
    private String name;
    private int health;
    private int shield;
    private int maxHealth;
    private ArrayList<Effect> effects;

    public Entity(String name, int health, int shield) {
        this.name = name;
        this.health = health;
        this.shield = shield;
        this.maxHealth = health;
        this.effects = new ArrayList<>();
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

    public ArrayList<Effect> getEffects() {
        return effects;
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

    public void applyEffect(Effect newEffect) {
        int repeats = 0;
        for (Effect effect : effects) {
            if (effect == newEffect) {
                effect.addAmount(newEffect.getAmount());
                repeats = 1;
                break;
            }
        }
        if (repeats == 0) {
            effects.add(newEffect);
        }
    }
}
