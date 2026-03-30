public class PoisonEffect extends Effect {
    public PoisonEffect(String name, Entity owner, int amount) {
        super(name, owner, amount);
    }

    public void receivesNotification(String event, Manager manager) {
        if (event == "endRound") {
            // tirar dano do entity igual ao amount e diminuir 1 no amount
            // se acabar remover o efeito tanto da lista de subs quanto do entity
        }
    }
}
