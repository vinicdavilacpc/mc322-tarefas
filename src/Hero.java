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

    public void takeDamage(int damage) {
        this.health -= damage;
    }

    public void drainsEnergy(int drainedEnergy) {
        this.energy -= drainedEnergy;
    }

    public void getShield(int shieldValue) {
        this.shield = shieldValue;
    }

    public String isAlive() {
        if (health > 0) {
            return name + "is still Standing";
        } else {
            return name + "was Slain";
        }
    }
    
}
