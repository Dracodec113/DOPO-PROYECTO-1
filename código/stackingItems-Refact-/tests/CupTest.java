package tests;

import domain.*;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;

public class CupTest {

    private Cup cup;

    @Before
    public void setUp() {
        cup = new Cup(3, "yellow", 0, 0, 3, "standard");
    }

    @Test
    public void shouldHaveCorrectWidth() {
        assertEquals(100, cup.getWidth());
    }

    @Test
    public void shouldReturnEmptyListOnPush() {
        ArrayList<String> order = new ArrayList<>();
        HashMap<String, StackingItem> items = new HashMap<>();
        ArrayList<Integer> affected = cup.onPush(order, items, "cup1");
        assertTrue(affected.isEmpty());
        assertEquals(1, order.size());
    }

    @Test
    public void shouldHaveCorrectHeight() {
        assertEquals(100, cup.getHeight());
    }
}