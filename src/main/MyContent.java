package main;

import game.GameContent;
import my_game.MyCharacter;
import my_game.Point;
import my_game.TimeStar;

public class MyContent extends GameContent {

	private MyCharacter myCharacter;

	@Override
	public void initContent() {
		myCharacter = new MyCharacter();
		myCharacter.setLocation(new Point(500, 700));
		MyCharacter timeStar = new MyCharacter();
		timeStar.setLocation(new Point(1000, 0));

	}

	public void addCharacter() {
	}

	public MyCharacter myCharacter() {
		return myCharacter;
	}

	public TimeStar timeStar() {
		return null;
	}


}
