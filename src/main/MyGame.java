package main;

import java.awt.Color;
import game.Game;
import game.GameContent;
import gui.GameCanvas;
import gui.GameDashboard;
import gui.OpeningPanel;
import buttons.ChangeButton;
import buttons.MusicButton;
import my_game.MyCharacter;
import my_game.TimeStar;
import my_game.FreezeStar;

import my_game.Point;
import shapes.Circle;
import shapes.Text;
import shapes.Image;

public class MyGame extends Game {


	public MyGame(String userName) {
		super(userName);
	}

	private MyContent content;
	private Circle[] pointss;

	

	// public long gameStartTime;

	@Override
	protected void initCanvas() {

		GameCanvas canvas = gameUI.canvas();
		canvas.setBackground(Color.WHITE);
		canvas.setBackgroundImage("resources/galaxy.jpg");

		MyCharacter myCharacter = content.myCharacter();
		myCharacter = new MyCharacter();
		Image image = new Image(myCharacter.getImageID(), myCharacter.getImageName(), 55, 52, 500, 700);
		image.setDraggable(true);
		canvas.addShape(image);
		image.setzOrder(3);

		Text t1 = new Text("t1", "Space Race Game ", 100, 50);
		t1.setColor(Color.white);
		t1.setFontName("Helvetica");
		t1.setFontSize(30);
		canvas.addShape(t1);

		Text t2 = new Text("ScoreText", "Score: 0 ", 100, 100);
		t2.setColor(Color.white);
		t2.setFontName("Helvetica");
		t2.setFontSize(30);
		canvas.addShape(t2);

		Text t3 = new Text("textForTimer", "Timer : 30 [sec]  ", 700, 100);
		t3.setColor(Color.white);
		t3.setFontName("Helvetica");
		t3.setFontSize(30);
		canvas.addShape(t3);

		
		TimeStar timeStar = content.timeStar();
		timeStar = new TimeStar();
		//Time Star init is out of the canvas bonds
		Image imageTs = new Image(timeStar.getImageID(), timeStar.getImageName(), 55, 52, 1100, 1000);
		canvas.addShape(imageTs);
		image.setzOrder(3);
		
		FreezeStar freezeStar = content.freezeStar();
		freezeStar = new FreezeStar();
		//Freeze Star init is out of the canvas bonds
		Image imageFs = new Image(freezeStar.getImageID(), freezeStar.getImageName(), 55, 52, 1100, 1000);
		canvas.addShape(imageFs);
		image.setzOrder(3);
		
		Point[] points = {

				new Point(400, 0),
				new Point(300, 50),
				new Point(900, 150),
				new Point(800, 200),
				new Point(700, 175),
				new Point(670, 125),
				new Point(330, 75)
		};
		///////////////////// this is where we create the
		///////////////////// astroids///////////////////////
		pointss = new Circle[points.length];
		for (int i = 0; i < points.length; i++) {
			 pointss[i] = new Circle("p" + i, points[i].x, points[i].y, 20);
			 pointss[i].setFillColor(Color.WHITE);
			 pointss[i].setIsFilled(true);
			 pointss[i].setzOrder(3);
			Game.UI().canvas().addShape(pointss[i]);
		}

	}

	@Override
	protected void initDashboard() {
		super.initDashboard();
		GameDashboard dashboard = gameUI.dashboard();

		dashboard.setBackground(Color.BLACK);
		ChangeButton changeButton = new ChangeButton("changeButton", "Change Space", 500, 40);
		MusicButton playMuteButton = new MusicButton("musicButton", "Mute", 700, 40);
		playMuteButton.setButtonColor(new Color(255,204,204));
		changeButton.setButtonColor(new Color(50,204,204));
		dashboard.addButton(changeButton);
		dashboard.addButton(playMuteButton);

	}

	@Override
	public void setGameContent(GameContent content) {
		// Call the Game superclass to set its content
		super.setGameContent(content);
		this.content = (MyContent) content;
	}

	public MyContent getContent() {
		return this.content;
	}

	public static void main(String[] args) throws Exception {

		OpeningPanel.showOpeningPanel();

	}

}
