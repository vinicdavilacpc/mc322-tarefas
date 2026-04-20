package game.card;

import game.core.Battle;
import game.model.Entity;
import game.model.Hero;
import game.view.Colors;

/**
 * Classe base abstrata para todas as cartas do jogo.
 * Define a estrutura básica de custo de energia e o comportamento polimórfico de uso.
 */
public abstract class Card {
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

    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getEnergyCost() { return energyCost; }
    public String getColoredName() { return this.color + this.name + Colors.RESET; }
    
    public abstract void use(Hero hero, Entity target, Battle battle);
}