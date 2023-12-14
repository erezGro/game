package my_game;

import java.awt.Color;

import game.AudioPlayer;
import game.Game;
import shapes.Circle;

public class GameUtils {
    int minY = (int) (Game.CANVA_HEIGHT * 0.7);
    int maxX = (int) (Game.CANVA_WIDTH * 0.94);
    

    public void redrawAstroids(Circle astroid,String currentPid) {

        int px1 = astroid.x1CircPosition();
        int py1 = astroid.y1CircPosition();
        int py2 = astroid.y2CircPosition();
        int px2 = astroid.x2CircPosition();

			// if astroid is at the edge - we recreate it
			if ((px1 <= 20) || (px2 >= Game.CANVA_WIDTH) || (py1 >= Game.CANVA_HEIGHT) || (py2 <= 0)) {

				int randomIntX = (int) (Math.random() * (((int) (Game.CANVA_WIDTH * 0.94) - 20) + 1)) + 20;
                int randomIntY = (int) (minY - (int) (Math.random() * ((minY))));
                int randomIntR = (int) (0.5*Math.random() * (40)) + 20;

				Circle p = new Circle(currentPid, randomIntX, randomIntY, randomIntR);
				p.setFillColor(Color.WHITE);
				p.setIsFilled(true);
				p.setzOrder(3);
				Game.UI().canvas().addShape(p);
                
			}
            
    }
    public void moveAstroids(Circle astroid) {

        Game.UI().canvas().moveShape("p0", 1, 1);
        Game.UI().canvas().moveShape("p1", -1, 1);
        Game.UI().canvas().moveShape("p2", 1, 1);
        Game.UI().canvas().moveShape("p3", 1, 1);
        Game.UI().canvas().moveShape("p4", -1, 1);
        Game.UI().canvas().moveShape("p5", 1, 1);
        Game.UI().canvas().moveShape("p6", -1, 1);

    }
    public boolean checkExplostion(Circle astroid, MyCharacter spaceShip) {
        int shipX = spaceShip.getLocation().x;
        int shipY = spaceShip.getLocation().y;

        int shipX2 = shipX - 5;
        int shipY2 = shipY - 5;

        int px1 = astroid.x1CircPosition();
        int py1 = astroid.y1CircPosition();
        int py2 = astroid.y2CircPosition();
        int radius = astroid.getRadius();

        Color currentColor = (Color) astroid.getFillColor();

                if ((currentColor != Color.RED) && (shipX >= px1 - 3 * radius) && (shipX2 <= px1 + radius)
                && (shipY >= py1 - 2 * radius)
                && (shipY2 <= py2 + radius)) {

				astroid.setFillColor(Color.RED);
				astroid.setIsFilled(true);
				astroid.setzOrder(3);
				Game.UI().canvas().addShape(astroid);
            	AudioPlayer.playSound("resources/audio/boom.wav");

            }

        if ((currentColor != Color.RED) && (shipX >= px1 - 3 * radius) && (shipX2 <= px1 + radius)
                && (shipY >= py1 - 2 * radius)
                && (shipY2 <= py2 + radius)) 

            return true;
        else
            return false;
    }

    public boolean checkIfSpaceGotBonusStar( MyCharacter spaceShip,int starX,int starY, boolean isStarShown){
        int shipX = spaceShip.getLocation().x;
		int shipY = spaceShip.getLocation().y;
        
		//Check if user got the time bonus
		if ((shipX >= starX - 40) && (shipY >= starY - 40) && (shipX <= starX + 40) && (shipY <= starY + 40)
        && (shipX < maxX) && (shipX > 60) && (isStarShown)) return true;
        else return false;

    }

    public static void moveShipToStartLocation( MyCharacter spaceShip,int maxAstX , int minAstX , int minY)  {

        int randomIntX = (int) (Math.random() * ((maxAstX - minAstX) + 1)) + minAstX;
        Game.UI().canvas().moveToLocation("space ship", randomIntX, minY);
        spaceShip.setLocation(new Point(randomIntX, minY));
    }
}
