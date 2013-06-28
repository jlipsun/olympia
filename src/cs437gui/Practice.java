package cs437gui;

public class Practice {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		double time = 213.3658;
		int intTime = (int)time;
		

		int seconds = intTime%60;
		
		int minutes = (intTime/60) % 60;
		
		int hours = (intTime/3600);
		
		
		
		System.out.print(hours + ":" + minutes + ":" + seconds);

	}

}
