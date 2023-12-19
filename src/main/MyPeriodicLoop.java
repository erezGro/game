package main;
import java.util.Map;
import game.AudioPlayer;
import game.Game;
import game.PeriodicLoop;
import shapes.Text;
import my_game.SpaceShipHandler;
import my_game.AstroidHandler;
import my_game.GameUtils;
import my_game.MyCharacter;
import my_game.TimerHandler;

public class MyPeriodicLoop extends PeriodicLoop {

	int moveOnceIn10miliseconds = 0;
	int InitgameDuration = 30;
	int randXfreeze = 700;
	int randYfreeze = 300;
	int tStarX;
	int tStarY;
	String[] pids = { "p0", "p1", "p2", "p3", "p4", "p5", "p6" };
	boolean timeStarShow = false;
	boolean freezeStarStatus = false;
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
		Game.endOfGame = false;
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
			AudioPlayer.playSound("resources/audio/GameOver.wav");
			Game.endOfGame = true;
			Game.endGame();
		}

		// Check if user got the time bonus
		if (spaceShipHandler.checkIfSpaceGotBonusStar(content.myCharacter(), tStarX, tStarY, timeStarShow)) {

			timeStarShow = false;
			tStarX = GameUtils.getRandomLocationX(); 
			tStarY = GameUtils.getRandomLocationY(); 
			freezeStarStatus = true;

			AudioPlayer.playSound("resources/audio/timer.wav");
			Game.gameDuration = Game.gameDuration + 10;
			Game.UI().canvas().moveToLocation("Time Star", Game.CANVA_WIDTH*2, Game.CANVA_HEIGHT*2);
		}

		// if spaceship gets the Freeze Bonus:
		if (spaceShipHandler.checkIfSpaceGotBonusStar(content.myCharacter(), randXfreeze, randYfreeze, freezeStarStatus)) {
			freezeStarStatus = false;
			randXfreeze = GameUtils.getRandomLocationX();
			randYfreeze = GameUtils.getRandomLocationY();
			timeStarShow = true;
			AudioPlayer.playSound("resources/audio/timer.wav");
			Game.UI().canvas().moveToLocation("Freeze Star", Game.CANVA_WIDTH*2, Game.CANVA_HEIGHT*2);
			// while freeze - Astroids are BLACK:
			AstroidHandler.astroidInFreezeMode(pids,Game.gameDuration,currentTimer);
			currentTimer = AstroidHandler.astroidInFreezeModeDelay(Game.gameDuration , currentTimer);
			textForTimer = TimerHandler.changeTimerParams (currentTimer,textForTimer);
		}

		// for each astroid we do the following:
		// get location
		// check location
		// if arrived to the edge: set new randomized location
		// if Boom with the spaceship - move spaceship back down to a randomized position
		astroidHandler.astroidAction(pids,elapsedTime,content.myCharacter());

		if (character == null)
			return;
	}
}
