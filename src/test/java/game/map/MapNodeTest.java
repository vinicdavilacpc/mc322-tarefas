package game.map;

import game.model.Enemy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MapNodeTest {

    @Test
    public void testNavegacaoEPropriedadesDoMapa() {
        Enemy enemy = new Enemy("Pikachu", 20, 0, 5, 3, 2, "");
        
        MapNode nodeA = new MapNode("Room A", enemy, "");
        MapNode nodeB = new MapNode("Room B", enemy, "");
        
        nodeA.addNextNode(nodeB);
        
        assertEquals("Room A", nodeA.getLocationName());
        assertEquals(enemy, nodeA.getEnemy());
        
        assertFalse(nodeA.isFinalNode());
        assertTrue(nodeB.isFinalNode());
        
        assertEquals(1, nodeA.getNextNodes().size());
        assertEquals(nodeB, nodeA.getNextNodes().get(0));
    }
}