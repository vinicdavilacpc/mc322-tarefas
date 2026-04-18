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
    private Stack<Card> buyStack;
    private Stack<Card> discardStack;
    private List<Card> playerHand;

    public CardStack(Stack<Card> buyStack, Stack<Card> discardStack) {
        this.buyStack = buyStack;
        this.discardStack = discardStack;
        this.playerHand = new ArrayList<>();
    }

    public Stack<Card> getBuyStack() { return buyStack; }
    public Stack<Card> getDiscardStack() { return discardStack; }
    public List<Card> getPlayerHand() { return playerHand; }

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

    public void discard(Card card) {
        this.discardStack.push(card);
    }

    public void discardHand() {
        this.discardStack.addAll(this.playerHand);
        this.playerHand.clear();
    }
}