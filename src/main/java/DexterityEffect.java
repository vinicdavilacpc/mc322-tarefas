

public class DexterityEffect extends Effect {
    public DexterityEffect(String name, Entity owner, int amount) {
        super(name, owner, amount);
    }

    public void receivesNotification(String event, Manager manager) {
        if (event.equals("beginningOfRound")) {
            System.out.printf("\n\n%s raised their defense and will block %d more damage!\n", this.getOwner().getName(), this.getAmount());
        }
    }
}
