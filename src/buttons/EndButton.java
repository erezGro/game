package buttons;

import game.Game;
import my_game.ScoreHandler;


public class EndButton extends GameButton {
	
	public EndButton(String id, String name, int width, int height, int posX, int posY) {
		super(id, name, width, height, posX, posY);
	}

	@Override
	public void buttonAction() {
		super.buttonAction();
		ScoreHandler.saveResultToDb();

		Game.endGame();
		


	}

}
