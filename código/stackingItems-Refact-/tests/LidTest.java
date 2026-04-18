package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import domain.Lid;

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
        assertEquals(60, lid.getWidth());
    }

    @Test
    public void shouldHaveFixedHeight() {
        assertEquals(20, lid.getHeight());
    }
}