package game.map;

import java.util.ArrayList;
import java.util.List;

import game.model.Enemy;

/**
 * Representa um nó no mapa do jogo (uma sala/batalha).
 * Funciona como o elemento base de uma estrutura em árvore.
 */
public class MapNode {
    private String locationName;
    private Enemy enemy;
    private List<MapNode> nextNodes;

    public MapNode(String locationName, Enemy enemy) {
        this.locationName = locationName;
        this.enemy = enemy;
        this.nextNodes = new ArrayList<>();
    }

    public void addNextNode(MapNode node) {
        this.nextNodes.add(node);
    }

    public String getLocationName() { 
        return locationName; 
    }

    public Enemy getEnemy() { 
        return enemy; 
    }

    public List<MapNode> getNextNodes() { 
        return nextNodes; 
    }

    public boolean isFinalNode() {
        return this.nextNodes.isEmpty();
    }
}