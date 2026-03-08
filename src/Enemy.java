public class Enemy {
    public String name;
    public int health;
    public int shield;

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
