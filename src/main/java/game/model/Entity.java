package game.model;

import game.effect.Effect;
import game.view.Colors;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma entidade genérica no jogo, servindo de classe base para o Herói e para os Inimigos.
 * Gerencia os atributos vitais, escudos e os efeitos de status ativos.
 */
public abstract class Entity {
    private String name;
    private int health;
    private int shield;
    private int maxHealth;

    /** Lista de efeitos de status atualmente aplicados à entidade. */
    private List<Effect> effects;
    private String color;

    public Entity(String name, int health, int shield, String color) {
        this.name = name;
        this.health = health;
        this.shield = shield;
        this.maxHealth = health;
        this.effects = new ArrayList<>();
        this.color = color;
    }

    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getShield() { return shield; }
    public int getMaxHealth() { return maxHealth; }
    public List<Effect> getEffects() { return effects; }
    public String getColoredName() { return this.color + this.name + Colors.RESET; }

    public void takeDamage(int damage) {
        if (this.shield >= damage) {
            this.shield -= damage;
        } else {
            int remainingDamage = damage - this.shield;
            this.shield = 0;
            this.health -= remainingDamage;
        }
    }

    public void gainShield(int shieldValue) {
        this.shield += shieldValue;
    }

    public void resetShield() {
        this.shield = 0;
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    /**
     * Adiciona um novo efeito à entidade. 
     * Se o efeito já estiver ativo (baseado no nome), apenas incrementa a quantidade existente.
     */
    public void applyEffect(Effect newEffect) {
        boolean isRepeated = false;
        for (Effect effect : effects) {
            // CORREÇÃO: Comparando o conteúdo (nome) em vez da referência de memória (==)
            if (effect.getName().equals(newEffect.getName())) {
                effect.addAmount(newEffect.getAmount());
                isRepeated = true;
                break;
            }
        }
        
        if (!isRepeated) {
            effects.add(newEffect);
        }
    }
}