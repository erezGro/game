package main;

import java.awt.Color;
import java.util.Map;

import game.AudioPlayer;
import game.Game;
import game.PeriodicLoop;
import shapes.Circle;
import shapes.FilledShape;
import shapes.Shape;
import shapes.Text;
import my_game.GameUtils;
import my_game.MyCharacter;
import my_game.Point;
import my_game.TimerHandler;


public class MyPeriodicLoop extends PeriodicLoop {

	int moveOnceIn10miliseconds = 0;
	int gameDuration = 30;
	int InitgameDuration = 30;

	int astroidSpeed = 1000;
	int minX = 200;
	int maxX = (int) (Game.CANVA_WIDTH * 0.94);
	int minY = (int) (Game.CANVA_HEIGHT * 0.7);
	//int maxY = 600;
	int randX = 500;
	int randY = 500;
	int randXfreeze = 700;
	int randYfreeze = 300;
	int tStarX;
	int tStarY;
/* 	int fStarX;
	int fStarY; */
			int minAstX = 20;
		int maxAstX = maxX;
	String[] pids = { "p0", "p1", "p2", "p3", "p4", "p5", "p6" };

	boolean playBoom = false;
	boolean timeStarShow = false;
	boolean freezeStarStatus = false;
	boolean jitterTimeStarStatus = false;
	boolean jitterFreezeStarStatus = false;
	boolean showClock = true;
	boolean setLocationFlag = true;
	private MyContent content;
	private boolean timerFirstTimeShow = true;

	GameUtils gameUtils = new GameUtils();

	public void setContent(MyContent content) {
		this.content = content;
	}

	@Override
	public void execute() {
		// Let the super class do its work first
		super.execute();
		redrawCharacter();

	}

