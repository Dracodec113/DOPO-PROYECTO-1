
/**
 * Write a description of class CrazyLid here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CrazyLid extends Lid
{
    public CrazyLid(int id, String color, int xPosition, int yPosition, int randomColor){
        super(id, color, xPosition, yPosition, randomColor);
    }
    
    @Override 
    public int[] placeCorrectPosition(int baseY, int currentX, java.util.Deque<int[]> stack) {
        int towerBaseWidth = Math.abs(getId()) * 20;
        return new int[]{towerBaseWidth, currentX, baseY - getHeight(), 0, getHeight()};
    }
}