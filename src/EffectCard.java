public class EffectCard extends Card {
    public EffectCard(String name, String description, int energyCost) {
        super(name, description, energyCost);
    }

    // arrumar para ficar igual aos outros cards (ter mais prints e checar energia)
    public void use(Hero hero, Entity target, Manager manager) {
        // instanciar o effect
        // apply effect
        // inscrever o effect em subscribers!!
    }
}
