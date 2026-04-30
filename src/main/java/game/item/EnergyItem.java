package game.item;

import game.map_event.Battle;
import game.model.Hero;

public class EnergyItem extends Item {
    private int energyAmount;

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