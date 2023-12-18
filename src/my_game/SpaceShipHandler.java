package my_game;

import java.awt.Color;

import game.AudioPlayer;
import game.Game;
import shapes.Circle;

public class SpaceShipHandler {
    int minY = (int) (Game.CANVA_HEIGHT * 0.7);
    int maxX = (int) (Game.CANVA_WIDTH * 0.94);
    


    public boolean checkIfSpaceGotBonusStar( MyCharacter spaceShip,int starX,int starY, boolean isStarShown){
        int shipX = spaceShip.getLocation().x;
		int shipY = spaceShip.getLocation().y;
        
		//Check if user got the time bonus
		if ((shipX >= starX - 40) && (shipY >= starY - 40) && (shipX <= starX + 40) && (shipY <= starY + 40)
        && (shipX < Game.CANVA_WIDTH) && (shipX > 60) && (isStarShown)) return true;
        else return false;

    }

    public static void moveShipToStartLocation( MyCharacter spaceShip,int maxAstX , int minAstX , int minY)  {

        int randomIntX = (int) (Math.random() * ((maxAstX - minAstX) + 1)) + Game.minAstX;
        Game.UI().canvas().moveToLocation("space ship", randomIntX, (int) (Game.CANVA_HEIGHT * 0.7));
        spaceShip.setLocation(new Point(randomIntX, (int) (Game.CANVA_HEIGHT * 0.7)));
    }
}
