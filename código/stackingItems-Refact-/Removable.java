import java.util.*;

/**
 * Interface to check if an item can be legally removed from the tower.
 */
public interface Removable {
    /**
     * Checks if the item can be removed based on the current tower state.
     * @param currentOrder The current list of keys.
     * @return true if the removal is allowed, false otherwise.
     */
    boolean canRemove(ArrayList<String> currentOrder);
}