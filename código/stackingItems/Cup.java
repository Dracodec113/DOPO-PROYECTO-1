
/**
 * Write a description of class Cup here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Cup extends StackingItem {

    private boolean hasLid;
    private Rectangle rectangle;
    /**
     * Constructor for objects of class Cup
     */
    public Cup(int id, String color) 
    {
        super(id, color, id);
        this.hasLid = false;
        this.rectangle = new Rectangle(id, color);
    }

    public void addLid() 
    {
        hasLid = true;
    }

    public boolean hasLid() 
    {
        return hasLid;
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

