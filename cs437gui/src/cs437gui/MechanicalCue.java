package cs437gui;

public class MechanicalCue extends Cue {
	private String description;
	private int x,y;
	
	public MechanicalCue(double startTime, double endTime, int moduleID, String description, int x, int y) {
		super(startTime, endTime, moduleID);
		this.description = description;
		this.x = x;
		this.y = y;
	 }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
}
