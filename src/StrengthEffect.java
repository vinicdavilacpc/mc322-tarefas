public class StrengthEffect extends Effect {
    public StrengthEffect(String name, Entity owner, int amount) {
        super(name, owner, amount);
    }

    public void receivesNotification(String event, Manager manager) {
        // vai ser notificada no fim do turno do heroi
    }
}
