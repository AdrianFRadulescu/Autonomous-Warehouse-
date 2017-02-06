package routeplanning;

public class State {	
	private int x;
	private int y; 
	
	public State(int _x, int _y) {
		this.x = _x;
		this.y = _y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setX(int _x) {
		this.x = _x;
	}
	
	public void setY(int _y) {
		this.y = _y;
	}
}
