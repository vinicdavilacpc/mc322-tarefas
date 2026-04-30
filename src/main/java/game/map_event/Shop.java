package game.map_event;

import java.util.ArrayList;
import java.util.List;

import game.card.CardStack;
import game.item.Item;
import game.model.Hero;
import game.view.Colors;
import game.view.GameConsoleView;

public class Shop extends MapEvent {
    private String shopName = "Pokémon Mart";
    private List<Item> itemsForSale;
    private int cardRemovalCost = 50;

    public Shop(List<Item> itemsForSale) {
        this.itemsForSale = new ArrayList<>(itemsForSale);
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
            System.out.println("Welcome! Buy some potions to help your journey.");
            System.out.println("Your PokeCoins: " + Colors.YELLOW_BOLD + hero.getPokeCoin() + Colors.RESET + "\n");

            for (int i = 0; i < itemsForSale.size(); i++) {
                Item item = itemsForSale.get(i);
                System.out.printf("%d: Buy %s - %d PokeCoins - %s\n", 
                    (i + 1), item.getColoredName(), item.getPrice(), item.getDescription());
            }

            int removalOption = itemsForSale.size() + 1;
            int leaveOption = itemsForSale.size() + 2;

            System.out.printf("%d: Remove a random card from your deck - %d PokeCoins\n", removalOption, cardRemovalCost);
            System.out.printf("%d: Leave Shop\n", leaveOption);

            int choice = view.getPlayerMove();

            if (choice > 0 && choice <= itemsForSale.size()) {
                int index = choice - 1;
                Item selectedItem = itemsForSale.get(index);
                
                if (hero.getPokeCoin() >= selectedItem.getPrice()) {
                    hero.subtractPokeCoin(selectedItem.getPrice());
                    hero.addItem(selectedItem);
                    System.out.println("\n>>> You bought " + selectedItem.getColoredName() + "!");
                    itemsForSale.remove(index);
                } else {
                    System.out.println(Colors.RED_BOLD + "\n>>> Not enough PokeCoins!" + Colors.RESET);
                }
                Thread.sleep(2000);
            } else if (choice == removalOption) {
                if (hero.getPokeCoin() >= cardRemovalCost) {
                    if (!deck.getBuyStack().isEmpty()) {
                        deck.getBuyStack().pop();
                        hero.subtractPokeCoin(cardRemovalCost);
                        cardRemovalCost += 25; 
                        System.out.println("\n>>> You removed a card from your deck!");
                    } else {
                        System.out.println("\n>>> Your deck is empty!");
                    }
                } else {
                    System.out.println(Colors.RED_BOLD + "\n>>> Not enough PokeCoins!" + Colors.RESET);
                }
                Thread.sleep(2000);
            }
            
            else if (choice == leaveOption) {
                System.out.println("\n>>> Thank you, come again!");
                inShop = false;
                Thread.sleep(1500);
            } else {
                view.displayInvalidChoice();
                Thread.sleep(1000);
            }
        }
        return hero.isAlive();
    }
}