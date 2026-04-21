package game.model;

import game.core.Battle;
import game.effect.Effect;
import game.effect.StrengthEffect;
import game.view.GameConsoleView;

/**
 * Representa o adversário controlado pelo sistema.
 * Contém a lógica de tomada de decisão do inimigo durante o combate.
 */
public class Enemy extends Entity {
    private int damage;
    private int defense; 
    private int strength;

    public Enemy(String name, int health, int shield, int damage, int defense, int strength, String color) {
        super(name, health, shield, color);
        this.damage = damage;
        this.defense = defense;
        this.strength = strength;
    }

    public int getDamage() { return damage; }
    public int getDefense() { return defense; }
    public int getStrength() { return strength; }

    public int getTotalDamage() {
        int total = this.damage;
        for (Effect effect : this.getEffects()) {
            if (effect.getName().equals("Strength")) {
                total += effect.getAmount();
            }
        }
        return total;
    }

    public void attack(Hero hero, int damage) {
        hero.takeDamage(damage);
    }

    public int enemyTurn(Hero hero, int turn, Battle battle, GameConsoleView view) {
        view.displayEnemyAction("\n===========================================");
        view.displayEnemyAction(String.format("It's the opponent's turn! %s is choosing their move.", this.getColoredName()));

        if (turn == 1) {
            int realDamage = this.getTotalDamage();
            view.displayEnemyAction(String.format("%s attacks for %d damage!", this.getColoredName(), realDamage));
            this.attack(hero, realDamage);
            view.displayEnemyAction(String.format("%s's health is now %d.", hero.getColoredName(), hero.getHealth()));
            turn = 2;
            
        } else if (turn == 2) {
            view.displayEnemyAction(String.format("%s used the shield!", this.getColoredName()));
            this.gainShield(this.getDefense());
            view.displayEnemyAction(String.format("%s's shield is now %d.", this.getColoredName(), this.getShield()));
            turn = 3;
            
        } else if (turn == 3) {
            view.displayEnemyAction(String.format("%s raised their damage!", this.getColoredName()));
            StrengthEffect appliedEffect = new StrengthEffect("Strength", this, this.getStrength());
            boolean isNew = this.applyEffect(appliedEffect);
            if (isNew) {
                battle.subscribe(appliedEffect);
            }
            view.displayEnemyAction(String.format("%s's attack is now %d.", this.getColoredName(), this.getTotalDamage()));
            turn = 1;
        }

        view.displayEnemyAction("===========================================\n");
        return turn;
    }
}