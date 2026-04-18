package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import domain.Cup;
import domain.HierarchicalCup;
import domain.StackingItem;

public class HierarchicalCupTest {

    private HierarchicalCup hierarchicalCup;
    private HashMap<String, StackingItem> items;

    @Before
    public void setUp() {
        hierarchicalCup = new HierarchicalCup(5, "black", 0, 0, 0, "hierarchical");
        items = new HashMap<>();
    }

    @Test
    public void shouldInsertBeforeSmallerElementsOnPush() {
        ArrayList<String> order = new ArrayList<>();
        
        Cup cup3 = new Cup(3, "yellow", 0, 0, 0, "normal");
        Cup cup1 = new Cup(1, "blue", 0, 0, 0, "normal");
        
        items.put("cup3", cup3);
        items.put("cup1", cup1);
        
        order.add("cup3");
        order.add("cup1");

        hierarchicalCup.onPush(order, items, "h5");

        int indexH = order.indexOf("h5");
        int index3 = order.indexOf("cup3");
        int index1 = order.indexOf("cup1");

        assertEquals(0, indexH);
        assertTrue(indexH < index3);
        assertTrue(indexH < index1);
    }

    @Test
    public void shouldNotInsertBeforeLargerElements() {
        ArrayList<String> order = new ArrayList<>();
        
        Cup cup7 = new Cup(7, "red", 0, 0, 0, "normal");
        items.put("cup7", cup7);
        order.add("cup7");

        hierarchicalCup.onPush(order, items, "h5");

        int indexH = order.indexOf("h5");
        int index7 = order.indexOf("cup7");

        assertEquals(1, indexH);
        assertTrue(index7 < indexH);
    }

    @Test
    public void shouldReturnEmptyListFromOnPush() {
        ArrayList<String> order = new ArrayList<>();
        ArrayList<Integer> affected = hierarchicalCup.onPush(order, items, "h5");

        assertTrue(affected.isEmpty());
    }

    @Test
    public void shouldNotBeRemovableAtBottom() {
        ArrayList<String> order = new ArrayList<>();
        order.add("h5");
        hierarchicalCup.onPush(new ArrayList<>(), items, "h5"); 
        
        assertFalse(hierarchicalCup.canRemove(order));
        
        order.add(0, "other");
        assertTrue(hierarchicalCup.canRemove(order));
    }
}