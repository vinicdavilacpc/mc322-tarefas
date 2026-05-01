package game.map_event;

import game.model.Hero;

/**
 * Representa uma opção individual dentro de um evento de Escolha (Choice).
 */
public class ChoiceOption {
    /** O texto descritivo da opção exibido no menu. */
    private String text;
    /** A ação (padrão Command) executada caso esta opção seja selecionada. */
    private ChoiceAction action;

    /**
     * Construtor da opção de escolha.
     * 
     * @param text O texto da opção.
     * @param action A expressão funcional com a consequência da opção.
     */
    public ChoiceOption(String text, ChoiceAction action) {
        this.text = text;
        this.action = action;
    }

    /** @return O texto da opção. */
    public String getText() { return text; }
    
    /**
     * Executa a consequência atrelada a esta opção sobre o herói.
     * 
     * @param hero O herói que sofrerá as consequências da ação.
     */
    public void executeAction(Hero hero) {
        action.apply(hero);
    }
}