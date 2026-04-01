package tests;

import domain.*;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;

public class CrazyLidTest {

    private CrazyLid crazyLid;

    @Before
    public void setUp() {
        crazyLid = new CrazyLid(3, "blue", 0, 0, 3, 2);
    }

    @Test
    public void shouldGoToBase() {
        assertTrue(crazyLid.goesToBase());
    }

    @Test
    public void shouldAlwaysBeRemovable() {
        ArrayList<String> order = new ArrayList<>();
        order.add("normalCup-3");
        order.add("lid-3");
        assertTrue(crazyLid.canRemove(order));
    }

    @Test
    public void shouldBeRemovableEvenWhenCoveringCompanion() {
        // CrazyLid no tiene regla de salida — siempre puede salir
        ArrayList<String> order = new ArrayList<>();
        order.add("lid-3");
        order.add("normalCup-3");
        assertTrue(crazyLid.canRemove(order));
    }
}