

abstract public class Effect extends Subscriber {
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

    public void addAmount(int extra) {
        this.amount += extra;
    }

    public String getString() {
        return this.name + " (" + this.amount + ")";
    }
}
