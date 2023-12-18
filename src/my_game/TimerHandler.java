package my_game;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import game.AudioPlayer;
import game.Game;
import shapes.Text;

public class TimerHandler {
    int currentScore;  

    public static  Text  setTimerTextParams(int gameDuration) {
		// TIMER UPDATING ON UI
		long elapsedTime = System.currentTimeMillis() / 1000 - Game.gameStartTime;
		long CurrentTimer = (gameDuration - elapsedTime);
		Text textForTimer = new Text("textForTimer", "Timer : " + CurrentTimer + " [sec]  ", 700, 100);
        textForTimer.setFontName("Helvetica");
        textForTimer.setFontSize(30);
        Game.UI().canvas().addShape(textForTimer);
        return textForTimer;
    }

    public static  Map<String, Long>  setTimerValue(int gameDuration) {
		// TIMER UPDATING ON UI
		long elapsedTime = System.currentTimeMillis() / 1000 - Game.gameStartTime;
		long currentTimer = (gameDuration - elapsedTime);
	//	Text textForTimer = new Text("textForTimer", "Timer : " + currentTimer + " [sec]  ", 700, 100);
    //    textForTimer.setFontName("Helvetica");
     //   textForTimer.setFontSize(30);
     //   Game.UI().canvas().addShape(textForTimer);
        Map<String, Long> result = new HashMap<String, Long>();
        result.put("elapsedTime", elapsedTime);
        result.put("currentTimer", currentTimer);
        return result;
    }
        
    public static Text changeTimerParams (long currentTimer,Text textForTimer) {
		// TIMER UPDATING ON UI
			if (currentTimer <= 10) {
                if ((int) currentTimer % 2 == 0) {
                    textForTimer.setColor(Color.red);
 /*                    if ((int) currentTimer == 4) {
                        AudioPlayer.playSound("resources/audio/ping.wav");
                    } */
                } else {
                    textForTimer.setColor(Color.yellow);
                }
            } else {
                textForTimer.setColor(Color.white);
            }
                    return textForTimer;
    }
}

