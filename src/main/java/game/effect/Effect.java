package game.effect;

import game.event.Subscriber;
import game.model.Entity;

/**
 * Classe base para todos os efeitos de status do jogo (como Veneno, Força, Destreza).
 * Atua como um Subscriber, escutando os eventos do turno para aplicar seus efeitos 
 * no momento correto.
 */
public abstract class Effect implements Subscriber {
    private String name;
    private Entity owner;
    private int amount;

    public Effect(String name, Entity owner, int amount) {
        this.name = name;
        this.owner = owner;
        this.amount = amount;
    }

    public String getName() { return name; }
    public Entity getOwner() { return owner; }
    public int getAmount() { return amount; }
    
    public void addAmount(int extra) { 
        this.amount += extra; 
    }

    public String getString() { 
        return this.name + " (" + this.amount + ")"; 
    }
}