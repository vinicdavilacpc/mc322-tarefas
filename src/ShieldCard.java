public class ShieldCard {
    private String name;
    private int energyCost;
    private int defense;

    public ShieldCard(String name, int energyCost, int defense) {
        this.name = name;
        this.energyCost = energyCost;
        this.defense = defense;
    }

    public String getName() {
        return name;
    }

    public int getEnergyCost() {
        return energyCost;
    }

    public int getDefense() {
        return defense;
    }

    public void use(Hero hero) {
        hero.gainShield(defense);
        hero.drainEnergy(energyCost);
    }

    public void defense(Hero hero) {
        if (hero.getEnergy() >= this.getEnergyCost()) {
            System.out.printf("\n>>> %s used %s!\n", hero.getName(), this.getName());
            this.use(hero);
            System.out.printf("%s is protected! Defense is now %d.\n\n", hero.getName(), hero.getShield());
        } else {
            System.out.println("\n>>> Not enough energy!\n");
        }
    }
}