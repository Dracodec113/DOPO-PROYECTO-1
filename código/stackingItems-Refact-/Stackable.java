import java.util.*;

/**
 * Interface for items with special behavior when added to the tower.
 */
public interface Stackable {
    /**
     * Defines what happens to the tower order when this item is pushed.
     * @param currentOrder The current list of keys in the tower.
     * @param items The map of all items.
     * @return A list of IDs affected by this action (to be removed or moved).
     */
    ArrayList<Integer> onPush(ArrayList<String> currentOrder, HashMap<String, StackingItem> items);
}