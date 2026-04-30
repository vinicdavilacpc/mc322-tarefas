package game.item;

import game.map_event.Battle;
import game.model.Hero;
import game.view.Colors;

/**
 * Classe base abstrata para todos os itens consumíveis do jogo.
 */
public abstract class Item {
    private String name;
    private String description;
    private int price;
    private String color;

    public Item(String name, String description, int price, String color) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.color = color;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getPrice() { return price; }
    public String getColoredName() { return this.color + this.name + Colors.RESET; }

    /**
     * Aplica o efeito do item. Pode ser usado dentro ou fora de batalha.
     * @param hero O herói que está consumindo o item.
     * @param battle O contexto da batalha (pode ser null se usado no mapa/loja).
     */
    public abstract void consume(Hero hero, Battle battle);
}