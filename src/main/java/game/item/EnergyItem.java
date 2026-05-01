package game.item;

import game.map_event.Battle;
import game.model.Hero;

/**
 * Representa um item consumível que restaura pontos de energia do herói durante o seu turno.
 */
public class EnergyItem extends Item {
    /** A quantidade de energia que será restaurada. */
    private int energyAmount;

    /**
     * Construtor para o item de energia.
     * 
     * @param name Nome do item.
     * @param description Descrição do efeito energético.
     * @param price Preço em PokeCoins.
     * @param energyAmount Quantidade exata de energia restaurada.
     * @param color Cor ANSI.
     */
    public EnergyItem(String name, String description, int price, int energyAmount, String color) {
        super(name, description, price, color);
        this.energyAmount = energyAmount;
    }

    @Override
    public void consume(Hero hero, Battle battle) {
        int currentEnergy = hero.getEnergy();
        int maxEnergy = hero.getMaxEnergy();
        int newEnergy = Math.min(currentEnergy + this.energyAmount, maxEnergy);
        
        hero.restoreEnergy(newEnergy);

        String message = String.format("\n>>> %s used %s and gained %d Energy!", hero.getColoredName(), this.getColoredName(), this.energyAmount);
        
        if (battle != null && battle.getView() != null) {
            battle.getView().displayEffectMessage(message);
        } else {
            System.out.println(message);
        }
    }
}