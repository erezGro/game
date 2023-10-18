package main;

import java.awt.Color;
// import java.awt.Image;

import game.AudioPlayer;
import game.Game;
import game.PeriodicLoop;
import shapes.Circle;
import shapes.FilledShape;
import shapes.Shape;
import shapes.Text;
import my_game.MyCharacter;
import my_game.Point;
// import my_game.TimeStar;
// import my_game.FreezeStar;

public class MyPeriodicLoop extends PeriodicLoop {

	int moveOnceIn10miliseconds = 0;
	int gameDuration = 30;	
	int InitgameDuration = 30;

	int astroidSpeed = 200;
	int minX = 200;
	int maxX = 800;
	int minY = 200;
	int maxY = 600;
	int randX = 500;
	int randY = 500;
	int randXfreeze = 700;
	int randYfreeze = 300;
	int tStarX;
	int tStarY;
	String[] pids = { "p0", "p1", "p2", "p3", "p4", "p5", "p6" };

	boolean playBoom = false;
	boolean timeStarShow = false;
	boolean freezeStarStatus = false;
	boolean jitterTimeStarStatus = false;
	boolean jitterFreezeStarStatus = false;
	boolean showClock = true;
	boolean setLocationFlag = true;
	private MyContent content;
	private boolean timerFirstTimeShow=true;

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
		// randX = (int) (Math.random() * ((maxX - minX) + 1)) + minX;
		// randY = (int) (Math.random() * ((maxY - minY) + 1)) + minY;
		MyCharacter character = content.myCharacter();

		// TIMER UPDATING ON UI
		long elapsedTime = System.currentTimeMillis() / 1000 - Game.gameStartTime;
		long CurrentTimer = (gameDuration - elapsedTime);
		Text textForTimer = new Text("textForTimer", "Timer : " + CurrentTimer + " [sec]  ", 700, 100);
		int freezeFactor = 0;

		int fStarX = 700;
		int fStarY = 300;
		if (setLocationFlag) {
			setLocationFlag = false;

			Game.UI().canvas().moveToLocation("space ship", 500, 600);
			content.myCharacter().setLocation(new Point(500, 600));
		}

		if (timerFirstTimeShow && (CurrentTimer <= 28)) {// &&(!freezeStarStatus)) {
			// System.out.println("FIRST TIME SHOWING TIMER BONUS");
			randX = (int) (Math.random() * ((maxX - minX) + 1)) + minX;
			randY = (int) (Math.random() * ((maxY - minY) + 1)) + minY;
			
			randXfreeze = (int) (Math.random() * ((maxX - minX) + 1)) + minX;
			randYfreeze = (int) (Math.random() * ((maxY - minY) + 1)) + minY;

			timeStarShow = true;
			timerFirstTimeShow = false;
			tStarX = randX;
			tStarY = randY;
			// System.out.println("X AND Y:"+randX+" : "+randY);

		}

		if (timeStarShow) {
			Game.UI().canvas().moveToLocation("Time Star", tStarX, tStarY);
			//timeStarShow = false;
		}

		if (freezeStarStatus) {

			fStarX = randXfreeze;// randX;//-20*(int)CurrentTimer;
			fStarY = randYfreeze;// randY;//-20*(int)CurrentTimer;

			Game.UI().canvas().moveToLocation("Freeze Star", fStarX, fStarY);

		}

		if (CurrentTimer <= 10) {
			if ((int) CurrentTimer % 2 == 0) {
				textForTimer.setColor(Color.red);
			} else {
				textForTimer.setColor(Color.yellow);
			}
		} else {
			textForTimer.setColor(Color.white);
		}
		textForTimer.setFontName("Helvetica");
		textForTimer.setFontSize(30);
		Game.UI().canvas().addShape(textForTimer);
		if (elapsedTime == gameDuration) {
			Game.endGame();
		}

		int shipX = content.myCharacter().getLocation().x;
		int shipY = content.myCharacter().getLocation().y;

		int shipX2 = shipX - 5;
		int shipY2 = shipY - 5;


		if ((shipX >= tStarX - 40) && (shipY >= tStarY - 40) && (shipX <= tStarX + 40) && (shipY <= tStarY + 40)
				&& (shipX < 950) && (shipX > 60) && (timeStarShow)) {
			// System.out.println("BOOM WITH TIMER");

			timeStarShow = false;
			randX = (int) (Math.random() * ((maxX - minX) + 1)) + minX;
			randY = (int) (Math.random() * ((maxY - minY) + 1)) + minY;
			tStarX = randX; // 500;//randX;//-20*(int)CurrentTimer;
			tStarY = randY; // 500;//randY;//-20*(int)CurrentTimer;
			freezeStarStatus = true;

			AudioPlayer.playSound("resources/audio/timer.wav");

			playBoom = false;
			showClock = false;
			gameDuration = gameDuration + 10;

			tStarX = 1100;
			tStarY = 1000;

			Game.UI().canvas().moveToLocation("Time Star", tStarX, tStarY);

		}

