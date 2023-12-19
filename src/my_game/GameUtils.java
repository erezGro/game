package my_game;

import game.Game;

public class GameUtils {
 
    public static  int  getRandomLocationX() {

        int tStarX = (int) (Math.random() * (((int) (Game.CANVA_WIDTH * 0.94) - Game.minX) + 1)) + Game.minX;
        
        return tStarX;

    }

    
    public static  int  getRandomLocationY() {

        int tStarY = (int) ( (Game.CANVA_HEIGHT * 0.7)*(Math.random()));

        return tStarY;

    }
}
