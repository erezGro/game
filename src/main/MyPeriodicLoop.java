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
import my_game.FreezeStar;

public class MyPeriodicLoop extends PeriodicLoop {

	int moveOnceIn10miliseconds = 0;
	int gameDuration = 30;
	int astroidSpeed = 200;
	int minX = 300;
	int maxX = 600;
	int minY = 300;
	int maxY = 700;
	int randX=500;
	int randY=500;
	
	boolean playBoom = false;
	boolean timeStarStatus = false;
	boolean freezeStarStatus = false;
	boolean jitterTimeStarStatus = false;
	boolean jitterFreezeStarStatus = false;
		boolean showClock = true;
	boolean setLocationFlag =true;
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
			// randX = (int) (Math.random() * ((maxX - minX) + 1)) + minX;
			// randY = (int) (Math.random() * ((maxY - minY) + 1)) + minY;
		MyCharacter character = content.myCharacter();

		// TIMER UPDATING ON UI
		long elapsedTime = System.currentTimeMillis() / 1000 - Game.gameStartTime;
		long CurrentTimer = (gameDuration - elapsedTime);
		Text textForTimer = new Text("textForTimer", "Timer : " + CurrentTimer + " [sec]  ", 700, 100);
		int freezeFactor=0;

		int randTimeMax = 25;
		int randTimeMin = 20;

		int randomIntTime = (int) (Math.random() * ((randTimeMax - randTimeMin) + 1)) + randTimeMin;
		int tStarX = 0;
		int tStarY = 0;
		int fStarX= 700;
		int fStarY= 300;
		// if ((elapsedTime <= 1)){
		// 				//  System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+CurrentTimer);
			if(setLocationFlag){
				setLocationFlag=false;
			
					Game.UI().canvas().moveToLocation("space ship", 500, 700);
				content.myCharacter().setLocation(new Point(500, 700));
		 }
			// if (CurrentTimer<=25) {
			// 	tStarX = 300;
			// 	tStarY = 500;
			// 	fStarX = 700;
			// 	fStarY = 500;
			// }
			// else if (CurrentTimer<=20) {
			// 	tStarX = 700;
			// 	tStarY = 500;
			// 	fStarX = 300;
			// 	fStarY = 500;
			// }
			// else if (CurrentTimer<=15) {
			// 	tStarX = 500;
			// 	tStarY = 500;
			// 	fStarX = 400;
			// 	fStarY = 200;
			// }
			// else if (CurrentTimer<=10) {
			// 	tStarX = 300;
			// 	tStarY = 500;
			// 	fStarX = 400;
			// 	fStarY = 400;
			// }
			// else if (CurrentTimer<=5) {
			// 	tStarX = 200;
			// 	tStarY = 500;
			// 	fStarX = 600;
			// 	fStarY = 200;
			// }
		// System.out.println("Time star: X"+timeStarStatus);
		// System.out.println("Freeze star: X"+freezeStarStatus);

		// if ((CurrentTimer < randomIntTime)&&(showClock)) {
			if ((CurrentTimer <= 28)){//&&(!freezeStarStatus)) {
			//// randX = (int) (Math.random() * ((maxX - minX) + 1)) + minX;
			//// randY = (int) (Math.random() * ((maxY - minY) + 1)) + minY;
			timeStarStatus=true;
		// 	tStarX = 500;//randX;//-20*(int)CurrentTimer;
		// 	tStarY = 500;//randY;//-20*(int)CurrentTimer;

		// //	TimeStar timeStar = content.timeStar();
		// 	Game.UI().canvas().moveToLocation("Time Star",tStarX,tStarY);
		} 

		if (timeStarStatus) {
			// randX = (int) (Math.random() * ((maxX - minX) + 1)) + minX;
			// randY = (int) (Math.random() * ((maxY - minY) + 1)) + minY;
			tStarX = 500;//randX;//-20*(int)CurrentTimer;
			tStarY = 500;//randY;//-20*(int)CurrentTimer;

		//	TimeStar timeStar = content.timeStar();
			Game.UI().canvas().moveToLocation("Time Star",tStarX,tStarY);
			// System.out.println("Time star: X"+randX+" , Y"+randY);
		} 

		// int randomIntFreeze = (int) (Math.random() * ((randTimeMax - randTimeMin) + 1)) + randTimeMin;
		// int fStarX= 700;
		// int fStarY= 300;
		randTimeMax = 14;
		randTimeMin = 5;
		randomIntTime = 15;//(int) (Math.random() * ((randTimeMax - randTimeMin) + 1)) + randTimeMin;

