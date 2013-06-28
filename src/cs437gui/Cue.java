package cs437gui;

public class Cue {
    private double startTime;
    private double endTime;
    private int moduleID;

    public Cue(double startTime, double endTime, int moduleID) {
    	this.startTime = startTime;
    	this.endTime = endTime;
    	this.moduleID = moduleID;
    }

    public void printCue(){
		System.out.println("Start Time: " + startTime);
		System.out.println("End Time: " + endTime);
		System.out.println("Module ID: " + moduleID);
    	
    }
    
    public double getStartTime() {
            return startTime;
    }

    public double getEndTime() {
            return endTime;
    }

    public void setStartTime(long startTime) {
            this.startTime = startTime;
    }

    public void setEndTime(long endTime) {
            this.endTime = endTime;
    }

    public double getDuration() {
            return endTime - startTime;
    }

    public boolean isActive(double time) {
            return (time >= startTime) && (time <= endTime);
    }

    public void setModuleID(int moduleID) {
            this.moduleID = moduleID;
    }

    public int getModuleID() {
            return this.moduleID;
    }
}
