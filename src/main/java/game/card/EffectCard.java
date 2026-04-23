package game.card;

import game.map_event.Battle;
import game.model.Entity;
import game.model.Hero;

/**
 * Representa uma carta com foco em efeitos (Buffs ou Debuffs).
 * Utiliza o padrão Command (através da interface EffectAction) para definir o efeito.
 */
public class EffectCard extends Card {
    /**
     * Interface funcional que encapsula o efeito a ser aplicado através de um padrão Command.
     */
    public interface EffectAction {
        /**
         * Executa a lógica customizada do efeito.
         *
         * @param hero   O herói lançando a carta.
         * @param target A entidade alvo do efeito.
         * @param battle O contexto da batalha.
         */
        void apply(Hero hero, Entity target, Battle battle);
    }

    /** O comando da ação que contém o efeito a ser ativado. */
    private EffectAction action;

    /**
     * Construtor da carta de Efeito.
     *
     * @param name        Nome da carta.
     * @param description Descrição da carta.
     * @param energyCost  Custo de energia.
     * @param action      Interface EffectAction (uma expressão lambda, geralmente) que descreve o comportamento.
     * @param color       A cor de exibição no console.
     */
    public EffectCard(String name, String description, int energyCost, EffectAction action, String color) {
        super(name, description, energyCost, color);
        this.action = action;
    }

    /**
     * Utiliza a carta, acionando o método apply na interface funcional EffectAction 
     * e drenando a energia do herói.
     *
     * @param hero   O herói lançando a carta.
     * @param target A entidade alvo (o próprio herói para buffs, o inimigo para debuffs).
     * @param battle A batalha em progresso.
     */
    @Override
    public void use(Hero hero, Entity target, Battle battle) {
        this.action.apply(hero, target, battle);
        hero.drainEnergy(this.getEnergyCost());
    }
}