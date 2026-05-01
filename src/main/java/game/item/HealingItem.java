package game.item;

import game.map_event.Battle;
import game.model.Hero;

/**
 * Representa um item consumível que restaura pontos de vida (HP) do herói.
 */
public class HealingItem extends Item {
    /** A quantidade de vida que será restaurada. */
    private int healAmount;

    /**
     * Construtor para o item de cura.
     * 
     * @param name Nome do item.
     * @param description Descrição do efeito curativo.
     * @param price Preço em PokeCoins.
     * @param healAmount Quantidade exata de HP restaurado.
     * @param color Cor ANSI.
     */
    public HealingItem(String name, String description, int price, int healAmount, String color) {
        super(name, description, price, color);
        this.healAmount = healAmount;
    }

    @Override
    public void consume(Hero hero, Battle battle) {
        hero.increaseHealth(this.healAmount);
        
        String message = String.format("\n>>> %s drank %s and recovered %d HP!", 
                                       hero.getColoredName(), this.getColoredName(), this.healAmount);
        
        if (battle != null && battle.getView() != null) {
            battle.getView().displayEffectMessage(message);
        } else {
            System.out.println(message);
        }
    }
}