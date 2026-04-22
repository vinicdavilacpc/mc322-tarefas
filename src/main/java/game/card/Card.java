package game.card;

import game.core.Battle;
import game.model.Entity;
import game.model.Hero;
import game.view.Colors;

/**
 * Classe base abstrata para todas as cartas do jogo.
 * Define a estrutura básica de custo de energia e o comportamento polimórfico de uso.
 */
public abstract class Card {
    /** O nome da carta. */
    private String name;
    /** Descrição textual do que a carta faz. */
    private String description;
    /** O custo em energia para utilizar a carta. */
    private int energyCost;
    /** A cor usada para formatar a carta no console. */
    private String color;

    /**
     * Construtor da classe base Card.
     *
     * @param name        O nome da carta.
     * @param description A descrição dos efeitos da carta.
     * @param energyCost  O custo de energia para jogá-la.
     * @param color       A cor ANSI da carta.
     */
    public Card(String name, String description, int energyCost, String color) {
        this.name = name;
        this.description = description;
        this.energyCost = energyCost;
        this.color = color;
    }

    /** @return O nome da carta. */
    public String getName() { return name; }
    /** @return A descrição detalhada da carta. */
    public String getDescription() { return description; }
    /** @return O custo de energia necessário para o uso da carta. */
    public int getEnergyCost() { return energyCost; }
    /** @return O nome da carta formatado com sua respectiva cor. */
    public String getColoredName() { return this.color + this.name + Colors.RESET; }
    
    /**
     * Aplica o efeito da carta em jogo. Esse método deve ser sobrescrito pelas subclasses.
     *
     * @param hero   O herói que está utilizando a carta.
     * @param target A entidade alvo do efeito da carta.
     * @param battle O gerenciador da batalha em andamento.
     */
    public abstract void use(Hero hero, Entity target, Battle battle);
}