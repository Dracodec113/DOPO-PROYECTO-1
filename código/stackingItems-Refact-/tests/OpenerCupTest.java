// tests/OpenerCupTest.java
package tests;

import domain.*;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;

public class OpenerCupTest {

    private OpenerCup openerCup;

    @Before
    public void setUp() {
        openerCup = new OpenerCup(4, "magenta", 0, 0, 4);
    }

    @Test
    public void shouldRemoveSmallerLidsOnPush() {
        ArrayList<String> order = new ArrayList<>();
        order.add("normalCup-1");
        order.add("lid-1");
        order.add("normalCup-3");
        order.add("lid-3");
        HashMap<String, StackingItem> items = new HashMap<>();

        ArrayList<Integer> affected = openerCup.onPush(order, items);

        assertTrue(affected.contains(1));
        assertTrue(affected.contains(3));
    }

    @Test
    public void shouldNotRemoveLidsOfSameOrLargerSize() {
        ArrayList<String> order = new ArrayList<>();
        order.add("normalCup-4");
        order.add("lid-4");
        order.add("normalCup-5");
        order.add("lid-5");
        HashMap<String, StackingItem> items = new HashMap<>();

        ArrayList<Integer> affected = openerCup.onPush(order, items);

        assertFalse(affected.contains(4));
        assertFalse(affected.contains(5));
    }

    @Test
    public void shouldReturnEmptyListWhenNoLidsToRemove() {
        ArrayList<String> order = new ArrayList<>();
        order.add("normalCup-1");
        order.add("normalCup-2");
        HashMap<String, StackingItem> items = new HashMap<>();

        ArrayList<Integer> affected = openerCup.onPush(order, items);

        assertTrue(affected.isEmpty());
    }
}