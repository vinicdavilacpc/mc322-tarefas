public class StrengthEffect extends Effect {
    public StrengthEffect(String name, Entity owner, int amount) {
        super(name, owner, amount);
    }

    public void receivesNotification(String event, Manager manager) {
        if (event.equals("beginningOfRound")) {
            System.out.printf("\n\n%s raised their attack and will deal %d more damage!\n", this.getOwner().getName(), this.getAmount());
        }
    }
}
