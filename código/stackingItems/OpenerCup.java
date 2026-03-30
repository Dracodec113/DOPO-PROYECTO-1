import java.util.ArrayList;
import java.util.HashMap;

/**
 * Write a description of class OpenerCup here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class OpenerCup extends Cup
{
    public OpenerCup(int id, String color, int height, int xPosition, int yPosition, int randomColor){
        super(id, color, height, xPosition, yPosition, randomColor);
    }

    @Override
    public ArrayList<Integer> onPush(ArrayList<Integer> order, HashMap<Integer, StackingItem> items) {
        ArrayList<Integer> toRemove = new ArrayList<>();
        for (Integer id : order) {
            if (id < 0 && Math.abs(id) < getId()) {
                toRemove.add(id);
            }
        }
        return toRemove;
    }
    @Override
    public boolean isOpener() {
        return true;
    }
}