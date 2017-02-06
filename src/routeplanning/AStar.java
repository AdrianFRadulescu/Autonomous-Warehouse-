package routeplanning;

public class AStar {
	private State initialState;
	private final State goalState;
	private String actions, result = "";
	
	public AStar(State _initialState, State _goalState) {
		this.initialState = _initialState;
		this.goalState = _goalState;
	}
	
	public String findPath() {
		int count = 0;
		int distance = Moves.maxDistance + 1;
		
		while ((initialState.getX() != goalState.getX() || initialState.getY() != goalState.getY()) && count != 15) {

			State tmpStateForward = new State(initialState.getX(), initialState.getY());
			State tmpStateBackward = new State(initialState.getX(), initialState.getY());
			State tmpStateLeft = new State(initialState.getX(), initialState.getY());
			State tmpStateRight = new State(initialState.getX(), initialState.getY());
			
			if (Moves.moveForward(tmpStateForward)) {
				tmpStateForward = Moves.changeStateY(tmpStateForward.getY() + 1, tmpStateForward);
				int tmpDistance = manhatanDistace(tmpStateForward, goalState);
				if (distance > tmpDistance) {
					distance = tmpDistance;
					initialState = tmpStateForward;
					actions = Actions.north;
				}
			}

			if (Moves.moveBackward(tmpStateBackward)) {
				tmpStateBackward = Moves.changeStateY(tmpStateBackward.getY() - 1, tmpStateBackward);
				int tmpDistance = manhatanDistace(tmpStateBackward, goalState); 
				if (distance > tmpDistance) {
					distance = tmpDistance;
					initialState = tmpStateBackward;
					actions = Actions.south;
				}
			}

			if (Moves.moveLeft(tmpStateLeft)) {
				tmpStateLeft = Moves.changeStateX(tmpStateLeft.getX() - 1, tmpStateLeft);
				int tmpDistance = manhatanDistace(tmpStateLeft, goalState);
				if (distance > tmpDistance) {
					distance = tmpDistance;
					initialState = tmpStateLeft;
					actions = Actions.west;
				}
			}
			
			if (Moves.moveRight(tmpStateRight)) {
				tmpStateRight = Moves.changeStateX(tmpStateRight.getX() + 1, tmpStateRight);
				int tmpDistance = manhatanDistace(tmpStateRight, goalState);
				if (distance > tmpDistance) {
					distance = tmpDistance;
					initialState = tmpStateRight;
					actions = Actions.east;
				}
			}
			
			result += actions;
		}
		return result;
	}
	
	public int manhatanDistace(State s1, State s2) {
		return (Math.abs(s1.getX() - s2.getX()) + Math.abs(s1.getY() - s2.getY()));
	}
}
