package game;

import static java.util.concurrent.TimeUnit.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import gui.Sleeper;

public class PeriodicScheduler {
	public static int periodicInterval = 1;
	private PeriodicLoop periodicLoop;
	private final ScheduledExecutorService scheduler =
			Executors.newScheduledThreadPool(1);
	public ScheduledFuture<?> beeperHandle;

	public static void setPeriodicInterval(int miliseconds) {
		periodicInterval = miliseconds;
	}
	
	public void setPeriodicLoop(PeriodicLoop myPeriodicLoop) {
		this.periodicLoop = myPeriodicLoop;
	}
	public void start() {
		  
	    final Runnable beeper = new Runnable() {
	    	public void run() { periodicLoop.execute(); }
	    };
	    
	    beeperHandle = scheduler.scheduleAtFixedRate(beeper, 0, periodicInterval, MILLISECONDS);
	}
	  
	  public void end() {
		  beeperHandle.cancel(true);
	  }
	  
	  public static void main(String[] args) {
		PeriodicScheduler ps = new PeriodicScheduler();
		ps.start();
		Sleeper.sleep(8000);
		ps.end();
		System.out.println("end");
	  }

	public static long PeriodicInterval(int i) {
		return 0;
	}
}