		// if ((CurrentTimer < randomIntTime)&&(showClock) && timeStarStatus) {
		if (freezeStarStatus) {
//			timeStarStatus = false;
//			freezeStarStatus = true;

			//		if ((elapsedTime ==10)&&(showClock)){//< randomIntFreeze)&&(showClock)) {
			// randX = (int) (Math.random() * ((maxX - minX) + 1)) + minX;
			// randY = (int) (Math.random() * ((maxY - minY) + 1)) + minY;
			fStarX = 700;//randX;//-20*(int)CurrentTimer;
			fStarY = 300;//randY;//-20*(int)CurrentTimer;

			Game.UI().canvas().moveToLocation("Freeze Star",fStarX,fStarY);
			// System.out.println("Freeze star: X"+fStarX+" , Y"+fStarY);

		} 

		if (CurrentTimer <= 10) {
			if ((int)CurrentTimer%2==0) {
				textForTimer.setColor(Color.red);
			}
			else{
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

//			content.timeStar().setLocation(new Point(100, 100));
//			content.timeStar().moveLocation(-50,50);
		// int tStarX = content.timeStar().getLocation().x;
		// int tStarY = content.timeStar().getLocation().y;
		int tStarX2 = tStarX - 50;
		int tStarY2 = tStarY - 50;

		int fStarX2 = tStarX - 50;
		int fStarY2 = tStarY - 50;

		int shipX = content.myCharacter().getLocation().x;
		int shipY = content.myCharacter().getLocation().y;
		// System.out.println("key character = '" + shipX + "'" + " pressed.");
		// System.out.println("key character = '" + shipY + "'" + " pressed.");

		int shipX2 = shipX - 5;
		int shipY2 = shipY - 5;

			 System.out.println("spaceship location: X"+shipX+" , Y"+shipY);

		// if spaceship gets the Timer Bonus:
		if ((shipX >= tStarX-40) && (shipY >= tStarY-40)&& (shipX <= tStarX+40) && (shipY <= tStarY+40) && (shipX < 950) && (shipX > 60)&&(timeStarStatus)) {

				timeStarStatus = false;
				freezeStarStatus  = true;

				AudioPlayer.playSound("resources/audio/timer.wav");
//							AudioPlayer.playSound("resources/clock.wav");

					playBoom = false;
				//AudioPlayer.playSound("resources/clock.wav");
				showClock = false;
				gameDuration=gameDuration+10;
				
			fStarX = 1100;
			fStarY = 1000;
			//  System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+CurrentTimer);

				Game.UI().canvas().moveToLocation("Time Star",fStarX,fStarY);

		}
			// System.out.println("Time star: X"+shipX+" , Y"+fStarX);

		// if spaceship gets the Freeze Bonus:
		if ((shipX >= fStarX-40) && (shipY >= fStarY-40)&& (shipX <= fStarX+40) && (shipY <= fStarY+40) && (shipX < 950) && (shipX > 60)&&(freezeStarStatus)) {
				freezeStarStatus  = false;
				timeStarStatus = true;

				AudioPlayer.playSound("resources/audio/timer.wav");
//							AudioPlayer.playSound("resources/clock.wav");

					playBoom = false;
				//AudioPlayer.playSound("resources/clock.wav");
				fStarX =1100;
				fStarY=1000;
							//  System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY"+CurrentTimer);

					Game.UI().canvas().moveToLocation("Freeze Star",fStarX,fStarY);

				showClock = false;
				//freezeFactor=1;
				try {
					Thread.sleep(5000);
					gameDuration=gameDuration+5;
					CurrentTimer=CurrentTimer+5;
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				};
				// Game.UI().canvas().moveToLocation("Freeze Star",1100,1000);

		}

		minX = 20;
		maxX = 500;
		minY = 0;
		maxY = 700;
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
				Color currentColor =  ((Circle) currentP).getFillColor();

			if ((currentColor != Color.RED)&&(shipX >= px1 - 3 * radius) && (shipX2 <= px1 + radius) && (shipY >= py1 - 2 * radius)
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
			// freezeFactor=1;
			if ((moveOnceIn10miliseconds >= astroidSpeed)&&(freezeFactor==0)) {

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
