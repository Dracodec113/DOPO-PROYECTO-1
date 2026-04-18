package tests;

import domain.*;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;

public class OpenerCupTest {

    private OpenerCup openerCup;
    private HashMap<String, StackingItem> items;

    @Before
    public void setUp() {
        openerCup = new OpenerCup(4, "magenta", 0, 0, 4, "opener");
        items = new HashMap<>();
    }

    @Test
    public void shouldRemoveSmallerLidsOnPush() {
        ArrayList<String> order = new ArrayList<>();
        
        Lid lid1 = new Lid(1, "blue", 0, 0, 1, 1);
        Lid lid3 = new Lid(3, "red", 0, 0, 3, 3);
        
        items.put("lid-1", lid1);
        items.put("lid-3", lid3);
        
        order.add("lid-1");
        order.add("lid-3");

        ArrayList<Integer> affected = openerCup.onPush(order, items, "opener-4");

        assertTrue(affected.contains(1));
        assertTrue(affected.contains(3));
        assertEquals(2, affected.size());
    }

    @Test
    public void shouldNotRemoveLidsOfSameOrLargerSize() {
        ArrayList<String> order = new ArrayList<>();
        
        Lid lid4 = new Lid(4, "green", 0, 0, 4, 4);
        Lid lid5 = new Lid(5, "black", 0, 0, 5, 5);
        
        items.put("lid-4", lid4);
        items.put("lid-5", lid5);
        
        order.add("lid-4");
        order.add("lid-5");

        ArrayList<Integer> affected = openerCup.onPush(order, items, "opener-4");

        assertFalse(affected.contains(4));
        assertFalse(affected.contains(5));
        assertTrue(affected.isEmpty());
    }

    @Test
    public void shouldReturnEmptyListWhenNoLidsToRemove() {
        ArrayList<String> order = new ArrayList<>();
        
        Cup cup1 = new Cup(1, "yellow", 0, 0, 1, "normal");
        items.put("cup-1", cup1);
        order.add("cup-1");

        ArrayList<Integer> affected = openerCup.onPush(order, items, "opener-4");

        assertTrue(affected.isEmpty());
    }
}