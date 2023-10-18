package main;

import java.awt.Color;
import my_game.MyCharacter;
import my_game.Point;
import shapes.Text;
import java.awt.event.KeyEvent;
import game.Game;
import game.KeyboardListener;

public class MyKeyboardListener extends KeyboardListener {
	private int score = 0;
	private MyContent myContent;

	public MyKeyboardListener() {
		super();
		myContent = (MyContent) this.content;
	}

	int shipDeltaMove = 10;

	@Override
	public void directionalKeyPressed(Direction direction) {
		switch (direction) {
			case UP:
				myContent.myCharacter().setDirectionPolicy(MyCharacter.Direction.UP);
				myContent.myCharacter().moveLocation(0, -shipDeltaMove);
				if ((myContent.myCharacter().getLocation().y) < 00) {
					Game.UI().canvas().moveToLocation("space ship", 500, 600);
					myContent.myCharacter().setLocation(new Point(500, 600));

					score++;
					Text gameScoreText = new Text("ScoreText", "Score: " + score, 100, 100);
					gameScoreText.setColor(Color.yellow);
					gameScoreText.setFontName("Helvetica");
					gameScoreText.setFontSize(30);
					Game.UI().canvas().addShape(gameScoreText);

				}
				break;
			case DOWN:
				myContent.myCharacter().setDirectionPolicy(MyCharacter.Direction.DOWN);
				if ((myContent.myCharacter().getLocation().y) < 680) {

					myContent.myCharacter().moveLocation(0, shipDeltaMove);
				}
				break;
			case LEFT:
				myContent.myCharacter().setDirectionPolicy(MyCharacter.Direction.LEFT);
				if ((myContent.myCharacter().getLocation().x) > 20) {

					myContent.myCharacter().moveLocation(-shipDeltaMove, 0);
				}
				break;
			case RIGHT:
				myContent.myCharacter().setDirectionPolicy(MyCharacter.Direction.RIGHT);
				if ((myContent.myCharacter().getLocation().x) < 954) {

					myContent.myCharacter().moveLocation(shipDeltaMove, 0);
				}
				break;
		}
	}

	@Override
	public void characterTyped(char c) {
		System.out.println("key character = '" + c + "'" + " pressed.");
	}

	@Override
	public void enterKeyPressed() {
		System.out.println("enter key pressed.");
	}

	@Override
	public void backSpaceKeyPressed() {
		System.out.println("backSpace key pressed.");
	}

	@Override
	public void spaceKeyPressed() {
		System.out.println("space key pressed.");
	}

	public void otherKeyPressed(KeyEvent e) {
		System.out.println("other key pressed. type:" + e);
	}
}