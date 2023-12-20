package my_game;

import java.awt.Color;

import DB.ExcelTable;
import game.Game;
import gui.ResultsPanel;
import shapes.Text;

public class ScoreHandler {
static ExcelTable results;

int currentScore;  
// private static PeriodicScheduler scheduler;    
    
    public static  int  addPointsToScore(int score) {

					score++;
					Text gameScoreText = new Text("ScoreText", "Score: " + score, 100, 100);
					gameScoreText.setColor(Color.yellow);
					gameScoreText.setFontName("Helvetica");
					gameScoreText.setFontSize(30);
					Game.UI().canvas().addShape(gameScoreText);
                    return score;
    }
 
	public static  void  saveResultToDb() {

		Text scoreText = (Text) Game.UI().canvas().getShape("ScoreText");
		String finalScore = scoreText.getText().replace("Score: ", "");
		Game.scheduler.end();
		// scheduler.end();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		// //Update Excel with User Name and Result:
		String fileName = "spaceshipResults";
		results = new ExcelTable(fileName);
		String[] line = { game.Game.userName, finalScore.trim() };

		String[] checkResult1 = results.getFields(game.Game.userName);
		if (checkResult1 != null) { // we have history score for this user, we will update only if user improved:
			if (Integer.parseInt(checkResult1[1]) < Integer.parseInt((finalScore.trim()))) {
				try {
					results.updateRow(line);
					System.out.println("user " + game.Game.userName
							+ " already found in Score Table, Updating with new improved Score");
				} catch (Exception e) {
					System.out.println("failed to add new score for user " + game.Game.userName);

					e.printStackTrace();
				}
			}

		} else {// the user is NOT found in Table, creating new Table-Line:
			try {
				results.insertRow(line);
				System.out.println("Added to table: new score result for user " + game.Game.userName);
			} catch (Exception e) {
				System.out.println("failed to add new line with score for user " + game.Game.userName);
				e.printStackTrace();
			}
		}

		results.WriteToFile();

		// get file content and show the results panel
		results.sortTable(1, true);
		String[][] final_results = results.getTableAsMatrix();
		ResultsPanel.showResultPanel(final_results); 

	}



}
