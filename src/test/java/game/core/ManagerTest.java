package game.core;

import game.view.GameConsoleView;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ManagerTest {
    
    @Test
    public void testManagerInitializeEverything() {
        GameConsoleView view = new GameConsoleView();
        Manager manager = new Manager(view);
        
        assertNotNull(manager.getView());
    }
}