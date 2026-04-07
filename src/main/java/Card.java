

abstract public class Card {
    private String name;
    private String description;
    private int energyCost;

    public Card(String name, String description, int energyCost) {
        this.name = name;
        this.description = description;
        this.energyCost = energyCost;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getEnergyCost() {
        return energyCost;
    }
    
    abstract public void use(Hero hero, Entity entity, Manager manager);
}
