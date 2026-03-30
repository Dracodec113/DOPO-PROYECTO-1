import org.junit.Test;
import static org.junit.Assert.*;

public class TowerContestC3Test {

    // ¿Qué debería hacer?
    
    @Test
    public void shouldSolveKnownCase() {
        String result = TowerContest.solve(4, 9);
        assertNotEquals("impossible", result);
        assertEquals(4, result.split(" ").length);
    }

    @Test
    public void shouldReturnImpossible() {
        assertEquals("impossible", TowerContest.solve(4, 100));
    }

    @Test
    public void shouldSolveWithOneCup() {
        assertEquals("1", TowerContest.solve(1, 1));
    }

    // ¿Qué NO debería hacer?
    
    @Test
    public void shouldNotAcceptNegativeHeight() {
        assertEquals("impossible", TowerContest.solve(4, -1));
    }

    @Test
    public void shouldNotAcceptZeroHeight() {
        assertEquals("impossible", TowerContest.solve(4, 0));
    }
}