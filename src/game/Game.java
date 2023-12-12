package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import DB.ExcelDB;
import DB.ExcelTable;
import buttons.EndButton;
import gui.GameUI;
import gui.ResultsPanel;
import shapes.Text;

public abstract class Game {
	protected static GameContent gameContent;
	protected static GameUI gameUI;
	private static MouseHandler mouseHandler;
	private static KeyboardListener keyboardListener;
	private static PeriodicScheduler scheduler;
	public static AudioPlayer audioPlayer;
	private static ExcelDB excelDB;
	public static long gameStartTime = System.currentTimeMillis() / 1000;
	public static String userName;
	static ExcelTable results;
	public static String gameMusicPath = "resources/audio/GameMusic.wav";
	// Get the screen size
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public static  int CANVA_WIDTH ;
	public static int CANVA_HEIGHT;

	public Game(String userName) {
		scheduler = new PeriodicScheduler();
		excelDB = ExcelDB.getInstance();
		audioPlayer = new AudioPlayer();
		Game.userName = userName;
		CANVA_WIDTH = (int) (screenSize.width * 0.9);
		CANVA_HEIGHT = (int) (screenSize.height * 0.9);

	}

	public void setGameContent(GameContent content) {
		gameContent = content;
	}

	public void setPeriodicLoop(PeriodicLoop periodicLoop) {
		scheduler.setPeriodicLoop(periodicLoop);
	}

	public void setMouseHandler(MouseHandler myMouseHandler) {
		mouseHandler = myMouseHandler;
	}

	public void setKeyboardListener(KeyboardListener myKeyboardListener) {
		keyboardListener = myKeyboardListener;
	}

	public void initGame() {
		initContent();
		initUI();
		if (keyboardListener != null)
			gameUI.frame().addKeyListener(keyboardListener.keyListener);
		scheduler.start();
	}

	public void initUI() {

		// Get the screen size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		// Calculate the width and height based on a percentage (e.g., 90%)
		int screenWidth = (int) (screenSize.width * 0.9);
		int screenHeight = (int) (screenSize.height * 0.9);

		gameUI = new GameUI("My Game", screenWidth, screenHeight);
		initCanvas();
		initDashboard();
		gameUI.setFocusable(true);
		gameUI.setVisible(true);
	}

	public static void endGame() {
		Text scoreText = (Text) Game.UI().canvas().getShape("ScoreText");
		String score = scoreText.getText().replace("Score: ", "");
		scheduler.end();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		// //Update Excel with User Name and Result:
		String fileName = "spaceshipResults";
		results = new ExcelTable(fileName);
		String[] line = { game.Game.userName, score.trim() };

		String[] checkResult1 = results.getFields(game.Game.userName);
		if (checkResult1 != null) { // we have history score for this user, we will update only if user improved:
			if (Integer.parseInt(checkResult1[1]) < Integer.parseInt((score.trim()))) {
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
		gameUI.frame().dispose();

	}

	protected abstract void initCanvas();

	protected void initContent() {
		gameContent.initContent();
	};

	protected void initDashboard() {
		Color orange = new Color(255, 115, 31);
		// Add end button to terminate game
		EndButton endButton = new EndButton("btnEND", "END", 100, 40, 850, 40);
		endButton.setButtonColor(orange);
		gameUI.dashboard().addButton(endButton);
		// gameUI.dashboard().addButton(new EndButton("btnEND", "END", 100, 40, 850,
		// 40));
	}

	// You can refer to the game UI from anywhere by Game.UI()
	public static GameUI UI() {
		return gameUI;
	}

	public static MouseHandler MouseHandler() {
		return mouseHandler;
	}

	public static KeyboardListener keybListener() {
		return keyboardListener;
	}

	public static GameContent Content() {
		return gameContent;
	}

	public static ExcelDB excelDB() {
		return excelDB;
	}

	public static AudioPlayer audioPlayer() {
		return audioPlayer;
	}

	public void setTimer() {
		gameStartTime = System.currentTimeMillis() / 1000;
	}

}
