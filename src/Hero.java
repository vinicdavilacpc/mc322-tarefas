public class Hero {
    public String name;
    public int health;
    public int energy;
    public int shield;

    public Hero(String name, int health, int energy, int shield) {
        this.name = name;
        this.health = health;
        this.energy = energy;
        this.shield = shield;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
    }

    public void drainsEnergy(int drainedEnergy) {
        this.energy -= drainedEnergy;
    }

    public void getShield(int shieldValue) {
        this.shield = shieldValue;
    }

    public int isAlive() {
        if (health > 0) {
            return 0;
        } else {
            return 1;
        }
    }
    
}
