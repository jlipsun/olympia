package cs437gui;
	
public class LightingCue extends Cue {
	
	//x position of light
	private int xposition;
	//y position of light
	private int yposition;
	private int rvalue;
	private int gvalue;
	private int bvalue;
	private String goboPattern;
	//radius of beam of light
	private int flood;
	//vertical stretch
	private int tilt;
	//horizontal stretch
	private int pan;
	
		
	public LightingCue(double startTime, double endTime, int xposition, int yposition, int rvalue, int gvalue, int bvalue, String goboPattern, int flood, int tilt, int pan) {		
		//in super is thing all cues have in common and some kind of id
		super(startTime, endTime, 5);
		
		this.xposition = xposition;
		this.yposition = yposition;
		this.rvalue = rvalue;
		this.gvalue = gvalue;
		this.bvalue = bvalue;
		this.goboPattern = goboPattern;
		this.flood = flood;
		this.tilt = tilt;
		this.pan = pan;
	}


	public int getXposition() {
		return xposition;
	}


	public void setXposition(int xposition) {
		this.xposition = xposition;
	}


	public int getYposition() {
		return yposition;
	}


	public void setYposition(int yposition) {
		this.yposition = yposition;
	}


	public int getRvalue() {
		return rvalue;
	}


	public void setRvalue(int rvalue) {
		this.rvalue = rvalue;
	}


	public int getGvalue() {
		return gvalue;
	}


	public void setGvalue(int gvalue) {
		this.gvalue = gvalue;
	}


	public int getBvalue() {
		return bvalue;
	}


	public void setBvalue(int bvalue) {
		this.bvalue = bvalue;
	}


	public String getGoboPattern() {
		return goboPattern;
	}


	public void setGoboPattern(String goboPattern) {
		this.goboPattern = goboPattern;
	}


	public int getFlood() {
		return flood;
	}


	public void setFlood(int flood) {
		this.flood = flood;
	}


	public int getTilt() {
		return tilt;
	}


	public void setTilt(int tilt) {
		this.tilt = tilt;
	}


	public int getPan() {
		return pan;
	}


	public void setPan(int pan) {
		this.pan = pan;
	}

}