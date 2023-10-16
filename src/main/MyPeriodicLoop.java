package main;

import java.awt.Color;

import game.AudioPlayer;
import game.Game;
import game.PeriodicLoop;
import shapes.Circle;
import shapes.Shape;
import shapes.Text;
import my_game.MyCharacter;
import my_game.Point;
import my_game.TimeStar;

public class MyPeriodicLoop extends PeriodicLoop {

	int moveOnceIn10miliseconds = 0;
	int gameDuration = 30;
	int astroidSpeed = 200;
	boolean playBoom = false;

		boolean showClock = true;

	private MyContent content;

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

		// TIMER UPDATING ON UI
		long elapsedTime = System.currentTimeMillis() / 1000 - Game.gameStartTime;
		long CurrentTimer = (gameDuration - elapsedTime);
		Text textForTimer = new Text("textForTimer", "Timer : " + CurrentTimer + " [sec]  ", 700, 100);
		
		int randTimeMax = 15;
		int randTimeMin = 5;

		int randomIntTime = (int) (Math.random() * ((randTimeMax - randTimeMin) + 1)) + randTimeMin;
		int tStarX = 0;
		int tStarY = 0;
		if ((CurrentTimer < randomIntTime)&&(showClock)) {
			tStarX = 500;//-20*(int)CurrentTimer;
			tStarY = 500;//-20*(int)CurrentTimer;

		//	TimeStar timeStar = content.timeStar();
			Game.UI().canvas().moveToLocation("Time Star",tStarX,tStarY);

		} 
		if (CurrentTimer < 10) {
			textForTimer.setColor(Color.red);
		} else {
			textForTimer.setColor(Color.white);
		}
		textForTimer.setFontName("Helvetica");
		textForTimer.setFontSize(30);
		Game.UI().canvas().addShape(textForTimer);
		if (elapsedTime == gameDuration) {
			Game.endGame();
		}

//			content.timeStar().setLocation(new Point(100, 100));
//			content.timeStar().moveLocation(-50,50);
		// int tStarX = content.timeStar().getLocation().x;
		// int tStarY = content.timeStar().getLocation().y;
		int tStarX2 = tStarX - 50;
		int tStarY2 = tStarY - 50;

		int shipX = content.myCharacter().getLocation().x;
		int shipY = content.myCharacter().getLocation().y;
		// System.out.println("key character = '" + shipX + "'" + " pressed.");
		// System.out.println("key character = '" + shipY + "'" + " pressed.");

		int shipX2 = shipX - 5;
		int shipY2 = shipY - 5;

		// if spaceship gets the Timer Bonus:
		if ((shipX >= tStarX) && (shipY >= tStarY-20)&& (shipX <= tStarX+60) && (shipY <= tStarY+60) && (shipX < 950) && (shipX > 60)) {
				//Game.audioPlayer();
				//Game.audioPlayer();
				AudioPlayer.playSound("resources/audio/timer.wav");
//							AudioPlayer.playSound("resources/clock.wav");

					playBoom = false;
				//AudioPlayer.playSound("resources/clock.wav");
				showClock = false;
				gameDuration=gameDuration+10;
				Game.UI().canvas().moveToLocation("Time Star",1100,1000);

		}
		int minX = 20;
		int maxX = 500;
		int minY = 0;
		int maxY = 700;
		int minR = 10;
		int maxR = 40;


		// for each astroid we need:
		// get location
		// check location
		// if arrived to the edge: set new randomized location
		// if Boom with the spaceship - move spaceship back down to a randomized X position

