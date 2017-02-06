package routeplanning;

public class AStarTest {
	
	public static void main(String[] args) {
		State initialState = new State(0, 0);
		State goalState = new State(0, 7);
		
		AStar test = new AStar(initialState, goalState);
		System.out.println(test.findPath());
	}
}
