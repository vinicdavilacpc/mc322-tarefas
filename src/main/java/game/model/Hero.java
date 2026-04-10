package game.model;
/**
 * Representa o personagem principal controlado pelo jogador.
 * Gerencia a energia disponível para usar as cartas a cada turno.
 */
public class Hero extends Entity {
    private int energy;

    public Hero(String name, int health, int energy, int shield, String color) {
        super(name, health, shield, color);
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