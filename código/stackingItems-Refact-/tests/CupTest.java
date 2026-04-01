// tests/CupTest.java
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
        cup = new Cup(3, "yellow", 0, 0, 3);
    }

    @Test
    public void shouldHaveCorrectWidth() {
        // id=3 → cellsWide = (2*3)-1 = 5 → width = 5*20 = 100
        assertEquals(100, cup.getWidth());
    }

    @Test
    public void shouldReturnEmptyListOnPush() {
        ArrayList<String> order = new ArrayList<>();
        HashMap<String, StackingItem> items = new HashMap<>();
        ArrayList<Integer> affected = cup.onPush(order, items);
        assertTrue(affected.isEmpty());
    }

    @Test
    public void shouldHaveCorrectHeight() {
        // id=3 → cellsWide = 5 → height = 5*20 = 100
        assertEquals(100, cup.getHeight());
    }
}