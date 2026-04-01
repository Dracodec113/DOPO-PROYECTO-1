package tests;

import domain.*;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class FearfulLidTest {

    private FearfulLid fearfulLid;

    @Before
    public void setUp() {
        fearfulLid = new FearfulLid(3, "red", 0, 0, 3, 1);
    }

    @Test
    public void shouldRequireCompanion() {
        assertTrue(fearfulLid.requiresCompanion());
    }

    @Test
    public void shouldNotBeRemovableWhenCoveringCompanion() {
        // La taza está justo antes que la tapa en el order
        ArrayList<String> order = new ArrayList<>();
        order.add("normalCup-3");
        order.add("lid-3");
        assertFalse(fearfulLid.canRemove(order));
    }

    @Test
    public void shouldBeRemovableWhenNotCoveringCompanion() {
        // Hay algo entre la taza y la tapa
        ArrayList<String> order = new ArrayList<>();
        order.add("normalCup-3");
        order.add("normalCup-1");
        order.add("lid-3");
        assertTrue(fearfulLid.canRemove(order));
    }
}