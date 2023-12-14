package my_game;

import java.awt.Color;
import game.Game;
import shapes.Text;

public class ScoreHandler {
int currentScore;  
    
    
    public static  int  addPointsToScore(int score) {

					score++;
					Text gameScoreText = new Text("ScoreText", "Score: " + score, 100, 100);
					gameScoreText.setColor(Color.yellow);
					gameScoreText.setFontName("Helvetica");
					gameScoreText.setFontSize(30);
					Game.UI().canvas().addShape(gameScoreText);
                    return score;
    }
   
}
