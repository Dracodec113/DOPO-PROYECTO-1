import static org.junit.Assert.*;
import org.junit.*;

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

    // ===================== pushCup =====================

    @Test
    public void testPushCupShouldAddCup() {
        tower.pushCup(3);
        assertTrue(tower.ok());
    }

    @Test
    public void testStackingItems() {
        tower.pushCup(3);
        tower.pushLid(3);
        String[][] expected = {{"cup", "3"}, {"lid", "3"}};
        assertArrayEquals(expected, tower.stackingItems());
    }

    // ===================== removeCup =====================

    @Test
    public void testRemoveCupShouldRemoveExisting() {
        tower.pushCup(3);
        tower.removeCup(3);
        String[][] items = tower.stackingItems();
        for (String[] item : items) {
            assertFalse(item[0].equals("cup") && item[1].equals("3"));
        }
    }

    @Test
    public void testRemoveCupShouldNotFailIfNotExists() {
        tower.removeCup(99); // no existe, no debe lanzar excepción
        assertTrue(tower.ok());
    }

    // ===================== pushLid =====================

    @Test
    public void testPushLidShouldAddLid() {
        tower.pushLid(3);
        assertTrue(tower.ok());
    }

    @Test
    public void testPushLidShouldNotAddDuplicate() {
        tower.pushLid(3);
        tower.pushLid(3);
        String[][] items = tower.stackingItems();
        int count = 0;
        for (String[] item : items) {
            if (item[0].equals("lid") && item[1].equals("3")) count++;
        }
        assertEquals(1, count);
    }

    // ===================== removeLid =====================

    @Test
    public void testRemoveLidShouldRemoveExisting() {
        tower.pushLid(3);
        tower.removeLid(3);
        String[][] items = tower.stackingItems();
        for (String[] item : items) {
            assertFalse(item[0].equals("lid") && item[1].equals("3"));
        }
    }

    @Test
    public void testRemoveLidShouldNotFailIfNotExists() {
        tower.removeLid(99);
        assertTrue(tower.ok());
    }

    // ===================== orderTower =====================

    @Test
    public void testOrderTowerShouldSortDescending() {
        tower.pushCup(2);
        tower.pushCup(5);
        tower.pushCup(1);
        tower.orderTower();
        String[][] items = tower.stackingItems();
        // primer elemento debe ser el más grande
        assertEquals("5", items[0][1]);
    }

    @Test
    public void testOrderTowerLidFollowsCup() {
        tower.pushCup(3);
        tower.pushLid(3);
        tower.pushCup(5);
        tower.orderTower();
        String[][] items = tower.stackingItems();
        // copa 5 primero, luego copa 3, luego lid 3
        assertEquals("5", items[0][1]);
        assertEquals("cup", items[1][0]);
        assertEquals("3", items[1][1]);
        assertEquals("lid", items[2][0]);
        assertEquals("3", items[2][1]);
    }

    // ===================== reverseTower =====================

    @Test
    public void testReverseTowerShouldSortAscending() {
        tower.pushCup(5);
        tower.pushCup(2);
        tower.pushCup(1);
        tower.reverseTower();
        String[][] items = tower.stackingItems();
        assertEquals("1", items[0][1]);
    }

    // ===================== cover =====================

    @Test
    public void testCoverShouldPlaceLidOnMatchingCup() {
        tower.pushCup(3);
        tower.pushLid(3);
        tower.cover();
        int[] lidded = tower.liddedCups();
        assertEquals(1, lidded.length);
        assertEquals(3, lidded[0]);
    }

    @Test
    public void testCoverShouldNotAffectCupsWithoutLid() {
        tower.pushCup(3);
        tower.pushCup(5);
        tower.pushLid(3);
        tower.cover();
        int[] lidded = tower.liddedCups();
        // solo la 3 debe estar tapada
        assertEquals(1, lidded.length);
    }

    // ===================== liddedCups =====================

    @Test
    public void testLiddedCupsShouldReturnCoveredCups() {
        tower.pushCup(2);
        tower.pushLid(2);
        tower.pushCup(4);
        tower.pushLid(4);
        tower.cover();
        int[] lidded = tower.liddedCups();
        assertEquals(2, lidded.length);
    }

    @Test
    public void testLiddedCupsShouldReturnEmptyIfNoneCovered() {
        tower.pushCup(3);
        tower.pushCup(5);
        int[] lidded = tower.liddedCups();
        assertEquals(0, lidded.length);
    }

    // ===================== height =====================

    @Test
    public void testHeightShouldBeZeroWhenEmpty() {
        assertEquals(0, tower.height());
    }

    @Test
    public void testHeightShouldReflectStackedItems() {
        tower.pushCup(1); // altura 1 cm
        tower.pushCup(2); // altura 3 cm
        assertTrue(tower.height() > 0);
    }

    // ===================== stackingItems =====================

    @Test
    public void testStackingItemsShouldReturnCorrectFormat() {
        tower.pushCup(3);
        tower.pushLid(3);
        String[][] items = tower.stackingItems();
        // formato: {{"cup","3"},{"lid","3"}} o similar
        assertNotNull(items);
        assertTrue(items.length >= 2);
        assertEquals(2, items[0].length); // cada entrada tiene tipo y número
    }

    @Test
    public void testStackingItemsShouldBeEmptyWhenTowerIsEmpty() {
        String[][] items = tower.stackingItems();
        assertEquals(0, items.length);
    }

    // ===================== Tower(int cups) =====================

    @Test
    public void testTowerWithCupsShouldCreateCorrectNumber() {
        Tower t = new Tower(4);
        t.makeInvisible();
        String[][] items = t.stackingItems();
        assertEquals(4, items.length);
    }

    @Test
    public void testTowerWithCupsShouldNotIncludeLids() {
        Tower t = new Tower(4);
        t.makeInvisible();
        String[][] items = t.stackingItems();
        for (String[] item : items) {
            assertEquals("cup", item[0]);
        }
    }
}