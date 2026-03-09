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
}