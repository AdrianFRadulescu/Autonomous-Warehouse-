package routeplanning;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class PathfindTest {

	@Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {     
                 { new State(0, 3), new State(0, 0), "SSS" }, { new State(0, 3), new State(3, 0), "SSSEEE" }, { new State(1, 5), new State(3, 7), "NNEE" }, 
                 { new State(11, 5), new State(4, 0), "SSSSSWWWWWWW" }, { new State(0, 0), new State(11, 7), "NNNNNNNEEEEEEEEEEE" }, { new State(9, 6), new State(3, 3), "SSSWWWWWW" }, 
                 { new State(0, 0), new State(0, 7), "NNNNNNN" }  });
    }
    
    private State mInput1;
    private State mInput2;
    private String mExpected;
    
    public PathfindTest(State mInput1, State mInput2, String mExpected) {
    	this.mInput1 = mInput1;
    	this.mInput2 = mInput2;
    	this.mExpected = mExpected;
    }
    
    private AStar as;
    
    @Before
    public void before(){
    	as = new AStar(mInput1, mInput2);
    }
    
	@Test
	public void test() {
		assertEquals(mExpected, as.findPath());
	}


}
