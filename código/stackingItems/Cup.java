
/**
 * Write a description of class Cup here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Cup extends StackingItem {
    private static final int DRAWCONSTANT = 20;
    public boolean hasLid;
    public Rectangle cupShape;
    
    public Cup(int id, String color, int height, int xPosition, int yPosition, int randomColor){
        super(id, color, DRAWCONSTANT * height, randomColor);
        this.hasLid = false;
        this.cupShape = new Rectangle(DRAWCONSTANT * height, DRAWCONSTANT * id, color, xPosition, yPosition);
        cupShape.draw();
    }
    
    public void redraw(int x, int y) {
        cupShape.drawAt(x, y);
    }
    
    @Override
    public void eraseShape(){
        cupShape.erase();
    }
}

