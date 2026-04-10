package game.card;
import game.core.Manager;
import game.model.Entity;
import game.model.Hero;
import game.view.Colors;

/**
 * Classe base abstrata para todas as cartas do jogo (Dano, Escudo, Efeito, etc).
 * Define a estrutura básica de custo de energia e o comportamento polimórfico de uso.
 */
abstract public class Card {
    private String name;
    private String description;
    /** O custo necessário em pontos de energia para que o herói possa jogar esta carta. */
    private int energyCost;
    private String color;

    public Card(String name, String description, int energyCost, String color) {
        this.name = name;
        this.description = description;
        this.energyCost = energyCost;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getEnergyCost() {
        return energyCost;
    }

    public String getColoredName() {
        return this.color + this.name + Colors.RESET;
    }
    
    /**
     * Executa a ação principal da carta durante o combate.
     * Este método deve ser sobrescrito pelas subclasses para definir a lógica específica 
     * (ex: causar dano, adicionar escudo ou aplicar um efeito de status).
     *
     * @param hero A entidade controlada pelo jogador que está jogando a carta.
     * @param entity O alvo primário da carta (pode ser o inimigo para ataques ou o próprio herói para defesas).
     * @param manager O gerenciador da partida, necessário para registrar novos efeitos (Subscribers).
     */
    abstract public void use(Hero hero, Entity entity, Manager manager);
}
