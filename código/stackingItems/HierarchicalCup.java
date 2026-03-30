import java.util.ArrayList;
import java.util.HashMap;

/**
 * Write a description of class Hierarchical here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HierarchicalCup extends Cup
{
    private boolean reachedBottom;

    public HierarchicalCup(int id, String color, int height, int xPosition, int yPosition, int randomColor){
        super(id, color, height, xPosition, yPosition, randomColor);
        this.reachedBottom = false;
    }

    @Override
    public ArrayList<Integer> onPush(ArrayList<Integer> order, HashMap<Integer, StackingItem> items) {
        ArrayList<Integer> toDisplace = new ArrayList<>();
        boolean hasLarger = false;

        for (Integer id : order) {
            if (Math.abs(id) > getId()) {
                hasLarger = true;
            }
        }

        for (Integer id : order) {
            if (Math.abs(id) < getId()) {
                if (items.get(id) == null || items.get(id).canBeRemoved(order)) {
                    toDisplace.add(id);
                }
            }
        }

        if (!hasLarger) {
            reachedBottom = true;
        }

        return toDisplace;
    }

    @Override
    public boolean canBeRemoved(ArrayList<Integer> order) {
        return !reachedBottom;
    }
    @Override
    public boolean isHierarchical() {
        return true;
    }
}
