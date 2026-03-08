public class ShieldCard {
    public String name;
    public int energyCost;
    public int defense;

    public ShieldCard(String name, int energyCost, int defense) {
        this.name = name;
        this.energyCost = energyCost;
        this.defense = defense;
    }

    public String getName() {
        return name;
    }

    public void activateShieldCard(Hero hero) {
        hero.getShield(defense);
        hero.drainsEnergy(energyCost);
    }
    
}
