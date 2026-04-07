

public class PoisonEffect extends Effect {
    public PoisonEffect(String name, Entity owner, int amount) {
        super(name, owner, amount);
    }

    public void receivesNotification(String event, Manager manager) {
        if (event.equals("endOfRound")) {
            System.out.printf("%s was poisoned and lost %d health points!\n", this.getOwner().getName(), getAmount());
            this.getOwner().takeDamage(this.getAmount());
            this.addAmount(-1);

            if (this.getAmount() <= 0) {
                this.getOwner().getEffects().remove(this);
                manager.unsubscribe(this);
                System.out.printf("%s is cured from the poison!", this.getOwner().getName());
            } else {
                System.out.printf("Poison was reduced to %d.\n", getAmount());
            }
        }
    }
}
