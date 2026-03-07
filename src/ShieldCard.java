public class ShieldCard {
    private String name;
    private int energyCost;

    public ShieldCard(String name, int energyCost) {
        this.name = name;
        this.energyCost = energyCost;
    }

    public String getName() {
        return name;
    }

    public void activateShieldCard(Hero hero, int shieldValue, int energyCost) {
        hero.getShield(shieldValue);
        hero.drainsEnergy(energyCost);
    }
    
}
