package game.model;

import game.effect.Effect;
import game.effect.PoisonEffect;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HeroTest {
    @Test
    public void testTotalDamageAbsorvedShield() {
        Hero hero = new Hero("Charmander", 20, 5, 10, "");
        hero.takeDamage(4);
        
        assertEquals(20, hero.getHealth());
        assertEquals(6, hero.getShield());
    }

    @Test
    public void testParcialDamageAbsorvedShield() {
        Hero hero = new Hero("Charmander", 20, 5, 5, "");
        hero.takeDamage(8);

        assertEquals(17, hero.getHealth());
        assertEquals(0, hero.getShield());
    }

    @Test
    public void testDamageIgnoreShield() { 
        Hero hero = new Hero("Charmander", 20, 5, 10, ""); 
        hero.takeDirectDamage(3); // poison ativado

        assertEquals(17, hero.getHealth());
        assertEquals(10, hero.getShield());
    }

    @Test
    public void testNegativeHealth() {
        Hero hero = new Hero("Charmander", 8, 5, 0, ""); 
        hero.takeDamage(10);

        assertEquals(0, hero.getHealth());
    }

    @Test
    public void testAccumulatingEffects() {
        Hero hero = new Hero("Charmander", 20, 5, 0, "");
        Effect poison1 = new PoisonEffect("Poison", hero, 3);
        Effect poison2 = new PoisonEffect("Poison", hero, 2);

        boolean isNew1 = hero.applyEffect(poison1);
        boolean isNew2 = hero.applyEffect(poison2);

        assertTrue(isNew1);
        assertFalse(isNew2);

        assertEquals(1, hero.getEffects().size());
        assertEquals(5, hero.getEffects().get(0).getAmount());
    }

    @Test
    public void testDrainEnergy() {
        Hero hero = new Hero("Charmander", 20, 5, 0, ""); // max energy = 5
        hero.drainEnergy(3);

        assertEquals(2, hero.getEnergy());
    }

    @Test
    public void testResetShield() {
        Hero hero = new Hero("Charmander", 20, 3, 0, "");
        hero.gainShield(15);

        assertEquals(15, hero.getShield());

        hero.resetShield();
        
        assertEquals(0, hero.getShield());
    }

    @Test
    public void testIsAlive() {
        Hero hero = new Hero("Charmander", 8, 5, 0, ""); 

        assertTrue(hero.isAlive());

        hero.takeDamage(8);

        assertFalse(hero.isAlive());
    }
}