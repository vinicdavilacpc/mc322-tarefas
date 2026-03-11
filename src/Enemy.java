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

    public void gainShield(int shieldValue) {
        this.shield += shieldValue;
    }

    public void resetShield() {
        this.shield = 0;
    }

    public void attack(Hero hero, int damage) {
        hero.takeDamage(damage);
    }
    
    public boolean isAlive() {
        return this.health > 0;
    }

    public int enemyTurn(Hero hero, DamageCard damage, ShieldCard shield, int turn) {
        System.out.println("\n===========================================");
        System.out.printf("It's the opponent's turn! %s is choosing their move.\n", this.getName());

        if (turn == 1) {
            System.out.printf("%s attacks for %d damage!\n", this.getName(), damage.getDamage());
            this.attack(hero, damage.getDamage());
            System.out.printf("%s's health is now %d.\n", hero.getName(), hero.getHealth());
            System.out.println("===========================================\n");
            turn--;
        } else if (turn == 0) {
            System.out.printf("%s used %s!\n", this.getName(), shield.getName());
            gainShield(shield.getDefense());
            System.out.printf("%s's shield is now %d.\n", this.getName(), shield.getDefense());
            System.out.println("===========================================\n");
            turn++;
        }

        return turn;
    }
}