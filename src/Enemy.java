public class Enemy {
    private String name;
    private int health;
    private int shield;

    public Enemy(String name, int health, int shield) {
        this.name = name;
        this.health = health;
        this.shield = shield;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
    }


    public void attack(Hero hero, int damage) {
        hero.takeDamage(damage);
    }

    public int getShield() {
        return shield;
    }
    
    public String isAlive() {
        if (health <= 0) {
            return name + "is still Standing";
        } else {
            return name + "was Slain";
        }
    }
}
