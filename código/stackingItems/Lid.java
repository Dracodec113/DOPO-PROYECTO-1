
/**
 * Write a description of class Lid here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Lid extends StackingItem {
    private static final int DRAWCONSTANT = 20;
    
    private Rectangle lidShape;
    public Lid(int id, String color, int xPosition, int yPosition){
        super(id, color, 1);
        this.lidShape = new Rectangle(DRAWCONSTANT, DRAWCONSTANT*id, color, xPosition, yPosition);
        lidShape.drawLid();
    }
}
