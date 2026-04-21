package game.model;

import game.card.CardStack;
import game.core.Battle;
import game.effect.Effect;
import game.effect.StrengthEffect;
import game.view.GameConsoleView;
import java.util.Stack;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnemyTest {
    @Test
    public void testDamageWithStrength() {
        Enemy enemy = new Enemy("Pikachu", 20, 0, 5, 3, 2, "");

        assertEquals(5, enemy.getTotalDamage());

        enemy.applyEffect(new StrengthEffect("Strength", enemy, 3));
        
        assertEquals(8, enemy.getTotalDamage());
    }

    @Test
    public void testAttackOnHero() {
        Enemy enemy = new Enemy("Pikachu", 20, 0, 5, 3, 2, "");
        Hero hero = new Hero("Charmander", 20, 5, 0, "");
        
        enemy.attack(hero, 7); // Simulando o dano que o enemyTurn passa
        
        assertEquals(13, hero.getHealth());
    }

    private Battle createBattle(Hero hero, Enemy enemy) {
        GameConsoleView view = new GameConsoleView();
        CardStack deck = new CardStack(new Stack<>(), new Stack<>());
        return new Battle(hero, enemy, deck, view);
    }

    @Test
    public void testTurn1Attack() {
        Enemy enemy = new Enemy("Pikachu", 20, 0, 5, 3, 2, "");
        Hero hero = new Hero("Charmander", 20, 5, 0, "");
        Battle battle = createBattle(hero, enemy);

        int nextTurn = enemy.enemyTurn(hero, 1, battle, battle.getView());

        assertEquals(15, hero.getHealth());
        assertEquals(2, nextTurn);
    }

    @Test
    public void testTurn2Defense() {
        Enemy enemy = new Enemy("Pikachu", 20, 0, 5, 3, 2, "");
        Hero hero = new Hero("Charmander", 20, 5, 0, "");
        Battle battle = createBattle(hero, enemy);

        int nextTurn = enemy.enemyTurn(hero, 2, battle, battle.getView());

        assertEquals(3, enemy.getShield());
        assertEquals(3, nextTurn);
    }

    @Test
    public void testTurn3Strength() {
        Enemy enemy = new Enemy("Pikachu", 20, 0, 5, 3, 2, "");
        Hero hero = new Hero("Charmander", 20, 5, 0, "");
        Battle battle = createBattle(hero, enemy);

        int nextTurn = enemy.enemyTurn(hero, 3, battle, battle.getView());

        assertEquals(1, nextTurn);

        boolean hasStrength = false;
        int strengthAmount = 0;
        
        for (Effect e : enemy.getEffects()) {
            if (e.getName().equals("Strength")) {
                hasStrength = true;
                strengthAmount = e.getAmount();
            }
        }
        
        assertTrue(hasStrength);
        assertEquals(2, strengthAmount);
    }
}
