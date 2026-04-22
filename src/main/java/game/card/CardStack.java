package game.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Gerencia a coleção de cartas do jogador durante o combate, dividindo-as em
 * pilha de compra, pilha de descarte e a mão atual do jogador.
 */
public class CardStack {
    /** Pilha contendo as cartas disponíveis para compra. */
    private Stack<Card> buyStack;
    /** Pilha onde são colocadas as cartas usadas ou descartadas da mão. */
    private Stack<Card> discardStack;
    /** Lista representando as cartas atualmente seguradas na mão do jogador. */
    private List<Card> playerHand;

    /**
     * Construtor do CardStack.
     *
     * @param buyStack     Pilha inicial preenchida com as cartas embaralhadas.
     * @param discardStack Pilha vazia para receber as cartas descartadas.
     */
    public CardStack(Stack<Card> buyStack, Stack<Card> discardStack) {
        this.buyStack = buyStack;
        this.discardStack = discardStack;
        this.playerHand = new ArrayList<>();
    }

    /** @return A pilha de compras. */
    public Stack<Card> getBuyStack() { return buyStack; }
    /** @return A pilha de descarte. */
    public Stack<Card> getDiscardStack() { return discardStack; }
    /** @return A lista de cartas na mão do jogador. */
    public List<Card> getPlayerHand() { return playerHand; }

    /**
     * Compra a carta no topo da pilha e a adiciona na mão do jogador.
     * Caso a pilha de compras esteja vazia, as cartas na pilha de descarte são 
     * movidas e embaralhadas novamente para a pilha de compras.
     */
    public void buy() {
        if (this.buyStack.isEmpty()) {
            if (this.discardStack.isEmpty()) {
                return;
            }
            this.buyStack.addAll(discardStack);
            this.discardStack.clear();
            Collections.shuffle(buyStack);
        }

        this.playerHand.add(this.buyStack.pop());
    }

    /**
     * Insere uma carta específica na pilha de descarte.
     *
     * @param card A carta a ser descartada.
     */
    public void discard(Card card) {
        this.discardStack.push(card);
    }

    /**
     * Move todas as cartas atualmente na mão do jogador para a pilha de descarte.
     */
    public void discardHand() {
        this.discardStack.addAll(this.playerHand);
        this.playerHand.clear();
    }

    /**
     * Reinicia o deck completo misturando a mão, o descarte e a pilha de compras
     * em um único monte embaralhado. Utilizado geralmente no início ou fim de batalhas.
     */
    public void resetBattleDeck() {
        this.buyStack.addAll(this.discardStack);
        this.buyStack.addAll(this.playerHand);
        
        this.discardStack.clear();
        this.playerHand.clear();
        
        Collections.shuffle(this.buyStack);
    }
}