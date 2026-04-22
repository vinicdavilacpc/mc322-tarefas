package game.card;

import game.core.Battle;
import game.effect.Effect;
import game.model.Entity;
import game.model.Hero;

/**
 * Representa uma carta com foco defensivo. 
 * Ao ser usada, consome a energia do herói e aplica escudo.
 */
public class ShieldCard extends Card {
    /** A quantidade de escudo base fornecida pela carta. */
    private int defense;

    /**
     * Construtor da carta de escudo.
     *
     * @param name        O nome da carta.
     * @param description A descrição da carta.
     * @param energyCost  O custo em energia.
     * @param defense     O valor gerado em escudo.
     * @param color       A cor ANSI da carta.
     */
    public ShieldCard(String name, String description, int energyCost, int defense, String color) {
        super(name, description, energyCost, color);
        this.defense = defense;
    }

    /** @return A defesa (escudo) base da carta. */
    public int getDefense() { return defense; }

    /**
     * Utiliza a carta, garantindo escudo ao herói. O valor de escudo recebe o acréscimo 
     * de efeitos de Destreza ("Dexterity") do herói e a energia correspondente é drenada.
     *
     * @param hero   O herói defensivo.
     * @param target Não é utilizado diretamente por essa carta, pode ser nulo ou o oponente.
     * @param battle O contexto da batalha atual.
     */
    @Override
    public void use(Hero hero, Entity target, Battle battle) {
        int totalDefense = this.defense;
        
        for (Effect effect : hero.getEffects()) {
            if (effect.getName().equals("Dexterity")) {
                totalDefense += effect.getAmount();
            }
        }

        hero.gainShield(totalDefense);
        hero.drainEnergy(this.getEnergyCost());
    }
}