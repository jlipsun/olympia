package cs437gui;

public class LightCue extends Cue{
		
	private int component;
	private int value;
	
	public LightCue(double startTime, double endTime, int moduleID, int component, int value) {
		super(startTime, endTime, moduleID);
		this.component = component;
		this.value = value;
	}
	
	public int getComponent() {
		return component;
	}

	public int getValue() {
		return value;
	}

	public void setComponent(int component) {
		this.component = component;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
}
