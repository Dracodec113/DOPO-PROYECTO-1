import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;

/**
 * Casos de prueba de unidad para Tower - Ciclo 2.
 * Todas las pruebas corren en modo invisible.
 *
 * @author Jeronimo Moreno
 * @version Ciclo 2 2026-1
 */
public class TowerC2Test {

    private Tower tower;

    @Before
    public void setUp() {
        tower = new Tower(10, 200);
        tower.makeInvisible();
    }

    @Test
    public void shouldShowCorrectlyStackingItems() {
        tower.pushCup(3);
        tower.pushLid(3);
        String[][] expected = {{"cup", "3"}, {"lid", "3"}};
        assertArrayEquals(expected, tower.stackingItems());
    }

    @Test
    public void shouldRemoveExistingCups() {
        tower.pushCup(3);
        tower.removeCup(3);
        String[][] items = tower.stackingItems();
        for (String[] item : items) {
            assertFalse(item[0].equals("cup") && item[1].equals("3"));
        }
    }

    @Test
    public void shouldNotAddDuplicate() {
        tower.pushLid(3);
        tower.pushLid(3);
        String[][] items = tower.stackingItems();
        int count = 0;
        for (String[] item : items) {
            if (item[0].equals("lid") && item[1].equals("3")) count++;
        }
        assertEquals(1, count);
    }

    @Test
    public void shouldSortDescending() {
        tower.pushCup(2);
        tower.pushCup(5);
        tower.pushCup(1);
        tower.orderTower();
        String[][] items = tower.stackingItems();
        // primer elemento debe ser el más grande
        assertEquals("5", items[0][1]);
    }

    @Test
    public void shouldTowerLidFollowsCup() {
        tower.pushCup(3);
        tower.pushLid(3);
        tower.pushCup(5);
        tower.orderTower();
        String[][] items = tower.stackingItems();
        assertEquals("5", items[0][1]);
        assertEquals("cup", items[1][0]);
        assertEquals("3", items[1][1]);
        assertEquals("lid", items[2][0]);
        assertEquals("3", items[2][1]);
    }

    @Test
    public void shouldReturnCoveredCups() {
        tower.pushCup(2);
        tower.pushLid(2);
        tower.pushCup(4);
        tower.pushLid(4);
        tower.cover();
        ArrayList<Integer> lidded = tower.liddedCups();
        assertEquals(2, lidded.size());
    }

    @Test
    public void shouldReturnEmptyIfNoneCovered() {
        tower.pushCup(3);
        tower.pushCup(5);
        ArrayList<Integer> lidded = tower.liddedCups();
        assertEquals(0, lidded.size());
    }

}