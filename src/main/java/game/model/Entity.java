package game.model;

import java.util.ArrayList;
import java.util.List;

import game.effect.Effect;
import game.view.Colors;

/**
 * Representa uma entidade genérica no jogo, servindo de classe base para o Herói e para os Inimigos.
 * Gerencia os atributos vitais, escudos e os efeitos de status ativos.
 */
public abstract class Entity {
    /** Nome da entidade. */
    private String name;
    /** Pontos de vida atuais. */
    private int health;
    /** Pontos de escudo atuais (absorvem dano antes da vida). */
    private int shield;
    /** Saúde máxima da entidade. */
    private int maxHealth;

    /** Lista de efeitos de status atualmente aplicados à entidade. */
    private List<Effect> effects;
    /** Cor com a qual o nome da entidade será exibido no console. */
    private String color;

    /**
     * Construtor da classe base Entity.
     *
     * @param name   O nome da entidade.
     * @param health A vida inicial e máxima.
     * @param shield O escudo inicial.
     * @param color  A cor de formatação para exibição.
     */
    public Entity(String name, int health, int shield, String color) {
        this.name = name;
        this.health = health;
        this.shield = shield;
        this.maxHealth = health;
        this.effects = new ArrayList<>();
        this.color = color;
    }

    /** @return O nome da entidade. */
    public String getName() { return name; }
    /** @return A quantidade atual de vida da entidade. */
    public int getHealth() { return health; }
    /** @return A quantidade atual de escudo da entidade. */
    public int getShield() { return shield; }
    /** @return A vida máxima possível da entidade. */
    public int getMaxHealth() { return maxHealth; }
    /** @return A lista de efeitos de status ativos. */
    public List<Effect> getEffects() { return effects; }
    /** @return O nome da entidade formatado com a cor definida. */
    public String getColoredName() { return this.color + this.name + Colors.RESET; }

    /**
     * Aplica dano à entidade, reduzindo o escudo primeiramente. 
     * O restante do dano (se houver) é reduzido da vida.
     *
     * @param damage Quantidade de dano a ser aplicada.
     */
    public void takeDamage(int damage) {
        if (this.shield >= damage) {
            this.shield -= damage;
        } else {
            int remainingDamage = damage - this.shield;
            this.shield = 0;
            this.health -= remainingDamage;
        }

        if (this.health < 0) {
            this.health = 0; 
        }
    }

    /**
     * Aplica dano direto à saúde da entidade, ignorando qualquer escudo.
     *
     * @param damage Quantidade de dano a ser aplicada.
     */
    public void takeDirectDamage(int damage) {
        this.health -= damage;

        if (this.health < 0) {
            this.health = 0; 
        }
    }

    /**
     * Adiciona pontos de escudo à entidade.
     *
     * @param shieldValue Valor de escudo a ser ganho.
     */
    public void gainShield(int shieldValue) {
        this.shield += shieldValue;
    }

    /**
     * Zera completamente o escudo atual da entidade.
     */
    public void resetShield() {
        this.shield = 0;
    }

    /**
     * Verifica se a entidade ainda possui saúde maior que zero.
     *
     * @return true se estiver vivo, false caso contrário.
     */
    public boolean isAlive() {
        return this.health > 0;
    }

    public void increaseMaxHealth(int amount) {
        this.maxHealth += amount;
        this.health += amount;
    }

    public void increaseHealth(int amount) {
        if (this.health + amount > this.maxHealth) {
            this.health = amount;
        } else {
            this.health += amount;
        }
    }

    /**
     * Adiciona um novo efeito à entidade. 
     * Se o efeito já estiver ativo (baseado no nome), apenas incrementa a quantidade existente.
     *
     * @param newEffect O efeito a ser aplicado.
     * @return true se for um efeito novo; false se apenas acumulou em um efeito já existente.
     */
    public boolean applyEffect(Effect newEffect) { 
        for (Effect effect : effects) {
            if (effect.getName().equals(newEffect.getName())) {
                effect.addAmount(newEffect.getAmount());
                return false; 
            }
        }
        
        effects.add(newEffect);
        return true; 
    }
}