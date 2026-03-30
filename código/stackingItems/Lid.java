
/**
 * Write a description of class Lid here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Lid extends StackingItem {
    private static final int DRAWCONSTANT = 20;
    
    public Rectangle lidShape;
    public Lid(int id, String color, int xPosition, int yPosition, int randomColor){
        super(id, color, DRAWCONSTANT, randomColor);
        this.lidShape = new Rectangle(DRAWCONSTANT, DRAWCONSTANT*id, color, xPosition, yPosition);
        lidShape.drawLid();
    }
    public void redraw(int x, int y) {
        lidShape.drawLidAt(x, y);
    }
    
    @Override
    public void eraseShape(){
        lidShape.erase();
    }
    
    @Override
    public int[] placeCorrectPosition(int baseY, int currentX, java.util.Deque<int[]> stack) {
        if (!stack.isEmpty()) stack.peek()[3] = 1;
        return new int[]{getHeight(), currentX, baseY - getHeight(), 1, getHeight()};
    }
}