	private void redrawCharacter() {

		MyCharacter character = content.myCharacter();
/* 
		// TIMER UPDATING ON UI
		long elapsedTime = System.currentTimeMillis() / 1000 - Game.gameStartTime;
		long currentTimer = (gameDuration - elapsedTime);
		Text textForTimer = new Text("textForTimer", "Timer : " + currentTimer + " [sec]  ", 700, 100);
		*/
		Map<String, Long> timers = TimerHandler.setTimerValue(gameDuration);
		long elapsedTime = timers.get("elapsedTime");
		long currentTimer = timers.get("currentTimer");
//		long currentTimer = (gameDuration - elapsedTime);

		//Text textForTimer = new Text("textForTimer", "Timer : " + currentTimer + " [sec]  ", 700, 100);

		Text textForTimer = TimerHandler.setTimerTextParams(gameDuration);
		
		int freezeFactor = 0; 

/* 		int fStarX = 700;
		int fStarY = 300; */
		if (setLocationFlag) {
			setLocationFlag = false;

			GameUtils.moveShipToStartLocation(content.myCharacter(),maxAstX , minAstX ,  minY);

/* 			Game.UI().canvas().moveToLocation("space ship", 500, minY);
			content.myCharacter().setLocation(new Point(500, minY)); */
		}

		if (timerFirstTimeShow && (currentTimer <= 25)) {/* 
			randX = (int) (Math.random() * ((maxX - minX) + 1)) + minX;
			randY = (int) (minY - 0.3 * (int) (Math.random() * ((minY)))); */

			tStarX = (int) (Math.random() * ((maxX - minX) + 1)) + minX;
			tStarY = (int) (minY - 0.3 * (int) (Math.random() * ((minY))));

			randXfreeze = (int) (Math.random() * ((maxX - minX) + 1)) + minX;
			randYfreeze = (int) (minY - 0.3 * (int) (Math.random() * ((minY))));
			timeStarShow = true;
			timerFirstTimeShow = false;
/* 			tStarX = randX;
			tStarY = randY; */
		}

		if (timeStarShow) {
			Game.UI().canvas().moveToLocation("Time Star", tStarX, tStarY);
		}

		if (freezeStarStatus) {

/* 			fStarX = randXfreeze;
			fStarY = randYfreeze; */

			Game.UI().canvas().moveToLocation("Freeze Star", randXfreeze, randYfreeze);

		}
		textForTimer = TimerHandler.changeTimerParams (currentTimer,textForTimer);
/* 		if (currentTimer <= 10) {
			if ((int) currentTimer % 2 == 0) {
				textForTimer.setColor(Color.red);
				if ((int) currentTimer <= 4) {
					AudioPlayer.playSound("resources/audio/ping.wav");
				}
			} else {
				textForTimer.setColor(Color.yellow);
			}
		} else {
			textForTimer.setColor(Color.white);
		} */
/* 		textForTimer.setFontName("Helvetica");
		textForTimer.setFontSize(30);
		Game.UI().canvas().addShape(textForTimer); */
		if (elapsedTime == gameDuration) {
			Game.endGame();
		}

		// Check if user got the time bonus
		if (gameUtils.checkIfSpaceGotBonusStar(content.myCharacter(), tStarX, tStarY, timeStarShow)) {


			timeStarShow = false;
			randX = (int) (Math.random() * ((maxX - minX) + 1)) + minX;
			randY = (int) (minY - 0.3 * (int) (Math.random() * ((minY))));

			tStarX = randX; 
			tStarY = randY; 
			freezeStarStatus = true;

			AudioPlayer.playSound("resources/audio/timer.wav");

			playBoom = false;
			showClock = false;
			gameDuration = gameDuration + 10;

/* 			tStarX = 1100;
			tStarY = 1000; */

			Game.UI().canvas().moveToLocation("Time Star", Game.CANVA_WIDTH*2, Game.CANVA_HEIGHT*2);

		}

		// if spaceship gets the Freeze Bonus:
		if (gameUtils.checkIfSpaceGotBonusStar(content.myCharacter(), randXfreeze, randYfreeze, freezeStarStatus)) {
			freezeStarStatus = false;
			randXfreeze = (int) (Math.random() * ((maxX - minX) + 1)) + minX;
	
			randYfreeze = (int) (minY - 0.3 * (int) (Math.random() * ((minY))));

			tStarX = randX; 
			tStarY = randY; 
			timeStarShow = true;

			AudioPlayer.playSound("resources/audio/timer.wav");

			playBoom = false;
/* 			fStarX = 1100;
			fStarY = 1000; */

			Game.UI().canvas().moveToLocation("Freeze Star", Game.CANVA_WIDTH*2, Game.CANVA_HEIGHT*2);

			showClock = false;

			// while freeze - Astroids are BLACK:
			for (int i = 0; i < pids.length; i++) {
				Shape currentP = Game.UI().canvas().getShape(pids[i]);
				((FilledShape) currentP).setFillColor(Color.BLACK);
				Game.UI().canvas().addShape(currentP);
			}

			try {
				Thread.sleep(5000);
				gameDuration = gameDuration + 5;
				currentTimer = currentTimer + 5;
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			;

		}




		// for each astroid we do the following:
		// get location
		// check location
		// if arrived to the edge: set new randomized location
		// if Boom with the spaceship - move spaceship back down to a randomized X
		// position

		for (int i = 0; i < pids.length; i++) {
			playBoom = false;

			String currentPid = pids[i];
			Shape currentP = Game.UI().canvas().getShape(pids[i]);

			// check: do we have a boom? if we do - we move the ship back down!
			if (gameUtils.checkExplostion((Circle) currentP, content.myCharacter())) {
				GameUtils.moveShipToStartLocation(content.myCharacter(),maxAstX , minAstX ,  minY);
/* 				int randomIntX = (int) (Math.random() * ((maxAstX - minAstX) + 1)) + minAstX;
				Game.UI().canvas().moveToLocation("space ship", randomIntX, minY);
				content.myCharacter().setLocation(new Point(randomIntX, minY)); */
			}

			// if astroid is at the edge - we recreate it
			gameUtils.redrawAstroids((Circle) currentP, currentPid);

			moveOnceIn10miliseconds++; // will make astroid to move in correct speed
			// moving astroids by redrawing them:
	
			if  (((InitgameDuration - (int) elapsedTime) > 6) && (astroidSpeed>50)) {
				astroidSpeed = ((InitgameDuration - (int) elapsedTime) * 2);
			}
			// freezeFactor=1;
			if ((moveOnceIn10miliseconds >= astroidSpeed) && (freezeFactor == 0)) {

				moveOnceIn10miliseconds = 0;
				gameUtils.moveAstroids((Circle) currentP);

			}

		}

		if (character == null)
			return;
	}
}
