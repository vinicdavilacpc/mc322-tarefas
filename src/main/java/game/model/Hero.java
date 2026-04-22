package game.model;

/**
 * Representa o personagem principal controlado pelo jogador.
 * Gerencia a energia disponível para usar as cartas a cada turno.
 */
public class Hero extends Entity {
    /** Quantidade atual de energia no turno. */
    private int energy;
    /** Quantidade máxima de energia da entidade. */
    private int maxEnergy;

    /**
     * Construtor da classe Hero.
     *
     * @param name   O nome do herói.
     * @param health A quantidade inicial e máxima de vida.
     * @param energy A quantidade inicial e máxima de energia.
     * @param shield O escudo inicial.
     * @param color  A cor utilizada no terminal.
     */
    public Hero(String name, int health, int energy, int shield, String color) {
        super(name, health, shield, color);
        this.energy = energy;
        this.maxEnergy = energy;
    }

    /** @return A quantidade de energia atual. */
    public int getEnergy() {
        return energy;
    }

    /** @return A energia máxima do herói. */
    public int getMaxEnergy() {
        return maxEnergy;
    }

    /**
     * Drena uma quantidade de energia do herói após o uso de uma carta.
     *
     * @param drainedEnergy A quantidade de energia a ser reduzida.
     */
    public void drainEnergy(int drainedEnergy) {
        this.energy -= drainedEnergy;
    }

    /**
     * Restaura a energia do herói para o valor especificado, tipicamente utilizado no início de cada turno.
     *
     * @param fullEnergy O novo valor total de energia.
     */
    public void restoreEnergy(int fullEnergy) {
        this.energy = fullEnergy;
    }
}