

public class Hero extends Entity {
    private int energy;

    public Hero(String name, int health, int energy, int shield) {
        super(name, health, shield);
        this.energy = energy;
    }

    public int getEnergy() {
        return energy;
    }

    public void drainEnergy(int drainedEnergy) {
        this.energy -= drainedEnergy;
    }

    public void restoreEnergy(int fullEnergy) {
        this.energy = fullEnergy;
    }

}