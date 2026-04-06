import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

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

    public void discardHand() {
        this.discardStack.addAll(this.playerHand);
        this.playerHand.clear();
    }
}
