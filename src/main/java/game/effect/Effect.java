package game.effect;

import game.event.Subscriber;
import game.model.Entity;

/**
 * Classe base para todos os efeitos de status do jogo (como Veneno, Força, Destreza).
 * Atua como um Subscriber, escutando os eventos do turno para aplicar seus efeitos 
 * no momento correto.
 */
public abstract class Effect implements Subscriber {
    /** O nome identificador do efeito (ex: "Poison"). */
    private String name;
    /** A entidade afetada por este efeito. */
    private Entity owner;
    /** O valor/quantidade ou duração atual deste efeito. */
    private int amount;

    /**
     * Construtor do Efeito.
     *
     * @param name   O nome do efeito.
     * @param owner  A entidade à qual o efeito é aplicado.
     * @param amount A potência, quantidade ou duração do efeito.
     */
    public Effect(String name, Entity owner, int amount) {
        this.name = name;
        this.owner = owner;
        this.amount = amount;
    }

    /** @return O nome do efeito. */
    public String getName() { return name; }
    /** @return O proprietário/afetado por esse efeito. */
    public Entity getOwner() { return owner; }
    /** @return O montante/intensidade atual do efeito. */
    public int getAmount() { return amount; }
    
    /**
     * Acumula/Altera o valor do efeito em execução com o valor extra informado.
     *
     * @param extra Valor adicional a ser somado no efeito existente.
     */
    public void addAmount(int extra) { 
        this.amount += extra; 
    }

    /**
     * Retorna a string representativa do efeito e seu montante para ser exibida em menus.
     *
     * @return String formatada (ex: "Poison (3)").
     */
    public String getString() { 
        return this.name + " (" + this.amount + ")"; 
    }
}