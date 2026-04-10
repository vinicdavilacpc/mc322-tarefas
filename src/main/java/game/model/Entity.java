package game.model;
import java.util.ArrayList;

/**
 * Representa uma entidade genérica no jogo, servindo de classe base para o Herói e para os Inimigos.
 * Gerencia os atributos vitais, escudos e os efeitos de status ativos.
 */
abstract public class Entity {
    private String name;
    private int health;
    private int shield;
    private int maxHealth;

    /** Lista de efeitos de status (como Veneno, Força, Destreza) atualmente aplicados à entidade. */
    private ArrayList<Effect> effects;
    private String color;

    public Entity(String name, int health, int shield, String color) {
        this.name = name;
        this.health = health;
        this.shield = shield;
        this.maxHealth = health;
        this.effects = new ArrayList<>();
        this.color = color;
    }

    public String getName() {
        return name;
    }
    
    public int getHealth() {
        return health;
    }

    public int getShield() {
        return shield;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public ArrayList<Effect> getEffects() {
        return effects;
    }

    public String getColoredName() {
        return this.color + this.name + Colors.RESET;
    }

    /**
     * Aplica dano à entidade, priorizando a redução do escudo antes de afetar a saúde.
     * Se o dano for maior que o escudo atual, a diferença é subtraída da saúde.
     *
     * @param damage A quantidade de dano a ser recebida.
     */
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
     * Se o efeito já estiver ativo na entidade, em vez de duplicá-lo, a quantidade (amount) 
     * do efeito existente é incrementada.
     *
     * @param newEffect O novo efeito de status a ser aplicado.
     */
    public void applyEffect(Effect newEffect) {
        int repeats = 0;
        for (Effect effect : effects) {
            if (effect == newEffect) {
                effect.addAmount(newEffect.getAmount());
                repeats = 1;
                break;
            }
        }
        if (repeats == 0) {
            effects.add(newEffect);
        }
    }
}
