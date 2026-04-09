public class Enemy extends Entity {
    private int damage;
    private int defense; 
    private int strength;

    public Enemy(String name, int health, int shield, int damage, int defense, int strength, String color) {
        super(name, health, shield, color);
        this.damage = damage;
        this.defense = defense;
        this.strength = strength;
    }

    public int getDamage() {
        return damage;
    }

    public int getDefense() {
        return defense;
    }

    public int getStrength() {
        return strength;
    }

    public void attack(Hero hero, int damage) {
        hero.takeDamage(damage);
    }

    public int enemyTurn(Hero hero, int turn, Manager manager) {
        System.out.println("\n===========================================");
        System.out.printf("It's the opponent's turn! %s is choosing their move.\n", this.getColoredName());

        if (turn == 1) {
            System.out.printf("%s attacks for %d damage!\n", this.getColoredName(), this.getDamage());
            this.attack(hero, this.getDamage());
            System.out.printf("%s's health is now %d.\n", hero.getColoredName(), hero.getHealth());
            System.out.println("===========================================\n");
            turn = 2;
        } else if (turn == 2) {
            System.out.printf("%s used the shield!\n", this.getColoredName());
            gainShield(this.getDefense());
            System.out.printf("%s's shield is now %d.\n", this.getColoredName(), this.getDefense());
            System.out.println("===========================================\n");
            turn = 3;
        } else if (turn == 3) {
            System.out.printf("%s raised their damage!\n", this.getColoredName());
            StrengthEffect appliedEffect = new StrengthEffect("Strength", hero, this.getStrength());
            this.applyEffect(appliedEffect);
            manager.subscribe(appliedEffect);
            System.out.printf("%s's attack is now %d.\n", this.getColoredName(), this.getStrength());
            System.out.println("===========================================\n");
            turn = 1;
        }

        return turn;
    }

    public void intent(int turn) {
        if (turn == 1) {
            System.out.printf("%s is powering up! (Damage: %s)\n", this.getColoredName(), this.getDamage());
        } else if (turn == 2) {
            System.out.printf("%s is raising their defense! (Shield: %s)\n", this.getColoredName(), this.getDefense());
        } else if (turn == 3) {
            System.out.printf("%s is getting stronger (Damage increase: %s)\n", this.getColoredName(), this.getStrength());
        }
    }
}