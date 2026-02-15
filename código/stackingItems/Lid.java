
/**
 * Write a description of class Lid here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Lid extends StackingItem {
    
    private Rectangle rectangle;
    /**
     * Constructor for objects of class Lid
     */
    public Lid(int id, String color) 
    {
        super(id, color, 1);
        rectangle = new Rectangle(id, color);
    }
    @Override
    public void makeVisible() 
    {
        rectangle.makeVisible();
    }

    @Override
    public void makeInvisible() 
    {
        rectangle.makeInvisible();
    }
    @Override
    public void moveVertical(int distance) 
    {
        rectangle.moveVertical(distance);
    }
    @Override
    public void moveHorizontal(int distance) 
    {
        rectangle.moveHorizontal(distance);
    }
}
