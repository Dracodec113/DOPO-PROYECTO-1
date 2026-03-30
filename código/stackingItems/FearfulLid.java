
/**
 * Write a description of class FearfulLid here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FearfulLid extends Lid
{
    public FearfulLid(int id, String color, int xPosition, int yPosition, int randomColor){
        super(id, color, xPosition, yPosition, randomColor);
    }
    @Override
    public int[] placeCorrectPosition(int baseY, int currentX, java.util.Deque<int[]> stack) {
        int companionWidth = Math.abs(getId()) * 20;
        boolean coveringCompanion = false;

        if (!stack.isEmpty()) {
            int[] topEntry = stack.peek();
            if (topEntry[3] == 0 && topEntry[0] == companionWidth) {
                coveringCompanion = true;
            }
            topEntry[3] = 1;
        }
        int lidState = coveringCompanion ? 3 : 1;
        return new int[]{getHeight(), currentX, baseY - getHeight(), lidState, getHeight()};
    }
    @Override
    public boolean isFearful() {
        return true;
    }
}
