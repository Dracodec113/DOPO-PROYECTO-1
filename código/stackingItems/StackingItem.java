import java.util.ArrayList;
import java.util.HashMap;

/**
 * Write a description of class StackingItem here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class StackingItem
{
    public int height;
    private int id;
    private String color;
    public int colorNum;
    /**
     * Constructor for objects of class StackingItem
     */
    public StackingItem(int id, String color, int height, int colorNum)
    {
        this.height = height;
        this.color = color;
        this.id = id;
        this.colorNum = colorNum;
    }

    public int getHeight()
    {
        return height;
    }
    public int getId()
    {
        return id;
    }
    public String getColor()
    {
        return color;
    }
    public ArrayList<Integer> onPush(ArrayList<Integer> order, HashMap<Integer, StackingItem> items) {
        return new ArrayList<>();
    }

    public boolean canBeRemoved(ArrayList<Integer> order) {
        return true;
    }
    public boolean isFearful() {
        return false;
    }
    public abstract int[] placeCorrectPosition(int baseY, int currentX, java.util.Deque<int[]> stack);
    public abstract void eraseShape();
    public abstract void redraw(int x, int y);  


}