		// if spaceship gets the Freeze Bonus:
		if ((shipX >= fStarX - 40) && (shipY >= fStarY - 40) && (shipX <= fStarX + 40) && (shipY <= fStarY + 40)
				&& (shipX < 950) && (shipX > 60) && (freezeStarStatus)) {
			freezeStarStatus = false;
			randXfreeze = (int) (Math.random() * ((maxX - minX) + 1)) + minX;
			randYfreeze = (int) (Math.random() * ((maxY - minY) + 1)) + minY;
			tStarX = randX; // 500;//randX;//-20*(int)CurrentTimer;
			tStarY = randY; // 500;//randY;//-20*(int)CurrentTimer;
			timeStarShow = true;

			AudioPlayer.playSound("resources/audio/timer.wav");

			playBoom = false;
			fStarX = 1100;
			fStarY = 1000;

			Game.UI().canvas().moveToLocation("Freeze Star", fStarX, fStarY);

			showClock = false;
			// freezeFactor=1;

			// while freeze - Astroids are BLACK:
			for (int i = 0; i <  pids.length; i++) {
			Shape currentP = Game.UI().canvas().getShape(pids[i]);
			((FilledShape) currentP).setFillColor(Color.BLACK);
			Game.UI().canvas().addShape(currentP);
		}

			try {
				Thread.sleep(5000);
				gameDuration = gameDuration + 5;
				CurrentTimer = CurrentTimer + 5;
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			;

		}

		int minAstX = 20;
		int maxAstX = 500;
		int minAstY = 0;
		int maxAstY = 700;
		int minR = 10;
		int maxR = 40;

		// for each astroid we do the following:
		// 		get location
		// 		check location
		//		if arrived to the edge: set new randomized location
		// 		if Boom with the spaceship - move spaceship back down to a randomized X
		// 		position

		for (int i = 0; i <  pids.length; i++) {
			playBoom = false;

			String currentPid = pids[i];
			Shape currentP = Game.UI().canvas().getShape(pids[i]);

			int radius = ((Circle) currentP).getRadius();
			int px1 = ((Circle) currentP).x1CircPosition();
			int px2 = ((Circle) currentP).x2CircPosition();
			int py1 = ((Circle) currentP).y1CircPosition();
			int py2 = ((Circle) currentP).y2CircPosition();

			// check: do we have a boom? if we do - we move the ship back down!
			Color currentColor = ((Circle) currentP).getFillColor();

			if ((currentColor != Color.RED) && (shipX >= px1 - 3 * radius) && (shipX2 <= px1 + radius)
					&& (shipY >= py1 - 2 * radius)
					&& (shipY2 <= py2 + radius)) {

				int randomIntX = (int) (Math.random() * ((maxAstX - minAstX) + 1)) + minAstX;

				Game.UI().canvas().moveToLocation("space ship", randomIntX, 600);
				content.myCharacter().setLocation(new Point(randomIntX, 600));
				playBoom = true;
				// Game.UI().canvas().moveToLocation("space ship", randomIntX, 700);
				// content.myCharacter().setLocation(new Point(randomIntX, 700));

				shipX = content.myCharacter().getLocation().x;
				shipY = content.myCharacter().getLocation().y;

				Circle p = new Circle(currentPid, px1, py1, radius);
				p.setFillColor(Color.RED);
				p.setIsFilled(true);
				p.setzOrder(3);
				Game.UI().canvas().addShape(p);

				if (playBoom == true) {
					// Game.audioPlayer();
					AudioPlayer.playSound("resources/audio/boom.wav");
					playBoom = false;
				}

			}

			// if astroid is at the edge - we recreate it
			if ((px1 <= 20) || (px2 >= 994) || (py1 >= 1000) || (py2 <= 0)) {

				int randomIntX = (int) (Math.random() * ((maxAstX - minAstX) + 1)) + minAstX;
				int randomIntY = (int) (Math.random() * ((maxAstY - minAstY) + 1)) + minAstY;
				int randomIntR = (int) (Math.random() * ((maxR - minR) + 1)) + minR;

				Circle p = new Circle(currentPid, randomIntX, randomIntY, randomIntR);
				p.setFillColor(Color.WHITE);
				p.setIsFilled(true);
				p.setzOrder(3);
				Game.UI().canvas().addShape(p);
			}

			moveOnceIn10miliseconds++; // will make astroid to move in correct speed
			// moving astroids by redrawing them:
			// astroidSpeed = astroidSpeed-1;
			if ((InitgameDuration-(int)elapsedTime)>6){
				astroidSpeed=((InitgameDuration-(int)elapsedTime)*2);
			}
freezeFactor=1;
			if ((moveOnceIn10miliseconds >= astroidSpeed) && (freezeFactor == 0)) {

				moveOnceIn10miliseconds = 0;
				Game.UI().canvas().moveShape("p0", 1, 1);
				Game.UI().canvas().moveShape("p1", -1, 1);
				Game.UI().canvas().moveShape("p2", 1, 1);
				Game.UI().canvas().moveShape("p3", 1, 1);
				Game.UI().canvas().moveShape("p4", -1, 1);
				Game.UI().canvas().moveShape("p5", 1, 1);
				Game.UI().canvas().moveShape("p6", -1, 1);
			}

		}

		if (character == null)
			return;

	}

}
