package cs437gui;

import java.util.Timer;
import java.util.TimerTask;

public class Startup {
  Timer timer;

  public Startup ( int seconds )   {
    timer = new Timer (  ) ;
    timer.schedule ( new ToDoTask (  ) , seconds*1000 ) ;
  }


  class ToDoTask extends TimerTask  {
    public void run (  )   {
    	new OlympiaMainGui();
    	timer.cancel (  ) ; //Terminate the thread
    }
  }


  public static void main ( String args [  ]  )   {
    //System.out.println ( "Schedule something to do in 5 seconds." ) ;

	new Startup ( 4 ) ;
	String pic = "splash.jpg";
	SplashScreen frame = new SplashScreen(pic, null, 4000);
	frame.setVisible(true);
    //System.out.println ( "Waiting." ) ;
  }
}