		String[] pids = { "p0", "p1", "p2", "p3", "p4", "p5", "p6" };
		for (int i = 0; i < 6; i++) {// pids.length; i++) {
			playBoom = false;

			String currentPid = pids[i];
			Shape currentP = Game.UI().canvas().getShape(pids[i]);

			int radius = ((Circle) currentP).getRadius();

			int px1 = ((Circle) currentP).x1CircPosition();
			int px2 = ((Circle) currentP).x2CircPosition();
			int py1 = ((Circle) currentP).y1CircPosition();
			int py2 = ((Circle) currentP).y2CircPosition();

			// check: do we have a boom? if we do - we move the ship back down!

			if ((shipX >= px1 - 3 * radius) && (shipX2 <= px1 + radius) && (shipY >= py1 - 2 * radius)
					&& (shipY2 <= py2 + radius)) {

				int randomIntX = (int) (Math.random() * ((maxX - minX) + 1)) + minX;

				Game.UI().canvas().moveToLocation("space ship", randomIntX, 700);
				content.myCharacter().setLocation(new Point(randomIntX, 700));
				playBoom=true;
				// Game.UI().canvas().moveToLocation("space ship", randomIntX, 700);
				// content.myCharacter().setLocation(new Point(randomIntX, 700));

				shipX = content.myCharacter().getLocation().x;
				shipY = content.myCharacter().getLocation().y;

				Circle p = new Circle(currentPid, px1, py1, radius);
				p.setFillColor(Color.RED);
				p.setIsFilled(true);
				p.setzOrder(3);
				Game.UI().canvas().addShape(p);
				

				if (playBoom==true) {
					//Game.audioPlayer();
					AudioPlayer.playSound("resources/audio/boom.wav");
					playBoom = false;
				}

			}

			// if astroid is at the edge - we recreate it
			if ((px1 <= 20) || (px2 >= 994) || (py1 >= 1000) || (py2 <= 0)) {

				int randomIntX = (int) (Math.random() * ((maxX - minX) + 1)) + minX;
				int randomIntY = (int) (Math.random() * ((maxY - minY) + 1)) + minY;
				int randomIntR = (int) (Math.random() * ((maxR - minR) + 1)) + minR;

				Circle p = new Circle(currentPid, randomIntX, randomIntY, randomIntR);
				p.setFillColor(Color.WHITE);
				p.setIsFilled(true);
				p.setzOrder(3);
				Game.UI().canvas().addShape(p);
			}

			moveOnceIn10miliseconds++; // will make astroid to move in correct speed
			// moving astroids by redrawing them:
		//	astroidSpeed = astroidSpeed-1;
			if (CurrentTimer<25) {
				astroidSpeed =100;
			}
			if (CurrentTimer<20) {
				astroidSpeed =80;
			}
			if (CurrentTimer<15) {
				astroidSpeed =50;
			}
			if (CurrentTimer<10) {
				astroidSpeed =30;
			}
			if (CurrentTimer<10) {
				astroidSpeed =20;
			}
			if (moveOnceIn10miliseconds >= astroidSpeed) {
				System.out.println(astroidSpeed);
				moveOnceIn10miliseconds = 0;
				Game.UI().canvas().moveShape("p0", 1, 1);
				Game.UI().canvas().moveShape("p1", -1, 1);
				Game.UI().canvas().moveShape("p2", 1, 1);
				Game.UI().canvas().moveShape("p3", 1, 1);
				Game.UI().canvas().moveShape("p4", -1, 1);
				Game.UI().canvas().moveShape("p5", 1, 1);
				Game.UI().canvas().moveShape("p6", -1, 1);
			}

			// // another check: do we have a boom? if we do - we move the ship back down!

			// if ((shipX + 5 * radius >= px1) && (shipX2 <= px1) && (shipY >= py1) &&
			// (shipY2 <= py2)) {
			// int randomIntX = (int) (Math.random() * ((maxX - minX) + 1)) + minX;

			// Game.UI().canvas().moveToLocation("space ship", randomIntX, 700);
			// content.myCharacter().setLocation(new Point(randomIntX, 700));
			// Game.UI().canvas().moveToLocation("space ship", randomIntX, 700);
			// content.myCharacter().setLocation(new Point(randomIntX, 700));

			// shipX = content.myCharacter().getLocation().x;
			// shipY = content.myCharacter().getLocation().y;

			// }

		}

		// TODO: Remove comments from next 2 lines
		if (character == null)
			return;

		// TODO
		// Call the canvas to change the shape properties according to
		// its current property values
		// You can get the shape using canvas.getShape(id) with the id of your character
		// Then you can cast it so you can refer to its specific properties.
		// For example, if your shape is a Circle you can use:
		// Circle circle = (Circle) canvas.getShape("MyCircle");
		// circle.move(-10, -10);
		// content.myCharacter().setRotation(content.myCharacter().getRotation() +20);
		// content.changeCharacter();
		// content.myCharacter().moveLocation(0, -10);

	}

}
