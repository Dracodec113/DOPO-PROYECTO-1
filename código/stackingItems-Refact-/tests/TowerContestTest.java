package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import domain.TowerContest;

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


@Test
public void shouldHandleTowerContestLogic() {

    assertEquals("impossible", TowerContest.solve(3, 2));  
    assertEquals("impossible", TowerContest.solve(3, 10));  
    assertEquals("impossible", TowerContest.solve(3, 7));   
    

    assertNotEquals("impossible", TowerContest.solve(4, 9));
    assertEquals("impossible", TowerContest.solve(2, 5)); 

    TowerContest.solve(4, 10); 
	}

	@Test
	public void shouldCalculateHeightCorrectly() {
	    int[] order1 = {7, 5, 3, 1};
	    assertTrue(TowerContest.calculateHeight(order1) > 0);
	    
	    int[] order2 = {1, 3, 5, 7}; // Todo encima
	    assertTrue(TowerContest.calculateHeight(order2) > 0);
	    
	    assertEquals(0, TowerContest.calculateHeight(new int[]{}));
	}
}