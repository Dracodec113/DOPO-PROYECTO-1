
/**
 * Write a description of class Cup here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Cup extends StackingItem {
    private static final int DRAWCONSTANT = 20;
    public boolean hasLid;
    private Rectangle cupShape;
    
    public Cup(int id, String color, int height, int xPosition, int yPosition){
        super(id, color, DRAWCONSTANT*height);
        this.hasLid = false;
        this.cupShape = new Rectangle(DRAWCONSTANT*height, DRAWCONSTANT*height, color, xPosition, yPosition);
        cupShape.draw();
    }
    
    public void redraw(int x, int y) {
        cupShape.drawAt(x, y);
    }
}

