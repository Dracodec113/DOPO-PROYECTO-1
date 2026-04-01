// tests/HierarchicalCupTest.java
package tests;

import domain.*;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;

public class HierarchicalCupTest {

    private HierarchicalCup hierarchicalCup;

    @Before
    public void setUp() {
        hierarchicalCup = new HierarchicalCup(5, "black", 0, 0, 0);
    }

    @Test
    public void shouldInsertBeforeSmallerElementsOnPush() {
        ArrayList<String> order = new ArrayList<>();
        order.add("normalCup-3");
        order.add("normalCup-1");
        order.add("hierarchical-5");
        HashMap<String, StackingItem> items = new HashMap<>();

        hierarchicalCup.onPush(order, items);

        // Después del onPush, hierarchical-5 debe estar antes que los menores
        int indexH = order.indexOf("hierarchical-5");
        int index3 = order.indexOf("normalCup-3");
        int index1 = order.indexOf("normalCup-1");

        assertTrue(indexH < index3);
        assertTrue(indexH < index1);
    }

    @Test
    public void shouldNotInsertBeforeLargerElements() {
        ArrayList<String> order = new ArrayList<>();
        order.add("normalCup-7");
        order.add("hierarchical-5");
        HashMap<String, StackingItem> items = new HashMap<>();

        hierarchicalCup.onPush(order, items);

        int indexH = order.indexOf("hierarchical-5");
        int index7 = order.indexOf("normalCup-7");

        assertTrue(index7 < indexH);
    }

    @Test
    public void shouldReturnEmptyListFromOnPush() {
        ArrayList<String> order = new ArrayList<>();
        order.add("hierarchical-5");
        HashMap<String, StackingItem> items = new HashMap<>();

        ArrayList<Integer> affected = hierarchicalCup.onPush(order, items);

        assertTrue(affected.isEmpty());
    }
}