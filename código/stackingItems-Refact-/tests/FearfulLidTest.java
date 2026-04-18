package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import domain.FearfulLid;
import domain.StackingItem;

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
        ArrayList<String> order = new ArrayList<>();
        HashMap<String, StackingItem> items = new HashMap<>();
        
        order.add("cup-3");
        order.add("fearful-3");
        
        fearfulLid.onPush(new ArrayList<>(), items, "fearful-3");

        assertFalse(fearfulLid.canRemove(order));
    }

    @Test
    public void shouldBeRemovableWhenNotCoveringCompanion() {
        ArrayList<String> order = new ArrayList<>();
        HashMap<String, StackingItem> items = new HashMap<>();
        
        order.add("cup-3");
        order.add("cup-1");
        order.add("fearful-3");
        
        fearfulLid.onPush(new ArrayList<>(), items, "fearful-3");

        assertTrue(fearfulLid.canRemove(order));
    }

    @Test
    public void shouldBeRemovableIfNoItemBelow() {
        ArrayList<String> order = new ArrayList<>();
        HashMap<String, StackingItem> items = new HashMap<>();
        
        order.add("fearful-3");
        
        fearfulLid.onPush(new ArrayList<>(), items, "fearful-3");

        assertTrue(fearfulLid.canRemove(order));
    }
}