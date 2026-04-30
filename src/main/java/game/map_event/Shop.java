package game.map_event;

import java.util.ArrayList;
import java.util.List;

import game.card.Card;
import game.card.CardStack;
import game.model.Hero;
import game.view.Colors;
import game.view.GameConsoleView;

public class Shop extends MapEvent {
    private String shopName = "PokeMart";
    private List<Card> cardsForSale;
    private List<Integer> cardPrices;
    private int cardRemovalCost = 50;

    /**
     * Construtor da Loja.
     * @param cardsForSale Lista de cartas disponíveis para compra.
     * @param cardPrices Lista com os preços correspondentes a cada carta.
     */
    public Shop(List<Card> cardsForSale, List<Integer> cardPrices) {
        this.cardsForSale = new ArrayList<>(cardsForSale);
        this.cardPrices = new ArrayList<>(cardPrices);
    }

    @Override
    public String getDescription() {
        return Colors.BOLD + "Merchant: " + Colors.RESET + shopName;
    }

    @Override
    public boolean start(Hero hero, CardStack deck, GameConsoleView view) throws InterruptedException {
        boolean inShop = true;

        while (inShop && hero.isAlive()) {
            view.clearScreen();
            System.out.println(Colors.YELLOW_BOLD + "\n=== " + shopName + " ===" + Colors.RESET);
            System.out.println("Welcome! Take a look at my wares.");
            System.out.println("Your PokeCoins: " + Colors.YELLOW_BOLD + hero.getPokeCoin() + Colors.RESET + "\n");

            for (int i = 0; i < cardsForSale.size(); i++) {
                Card card = cardsForSale.get(i);
                int price = cardPrices.get(i);
                System.out.printf("%d: Buy %s - %d PokeCoins (Cost: %d Energy) - %s\n", 
                    (i + 1), card.getColoredName(), price, card.getEnergyCost(), card.getDescription());
            }

            int removalOption = cardsForSale.size() + 1;
            int sellRelicOption = cardsForSale.size() + 2;
            int leaveOption = cardsForSale.size() + 3;

            System.out.printf("%d: Remove a random card from your deck - %d PokeCoins\n", removalOption, cardRemovalCost);
            System.out.printf("%d: Sell a Relic (+50 PokeCoins)\n", sellRelicOption);
            System.out.printf("%d: Leave Shop\n", leaveOption);

            int choice = view.getPlayerMove();

            if (choice > 0 && choice <= cardsForSale.size()) {
                int index = choice - 1;
                int price = cardPrices.get(index);
                
                if (hero.getPokeCoin() >= price) {
                    hero.subtractPokeCoin(price);
                    deck.getBuyStack().push(cardsForSale.get(index));
                    System.out.println("\n>>> You bought " + cardsForSale.get(index).getColoredName() + "!");
                    cardsForSale.remove(index);
                    cardPrices.remove(index);
                } else {
                    System.out.println(Colors.RED_BOLD + "\n>>> Not enough PokeCoins!" + Colors.RESET);
                }
                Thread.sleep(2000);
            } 

            else if (choice == removalOption) {
                if (hero.getPokeCoin() >= cardRemovalCost) {
                    if (!deck.getBuyStack().isEmpty()) {
                        Card removed = deck.getBuyStack().pop();
                        hero.subtractPokeCoin(cardRemovalCost);
                        cardRemovalCost += 25;
                        System.out.println("\n>>> You removed " + removed.getColoredName() + " from your deck!");
                    } else {
                        System.out.println("\n>>> Your deck is empty!");
                    }
                } else {
                    System.out.println(Colors.RED_BOLD + "\n>>> Not enough PokeCoins!" + Colors.RESET);
                }
                Thread.sleep(2000);
            }

            // Lógica de Venda de Relíquias (Placeholder)
            // else if (choice == sellRelicOption) {
                /*
                 * TODO: Integrar com a lista de relíquias do Hero quando a classe Relic for criada.
                 * Exemplo futuro: if (!hero.getRelics().isEmpty()) { hero.removeRelic(...); hero.addPokeCoin(50); }
                 */
            //     System.out.println("\n>>> You sold a Relic and gained 50 PokeCoins!");
            //     hero.addPokeCoin(50);
            //     Thread.sleep(2000);
            // }

            else if (choice == leaveOption) {
                System.out.println("\n>>> Thank you, come again!");
                inShop = false;
                Thread.sleep(1500);
            } 

            else {
                view.displayInvalidChoice();
                Thread.sleep(1000);
            }
        }
        return hero.isAlive();
    }
}