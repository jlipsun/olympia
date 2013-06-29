package cs437gui;

public class SupertitleCue extends Cue{
	private String title; 

	public SupertitleCue(double startTime, double endTime, String title) {
		super(startTime, endTime, 4);
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
