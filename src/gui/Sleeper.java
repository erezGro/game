package gui;

public class Sleeper {
	public static void sleep(long miliseconds) {
		try {
			Thread.sleep(miliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
