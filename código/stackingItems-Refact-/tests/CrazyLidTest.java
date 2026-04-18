package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import domain.CrazyLid;
import domain.StackingItem;

public class CrazyLidTest {

    private CrazyLid crazyLid;

    @Before
    public void setUp() {
        crazyLid = new CrazyLid(3, "blue", 0, 0, 3, 2);
    }

    @Test
    public void shouldAlwaysBeRemovable() {
        ArrayList<String> order = new ArrayList<>();
        order.add("cup-3");
        order.add("crazylid-3");
        assertTrue(crazyLid.canRemove(order));
    }

    @Test
    public void shouldBeRemovableEvenWhenCoveringCompanion() {
        ArrayList<String> order = new ArrayList<>();
        order.add("crazylid-3");
        order.add("cup-3");
        assertTrue(crazyLid.canRemove(order));
    }

    @Test
    public void shouldInsertAtCompanionPosition() {
        ArrayList<String> order = new ArrayList<>();
        HashMap<String, StackingItem> items = new HashMap<>();
        
        order.add("cup-1");
        order.add("cup-3");
        order.add("cup-5");

        crazyLid.onPush(order, items, "crazylid-3");

        assertEquals(1, order.indexOf("crazylid-3"));
        assertEquals(2, order.indexOf("cup-3"));
    }

    @Test
    public void shouldInsertAtEndIfNoCompanionFound() {
        ArrayList<String> order = new ArrayList<>();
        HashMap<String, StackingItem> items = new HashMap<>();
        
        order.add("cup-1");
        order.add("cup-2");

        crazyLid.onPush(order, items, "crazylid-3");

        assertEquals(2, order.indexOf("crazylid-3"));
    }
}