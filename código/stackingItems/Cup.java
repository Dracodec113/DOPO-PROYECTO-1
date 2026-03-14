
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
    @Override
    public int[] placeCorrectPosition(int baseY, int currentX, java.util.Deque<int[]> stack) {
        int itemWidth = Math.abs(getId()) * 20;
        int[] validContainer = null;
        int[] topItem = null;
    
        for (int[] entry : stack) {
            if (entry[0] > itemWidth && entry[3] == 0) {
                validContainer = entry;
                break;
            } else if (entry[0] > itemWidth && entry[3] == 1) {
                break;
            }
            if (topItem == null) topItem = entry;
        }
    
        
        int finalX = currentX;
        int finalY = baseY;
    
        if (validContainer != null) {
            if (topItem != null && topItem[0] < validContainer[0]) {
                finalX = topItem[1] + (topItem[0] - itemWidth) / 2;
                finalY = topItem[2];
            } else {
                int offset = (validContainer[0] - itemWidth) / 2;
                finalX = validContainer[1] + offset;
                finalY = validContainer[2] + validContainer[4] - 20;
            }
        }
    
        return new int[]{itemWidth, finalX, finalY - getHeight(), 0, getHeight()};
    }
}

