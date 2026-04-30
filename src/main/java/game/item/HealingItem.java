package game.item;

import game.map_event.Battle;
import game.model.Hero;

public class HealingItem extends Item {
    private int healAmount;

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