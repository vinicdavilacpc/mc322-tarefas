abstract public class Effect {
    private String name;
    private Entity owner;
    private int amount;

    public Effect(String name, Entity owner, int amount) {
        this.name = name;
        this.owner = owner;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public Entity getOwner() {
        return owner;
    } 

    public int getAmount() {
        return amount;
    }
}
