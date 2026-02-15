
/**
 * Write a description of class StackingItem here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class StackingItem
{
    // instance variables - replace the example below with your own
    private int height;
    private int id;
    private String color;
    /**
     * Constructor for objects of class StackingItem
     */
    public StackingItem(int id, String color, int height)
    {
        this.height = height;
        this.color = color;
        this.id = id;
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
    public abstract void makeVisible();
    public abstract void makeInvisible();
    public abstract void moveVertical(int distance);
    public abstract void moveHorizontal(int distance);
}