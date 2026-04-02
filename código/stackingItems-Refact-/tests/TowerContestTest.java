package tests;



import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class TowerContestTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class TowerContestTest
{
    @Test
    public void shouldSolveKnownCase() {
        String result = domain.TowerContest.solve(4, 9);
        assertEquals("7 3 5 1", result);
    }

    @Test
    public void shouldSayImpossible() {
        String result = domain.TowerContest.solve(4, 100);
        assertEquals("impossible", result);
    }
}