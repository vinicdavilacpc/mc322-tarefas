package game.map;

import java.util.ArrayList;
import java.util.List;

import game.model.Enemy;
import game.view.Colors;

/**
 * Representa um nó no mapa do jogo (uma sala/batalha).
 * Funciona como o elemento base de uma estrutura em árvore direcional que os jogadores percorrem.
 */
public class MapNode {
    /** O nome ou título da localização no mapa. */
    private String locationName;
    /** O inimigo que o herói deverá enfrentar se escolher este nó. */
    private Enemy enemy;
    /** A cor ANSI usada para desenhar o nome da localização no terminal. */
    private String color;
    /** Lista de nós subsequentes aos quais o jogador pode acessar a partir deste. */
    private List<MapNode> nextNodes;

    private int pokeCoinReward;
    private String rewardType;
    private int rewardAmount;

    /**
     * Construtor para o nó do mapa.
     *
     * @param locationName O nome da região/sala.
     * @param enemy        O monstro locado nessa região.
     * @param color        A cor base do nó.
     */
    public MapNode(String locationName, Enemy enemy, String color, int pokeCoinReward, String rewardType, int rewardAmount) {
        this.locationName = locationName;
        this.enemy = enemy;
        this.color = color;
        this.nextNodes = new ArrayList<>();
        this.pokeCoinReward = pokeCoinReward;
        this.rewardType = rewardType;
        this.rewardAmount = rewardAmount;
    }

    /**
     * Adiciona um novo nó ao qual é possível transitar a partir deste.
     *
     * @param node O nó vizinho subsequente.
     */
    public void addNextNode(MapNode node) {
        this.nextNodes.add(node);
    }

    /** @return O nome texto simples do local. */
    public String getLocationName() { 
        return locationName; 
    }

    /** @return O inimigo associado a essa parada no mapa. */
    public Enemy getEnemy() { 
        return enemy; 
    }

    /** @return O nome formatado em cor para ser impresso no console. */
    public String getColoredLocationName() {
        return this.color + this.locationName + Colors.RESET;
    }

    /** @return Lista de todos os próximos caminhos a partir deste ponto. */
    public List<MapNode> getNextNodes() { 
        return nextNodes; 
    }

    public int getPokeCoinReward() {
        return pokeCoinReward;
    }

    public String getRewardType() {
        return rewardType;
    }

    public int getRewardAmount() {
        return rewardAmount;
    }

    /**
     * Determina se este nó é o último ou um ponto final na aventura.
     *
     * @return true se não houver próximos nós cadastrados, false do contrário.
     */
    public boolean isFinalNode() {
        return this.nextNodes.isEmpty();
    }
}