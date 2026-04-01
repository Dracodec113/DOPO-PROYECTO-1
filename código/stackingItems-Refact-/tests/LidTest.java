// tests/LidTest.java
package tests;

import domain.*;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class LidTest {

    private Lid lid;

    @Before
    public void setUp() {
        lid = new Lid(2, "blue", 0, 0, 2, 2);
    }

    @Test
    public void shouldAlwaysBeRemovable() {
        ArrayList<String> order = new ArrayList<>();
        order.add("normalCup-2");
        order.add("lid-2");
        assertTrue(lid.canRemove(order));
    }

    @Test
    public void shouldHaveCorrectWidth() {
        // id=2 → cellsWide = (2*2)-1 = 3 → width = 3*20 = 60
        assertEquals(60, lid.getWidth());
    }

    @Test
    public void shouldHaveFixedHeight() {
        assertEquals(20, lid.getHeight());
    }
}