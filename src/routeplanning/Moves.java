package routeplanning;

public class Moves {
	
	public static final int xMax = 11;
	public static final int yMax = 7;
	public static final int maxDistance = 18;
	
	public static boolean moveForward(State _state) {
		int change = _state.getY() + 1;
		if (change <= yMax) {
			return true;
		}
		return false;
	}
	
	public static boolean moveBackward(State _state) {
		int change = _state.getY() - 1;
		if (change >= 0) {
			return true;
		}
		return false;
	}
	
	public static State changeStateY (int change, State _state) {
		_state.setY(change);
		return _state;
	}
	
	public static boolean moveRight(State _state) {
		int change = _state.getX() + 1;
		if (change <= xMax) {
			return true;
		}
		return false;
	}
	
	public static boolean moveLeft(State _state) {
		int change = _state.getX() - 1;
		if (change >= 0) {
			return true;
		}
		return false;
	}
	
	public static State changeStateX (int change, State _state) {
		_state.setX(change);
		return _state;
	}
}
