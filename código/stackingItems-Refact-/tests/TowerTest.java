package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import domain.Tower;

public class TowerTest {

    private Tower tower;

    @Before
    public void setUp() {
        tower = new Tower(600, 600);
    }

    @Test
    public void shouldAddCupToTower() {
        tower.pushCup(3);
        String[][] items = tower.stackingItems();
        boolean found = false;
        for (String[] item : items) {
            if (item[0].equals("cup") && item[1].equals("3")) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    public void shouldNotAddDuplicateCup() {
        tower.pushCup(3);
        tower.pushCup(3);
        String[][] items = tower.stackingItems();
        int count = 0;
        for (String[] item : items) {
            if (item[0].equals("cup") && item[1].equals("3")) {
                count++;
            }
        }
        assertEquals(1, count);
    }

    @Test
    public void shouldNotPushInvalidCupType() {
        Tower tower = new Tower(5);
        tower.pushCup("tipoInvalido", 10); 
    }

    @Test
    public void shouldNotPopLidWhenEmpty() {
        Tower tower = new Tower(0);
        tower.popLid();
    }

    @Test
    public void shouldHandleVisibility() {
        Tower tower = new Tower(3);
        tower.makeVisible();
        tower.makeInvisible();
    }

    @Test
    public void shouldOrderAndReverseTower() {
        Tower tower = new Tower(3);
        tower.pushCup("normal", 1);
        tower.pushCup("normal", 2);
        tower.orderTower();
        tower.reverseTower();
    }
    
    @Test
    public void shouldAddLidToTower() {
        tower.pushCup(3);
        tower.pushLid(3);
        String[][] items = tower.stackingItems();
        boolean found = false;
        for (String[] item : items) {
            if (item[0].equals("lid") && item[1].equals("3")) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    public void shouldNotAddDuplicateLid() {
        tower.pushCup(3);
        tower.pushLid(3);
        tower.pushLid(3);
        String[][] items = tower.stackingItems();
        int count = 0;
        for (String[] item : items) {
            if (item[0].equals("lid") && item[1].equals("3")) {
                count++;
            }
        }
        assertEquals(1, count);
    }

    @Test
    public void shouldRemoveCupFromTower() {
        tower.pushCup(3);
        tower.removeCup(3);
        String[][] items = tower.stackingItems();
        boolean found = false;
        for (String[] item : items) {
            if (item[0].equals("cup") && item[1].equals("3")) {
                found = true;
                break;
            }
        }
        assertFalse(found);
    }

    @Test
    public void shouldRemoveLidFromTower() {
        tower.pushCup(3);
        tower.pushLid(3);
        tower.removeLid(3);
        String[][] items = tower.stackingItems();
        boolean found = false;
        for (String[] item : items) {
            if (item[0].equals("lid") && item[1].equals("3")) {
                found = true;
                break;
            }
        }
        assertFalse(found);
    }

    @Test
    public void shouldDetectLiddedCups() {
        tower.pushCup(3);
        tower.pushLid(3);
        ArrayList<Integer> lidded = tower.liddedCups();
        assertTrue(lidded.contains(3));
    }

    @Test
    public void shouldNotDetectCupWithoutLidAsLidded() {
        tower.pushCup(3);
        ArrayList<Integer> lidded = tower.liddedCups();
        assertFalse(lidded.contains(3));
    }

    @Test
    public void shouldNotAllowFearfulLidWithoutCompanion() {
        tower.pushLid("fearful", 3);
        String[][] items = tower.stackingItems();
        boolean found = false;
        for (String[] item : items) {
            if (item[0].equals("lid") && item[1].equals("3")) {
                found = true;
                break;
            }
        }
        assertFalse(found);
    }

    @Test
    public void shouldPlaceCrazyLidAtBase() {
        tower.pushCup(3);
        tower.pushCup(2);
        tower.pushLid("crazy", 3);
        String[][] items = tower.stackingItems();
        // CrazyLid debe ser el primer elemento (index 0)
        assertEquals("lid", items[0][0]);
        assertEquals("3", items[0][1]);
    }

    @Test
    public void shouldOpenerCupRemoveBlockingLids() {
        tower.pushCup(1);
        tower.pushLid(1);
        tower.pushCup(2);
        tower.pushLid(2);
        tower.pushCup("opener", 4);
        String[][] items = tower.stackingItems();
        boolean lid1Found = false;
        boolean lid2Found = false;
        for (String[] item : items) {
            if (item[0].equals("lid") && item[1].equals("1")) lid1Found = true;
            if (item[0].equals("lid") && item[1].equals("2")) lid2Found = true;
        }
        assertFalse(lid1Found);
        assertFalse(lid2Found);
    }

    @Test
    public void shouldCoverExistingItems() {
        Tower tower = new Tower(5);
        tower.cover(); 
    
        tower.pushCup("normal", 10);
        tower.pushLid("normal", 10);
        tower.cover();
    }
    
    @Test
    public void shouldHandleInvalidSwaps() {
        Tower tower = new Tower(5);
        tower.pushCup("normal", 10);
        
        tower.swap(null, new String[]{"cup"}); 
        
        tower.swap(new String[]{"avion", "1"}, new String[]{"cup", "10"});
        
        tower.swap(new String[]{"cup", "999"}, new String[]{"cup", "10"});
    }
    
    @Test
    public void shouldTestOrderingAndReversing() {
        Tower tower = new Tower(5);
        tower.pushCup("normal", 10);
        tower.pushCup("normal", 5);
        tower.orderTower();
        tower.reverseTower();
    }

    @Test
    public void shouldHandleEmptyPopAndInvalidPush() {
        Tower tower = new Tower(3);
        tower.popLid();
        tower.popCup();
        tower.pushCup("tipo_inexistente", 10);
    }
    
    @Test
    public void shouldPopCupFromTop() {
        tower.pushCup(3);
        tower.pushCup(2);
        tower.popCup();
        String[][] items = tower.stackingItems();
        boolean found = false;
        for (String[] item : items) {
            if (item[0].equals("cup") && item[1].equals("2")) {
                found = true;
                break;
            }
        }
        assertFalse(found);
    }
}