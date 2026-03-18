public class ShieldCard extends Card {
    private int defense;

    public ShieldCard(String name, String description, int energyCost, int defense) {
        super(name, description, energyCost);
        this.defense = defense;
    }

    public int getDefense() {
        return defense;
    }

    public void use(Hero hero, Entity target) {
        target.gainShield(defense);
        hero.drainEnergy(this.getEnergyCost());
    }

    public void defense(Hero hero, Entity target) {
        if (hero.getEnergy() >= this.getEnergyCost()) {
            System.out.printf("\n>>> %s used %s!\n", hero.getName(), this.getName());
            this.use(hero, target);
            System.out.printf("%s is protected! Defense is now %d.\n\n", hero.getName(), hero.getShield());
        } else {
            System.out.println("\n>>> Not enough energy!\n");
        }
    }
}