public class Enemy extends Entity {
    private int damage;

    public Enemy(String name, int health, int shield, int damage) {
        this.damage = damage;
        super(name, health, shield);
    }

    public int getDamage() {
        return damage;
    }

    public void attack(Hero hero, int damage) {
        hero.takeDamage(damage);
    }

    public int enemyTurn(Hero hero, int defense, int turn) {
        System.out.println("\n===========================================");
        System.out.printf("It's the opponent's turn! %s is choosing their move.\n", this.getName());

        if (turn == 1) {
            System.out.printf("%s attacks for %d damage!\n", this.getName(), this.getDamage());
            this.attack(hero, this.getDamage());
            System.out.printf("%s's health is now %d.\n", hero.getName(), hero.getHealth());
            System.out.println("===========================================\n");
            turn--;
        } else if (turn == 0) {
            System.out.printf("%s used the shield!\n", this.getName());
            gainShield(defense);
            System.out.printf("%s's shield is now %d.\n", this.getName(), defense);
            System.out.println("===========================================\n");
            turn++;
        }

        return turn;
    }
}