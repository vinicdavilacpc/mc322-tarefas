package game.map_event;

import game.model.Hero;

/**
 * Interface funcional que atua como o padrão Command para definir a ação
 * executada ao selecionar uma opção em um evento de Escolha.
 */
@FunctionalInterface
public interface ChoiceAction {
    /**
     * Aplica o efeito narrativo ou mecânico da escolha.
     * 
     * @param hero O herói alvo da ação.
     */
    void apply(Hero hero);
}