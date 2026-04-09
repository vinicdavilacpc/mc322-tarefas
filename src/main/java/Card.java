abstract public class Card {
    private String name;
    private String description;
    private int energyCost;
    private String color;

    public Card(String name, String description, int energyCost, String color) {
        this.name = name;
        this.description = description;
        this.energyCost = energyCost;
        this.color = color;
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

    public String getColoredName() {
        return this.color + this.name + Colors.RESET;
    }
    
    abstract public void use(Hero hero, Entity entity, Manager manager);
}
