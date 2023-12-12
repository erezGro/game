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

	int minY = (int) (Game.CANVA_HEIGHT * 0.7);
	int maxX = (int) (Game.CANVA_WIDTH * 0.94);

	@Override
	public void directionalKeyPressed(Direction direction) {
		switch (direction) {
			case UP:
				myContent.myCharacter().setDirectionPolicy(MyCharacter.Direction.UP);
				myContent.myCharacter().moveLocation(0, -10);
				if ((myContent.myCharacter().getLocation().y) < 0) {
					Game.UI().canvas().moveToLocation("space ship", 500, minY);
					myContent.myCharacter().setLocation(new Point(500, minY));

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
				if ((myContent.myCharacter().getLocation().y) < minY) {

					myContent.myCharacter().moveLocation(0, 10);
				}
				break;
			case LEFT:
				myContent.myCharacter().setDirectionPolicy(MyCharacter.Direction.LEFT);
				if ((myContent.myCharacter().getLocation().x) > 5) {

					myContent.myCharacter().moveLocation(-10, 0);
				}
				break;
			case RIGHT:
				myContent.myCharacter().setDirectionPolicy(MyCharacter.Direction.RIGHT);
				if ((myContent.myCharacter().getLocation().x) < maxX) {

					myContent.myCharacter().moveLocation(10, 0);
				}
				break;

			case WUP:
				myContent.myCharacter().setDirectionPolicy(MyCharacter.Direction.UP);
				myContent.myCharacter().moveLocation(0, -10);
				if ((myContent.myCharacter().getLocation().y) < 0) {
					Game.UI().canvas().moveToLocation("space ship", 500, minY);
					myContent.myCharacter().setLocation(new Point(500, minY));

					score++;
					Text gameScoreText = new Text("ScoreText", "Score: " + score, 100, 100);
					gameScoreText.setColor(Color.yellow);
					gameScoreText.setFontName("Helvetica");
					gameScoreText.setFontSize(30);
					Game.UI().canvas().addShape(gameScoreText);

				}
				break;
			case XDOWN:
				myContent.myCharacter().setDirectionPolicy(MyCharacter.Direction.DOWN);
				if ((myContent.myCharacter().getLocation().y) < minY) {

					myContent.myCharacter().moveLocation(0, 10);
				}
				break;
			case ALEFT:
				myContent.myCharacter().setDirectionPolicy(MyCharacter.Direction.LEFT);
				if ((myContent.myCharacter().getLocation().x) > 5) {

					myContent.myCharacter().moveLocation(-10, 0);
				}
				break;
			case DRIGHT:
				myContent.myCharacter().setDirectionPolicy(MyCharacter.Direction.RIGHT);
				if ((myContent.myCharacter().getLocation().x) < maxX) {

					myContent.myCharacter().moveLocation(10, 0);
				}
				break;

			case UR:
				myContent.myCharacter().setDirectionPolicy(MyCharacter.Direction.UR);
				if ((myContent.myCharacter().getLocation().x) < maxX) {
					myContent.myCharacter().moveLocation(10, -20);
					if ((myContent.myCharacter().getLocation().y) < 0) {
						Game.UI().canvas().moveToLocation("space ship", 500, minY);
						myContent.myCharacter().setLocation(new Point(500, minY));

						score++;
						Text gameScoreText = new Text("ScoreText", "Score: " + score, 100, 100);
						gameScoreText.setColor(Color.yellow);
						gameScoreText.setFontName("Helvetica");
						gameScoreText.setFontSize(30);
						Game.UI().canvas().addShape(gameScoreText);

					}

				}

				break;

			case UL:
				myContent.myCharacter().setDirectionPolicy(MyCharacter.Direction.UL);
				if ((myContent.myCharacter().getLocation().x) > 20) {

					myContent.myCharacter().moveLocation(-10, -20);

					if ((myContent.myCharacter().getLocation().y) < 0) {
						Game.UI().canvas().moveToLocation("space ship", 500, minY);
						myContent.myCharacter().setLocation(new Point(500, minY));

						score++;
						Text gameScoreText = new Text("ScoreText", "Score: " + score, 100, 100);
						gameScoreText.setColor(Color.yellow);
						gameScoreText.setFontName("Helvetica");
						gameScoreText.setFontSize(30);
						Game.UI().canvas().addShape(gameScoreText);

					}

				}
				break;

			case DR:
				myContent.myCharacter().setDirectionPolicy(MyCharacter.Direction.DR);
				if (((myContent.myCharacter().getLocation().x) < maxX)
						&& ((myContent.myCharacter().getLocation().y) < minY)) {

					myContent.myCharacter().moveLocation(10, 20);
				}
				break;

			case DL:
				myContent.myCharacter().setDirectionPolicy(MyCharacter.Direction.DL);
				if (((myContent.myCharacter().getLocation().x) > 20)
						&& ((myContent.myCharacter().getLocation().y) < minY)) {

					myContent.myCharacter().moveLocation(-10, 20);
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
