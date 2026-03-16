public class Hero extends Entity {
    private int energy;

    public Hero(String name, int health, int energy, int shield) {
        this.energy = energy;
        super(name, health, shield);
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