package my_game;

import java.awt.Color;

import game.AudioPlayer;
import game.Game;
import main.MyContent;
import shapes.Circle;
import shapes.FilledShape;
import shapes.Shape;


public class AstroidHandler {
    int minY = (int) (Game.CANVA_HEIGHT * 0.7);
    int maxX = (int) (Game.CANVA_WIDTH * 0.94);
    int moveOnceInXmiliseconds = 0;
	private MyContent content;
	int InitgameDuration = 30;
	int astroidSpeed = 5000;
    public Object astroidHandler;
//	public int minAstX = 20;
//	public int maxAstX = maxX;

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
        int moveOnceInXmiliseconds = 0;

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

    public void astroidAction(String[] pids  , long elapsedTime , MyCharacter spaceShip) {

		for (int i = 0; i < pids.length; i++) {

			String currentPid = pids[i];
			Shape currentP = Game.UI().canvas().getShape(pids[i]);

			// check: do we have a boom? if we do - we move the ship back down!
			if (checkExplostion((Circle) currentP, spaceShip)) {
                SpaceShipHandler.moveShipToStartLocation(spaceShip,maxX , Game.minAstX+10 ,  minY);

			}

			// if astroid is at the edge - we recreate it
			redrawAstroids((Circle) currentP, currentPid);
			moveOnceInXmiliseconds++; // will make astroid to move in correct speed
			// moving astroids by redrawing them:
	
			if  (((InitgameDuration - (int) elapsedTime) > 3) && (astroidSpeed>1)) {
				astroidSpeed = ((InitgameDuration - (int) elapsedTime) * 2);
			}
			if ((moveOnceInXmiliseconds >= 5*astroidSpeed)) {

				moveOnceInXmiliseconds = 0;
				moveAstroids((Circle) currentP);

			}

		}
    }

        public static void astroidInFreezeMode(String[] pids , long gameDuration , long currentTimer) {
			for (int i = 0; i < pids.length; i++) {
				Shape currentP = Game.UI().canvas().getShape(pids[i]);
				((FilledShape) currentP).setFillColor(Color.BLACK);
				Game.UI().canvas().addShape(currentP);
			}

    }

        public static long astroidInFreezeModeDelay(long gameDuration , long currentTimer) {
            try {
                Thread.sleep(5000);
                Game.gameDuration = Game.gameDuration + 5;
                currentTimer = currentTimer + 5;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return currentTimer;
    }
}
