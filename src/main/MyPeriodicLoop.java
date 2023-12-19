package main;

//import java.awt.Color;
import java.util.Map;

import game.AudioPlayer;
import game.Game;
import game.PeriodicLoop;
//import shapes.Circle;
//import shapes.FilledShape;
//import shapes.Shape;
import shapes.Text;
////import my_game.GameUtils;
import my_game.SpaceShipHandler;

import my_game.AstroidHandler;
import my_game.GameUtils;
import my_game.MyCharacter;
//import my_game.Point;
import my_game.TimerHandler;


public class MyPeriodicLoop extends PeriodicLoop {

	int moveOnceIn10miliseconds = 0;
	//int gameDuration = 30;
	int InitgameDuration = 30;

	//int astroidSpeed = 5000;
	//int minX = 200;
	//int maxX = (int) (Game.CANVA_WIDTH * 0.94);
	//int minY = (int) (Game.CANVA_HEIGHT * 0.7);
	//int randX = 500;
	//int randY = 500;
	int randXfreeze = 700;
	int randYfreeze = 300;
	int tStarX;
	int tStarY;
	//int minAstX = 20;
	//int maxAstX = maxX;
	String[] pids = { "p0", "p1", "p2", "p3", "p4", "p5", "p6" };

	//boolean playBoom = false;
	boolean timeStarShow = false;
	boolean freezeStarStatus = false;
	//boolean jitterTimeStarStatus = false;
	//boolean jitterFreezeStarStatus = false;
	//boolean showClock = true;
	boolean setLocationFlag = true;
	private MyContent content;
	private boolean timerFirstTimeShow = true;

	GameUtils gameUtils = new GameUtils();
	SpaceShipHandler spaceShipHandler = new SpaceShipHandler();

	AstroidHandler astroidHandler = new AstroidHandler();

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

		Map<String, Long> timers = TimerHandler.setTimerValue(Game.gameDuration);
		long elapsedTime = timers.get("elapsedTime");
		long currentTimer = timers.get("currentTimer");
		Text textForTimer = TimerHandler.setTimerTextParams(Game.gameDuration);
		
		if (setLocationFlag) {
			setLocationFlag = false;
			SpaceShipHandler.moveShipToStartLocation(content.myCharacter(),(int) (Game.CANVA_WIDTH * 0.94) , Game.minAstX ,  (int) (Game.CANVA_HEIGHT * 0.7));
		}

		if (timerFirstTimeShow && (currentTimer <= 25)) {

			tStarX = GameUtils.getRandomLocationX();
			tStarY = GameUtils.getRandomLocationY();

/* 			tStarX = (int) (Math.random() * ((maxX - minX) + 1)) + minX;
			tStarY = (int) (minY - 0.3 * (int) (Math.random() * ((minY))));

			randXfreeze = (int) (Math.random() * ((maxX - minX) + 1)) + minX;
			randYfreeze = (int) (minY - 0.3 * (int) (Math.random() * ((minY)))) */;
			timeStarShow = true;
			timerFirstTimeShow = false;
		}

		if (timeStarShow) {
			Game.UI().canvas().moveToLocation("Time Star", tStarX, tStarY);
		}

		if (freezeStarStatus) {
			Game.UI().canvas().moveToLocation("Freeze Star", randXfreeze, randYfreeze);
		}
		textForTimer = TimerHandler.changeTimerParams (currentTimer,textForTimer);

		if (elapsedTime == Game.gameDuration) {
			AudioPlayer.playSound("resources/audio/endgame.wav");
			Game.endGame();
		}

		// Check if user got the time bonus
		if (spaceShipHandler.checkIfSpaceGotBonusStar(content.myCharacter(), tStarX, tStarY, timeStarShow)) {

			timeStarShow = false;
			//randX = (int) (Math.random() * ((maxX - minX) + 1)) + minX;
			//randY = (int) (minY - 0.3 * (int) (Math.random() * ((minY))));

			tStarX = GameUtils.getRandomLocationX(); 
			tStarY = GameUtils.getRandomLocationY(); 
			freezeStarStatus = true;

			AudioPlayer.playSound("resources/audio/timer.wav");

			//playBoom = false;
			//showClock = false;
			Game.gameDuration = Game.gameDuration + 10;

			Game.UI().canvas().moveToLocation("Time Star", Game.CANVA_WIDTH*2, Game.CANVA_HEIGHT*2);

		}

		// if spaceship gets the Freeze Bonus:
		if (spaceShipHandler.checkIfSpaceGotBonusStar(content.myCharacter(), randXfreeze, randYfreeze, freezeStarStatus)) {
			freezeStarStatus = false;
			randXfreeze = GameUtils.getRandomLocationX();
			randYfreeze = GameUtils.getRandomLocationY();
			//randXfreeze = (int) (Math.random() * ((maxX - minX) + 1)) + minX;
			//randYfreeze = (int) (minY - 0.3 * (int) (Math.random() * ((minY))));
			timeStarShow = true;
			AudioPlayer.playSound("resources/audio/timer.wav");
			Game.UI().canvas().moveToLocation("Freeze Star", Game.CANVA_WIDTH*2, Game.CANVA_HEIGHT*2);
			//showClock = false;

			// while freeze - Astroids are BLACK:
			AstroidHandler.astroidInFreezeMode(pids,Game.gameDuration,currentTimer);
			currentTimer = AstroidHandler.astroidInFreezeModeDelay(Game.gameDuration , currentTimer);
			/* try {
				Thread.sleep(5000);
				Game.gameDuration = Game.gameDuration + 5;
				currentTimer = currentTimer + 5;
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			} */
			textForTimer = TimerHandler.changeTimerParams (currentTimer,textForTimer);

		}




		// for each astroid we do the following:
		// get location
		// check location
		// if arrived to the edge: set new randomized location
		// if Boom with the spaceship - move spaceship back down to a randomized X
		// position
		//AstroidHandler.astroidAction(pids , maxAstX , minAstX ,  minY , elapsedTime , freezeFactor);
		astroidHandler.astroidAction(pids,elapsedTime,content.myCharacter());
/* 		for (int i = 0; i < pids.length; i++) {
			//playBoom = false;

			String currentPid = pids[i];
			Shape currentP = Game.UI().canvas().getShape(pids[i]);

			// check: do we have a boom? if we do - we move the ship back down!
			if (astroidHandler.checkExplostion((Circle) currentP, content.myCharacter())) {
				GameUtils.moveShipToStartLocation(content.myCharacter(),maxAstX , minAstX ,  minY);
			}

			// if astroid is at the edge - we recreate it
			astroidHandler.redrawAstroids((Circle) currentP, currentPid);
			moveOnceIn10miliseconds++; // will make astroid to move in correct speed
			// moving astroids by redrawing them:
	
			if  (((InitgameDuration - (int) elapsedTime) > 6) && (astroidSpeed>50)) {
				astroidSpeed = ((InitgameDuration - (int) elapsedTime) * 2);
			}
			// freezeFactor=1;
			if ((moveOnceIn10miliseconds >= astroidSpeed)) {

				moveOnceIn10miliseconds = 0;
				astroidHandler.moveAstroids((Circle) currentP);

			} 

		}
 */
		if (character == null)
			return;
	}
}
