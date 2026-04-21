package game.card;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Stack;

public class CardStackTest {
    @Test
    public void testBuyCard() {
        Stack<Card> buy = new Stack<>();
        Stack<Card> discard = new Stack<>();
        
        Card card = new DamageCard("Attack", "Damage", 1, 5, "");
        buy.push(card);
        CardStack deck = new CardStack(buy, discard);
        deck.buy();
        
        assertEquals(1, deck.getPlayerHand().size());
        assertTrue(deck.getBuyStack().isEmpty());
        assertEquals(card, deck.getPlayerHand().get(0));
    }

    @Test
    public void testShuffleDiscardDeckEmptyBuy() {
        Stack<Card> buy = new Stack<>();
        Stack<Card> discard = new Stack<>();
        
        Card card1 = new DamageCard("Attack 1", "Damage", 1, 5, "");
        Card card2 = new DamageCard("Attack 2", "Damage", 1, 5, "");
        discard.push(card1);
        discard.push(card2);
        CardStack deck = new CardStack(buy, discard);
        deck.buy(); 
        
        assertEquals(1, deck.getPlayerHand().size());
        assertEquals(1, deck.getBuyStack().size());
        assertTrue(deck.getDiscardStack().isEmpty());
    }

    @Test
    public void testBuyEverythingEmpty() {
        Stack<Card> buy = new Stack<>();
        Stack<Card> discard = new Stack<>();
        CardStack deck = new CardStack(buy, discard);
        
        deck.buy();
        
        assertTrue(deck.getPlayerHand().isEmpty());
        assertTrue(deck.getBuyStack().isEmpty());
        assertTrue(deck.getDiscardStack().isEmpty());
    }

    @Test
    public void testDiscardCard() {
        Stack<Card> buy = new Stack<>();
        Stack<Card> discard = new Stack<>();
        CardStack deck = new CardStack(buy, discard);
        
        Card card = new DamageCard("Attack", "Damage", 1, 5, "");
        deck.discard(card);
        
        assertEquals(1, deck.getDiscardStack().size());
        assertEquals(card, deck.getDiscardStack().peek());
    }

    @Test
    public void testDiscardHand() {
        Stack<Card> buy = new Stack<>();
        Stack<Card> discard = new Stack<>();
        CardStack deck = new CardStack(buy, discard);
        
        deck.getPlayerHand().add(new DamageCard("Card 1", "Damage", 1, 5, ""));
        deck.getPlayerHand().add(new DamageCard("Card 2", "Damage", 1, 5, ""));
        deck.discardHand();
        
        assertTrue(deck.getPlayerHand().isEmpty());
        assertEquals(2, deck.getDiscardStack().size());
    }

    @Test
    public void testResetBattleDeck() {
        Stack<Card> buy = new Stack<>();
        Stack<Card> discard = new Stack<>();
        CardStack deck = new CardStack(buy, discard);
        
        deck.getDiscardStack().push(new DamageCard("Card 1", "Damage", 1, 1, ""));
        deck.getDiscardStack().push(new DamageCard("Card 2", "Damage", 1, 1, ""));
        deck.getPlayerHand().add(new DamageCard("Card 3", "Damage", 1, 1, ""));
        deck.getBuyStack().push(new DamageCard("Card 4", "Damage", 1, 1, ""));
        
        deck.resetBattleDeck();
        
        assertEquals(4, deck.getBuyStack().size());
        assertTrue(deck.getDiscardStack().isEmpty());
        assertTrue(deck.getPlayerHand().isEmpty());
    }
}
