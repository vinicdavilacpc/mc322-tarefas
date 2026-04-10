package game.card;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 * Gerencia a coleção de cartas do jogador durante o combate, dividindo-as em
 * pilha de compra, pilha de descarte e a mão atual do jogador.
 */
public class CardStack {
    private Stack<Card> buyStack = new Stack<>();
    private Stack<Card> discardStack = new Stack<>();
    private ArrayList<Card> playerHand = new ArrayList<>();

    public CardStack(Stack<Card> buyStack, Stack<Card> discardStack) {
        this.buyStack = buyStack;
        this.discardStack = discardStack;
    }

    public Stack<Card> getBuyStack() {
        return buyStack;
    }

    public Stack<Card> getDiscardStack() {
        return discardStack;
    }

    public ArrayList<Card> getPlayerHand() {
        return playerHand;
    }

    /**
     * Move a carta do topo da pilha de compras para a mão do jogador.
     * Se a pilha de compras estiver vazia, o método resgata todas as cartas 
     * da pilha de descarte, embaralha e as coloca de volta na pilha de compras 
     * antes de realizar a compra.
     */
    public void buy() {
        if (this.buyStack.isEmpty()) {
            this.buyStack.addAll(discardStack);
            this.discardStack.clear();
            Collections.shuffle(buyStack);
        }

        this.playerHand.add(this.buyStack.pop());

        // Implementar verificação de segurança para caso as duas pilhas fiquem vazias
    }

    public void discard(Card card) {
        this.discardStack.addElement(card);
    }

    /**
     * Move todas as cartas atualmente na mão do jogador para a pilha de descarte.
     * Geralmente chamado no final de cada turno.
     */
    public void discardHand() {
        this.discardStack.addAll(this.playerHand);
        this.playerHand.clear();
    }
}
