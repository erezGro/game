package gui;

import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import buttons.GameButton;
import shapes.Shape.STATUS;

public class GameDashboard extends JPanel  {
	
	private static final long serialVersionUID = 1L;
	
	private final Map<String, GameButton> buttons;

	String borderColor;
	int borderWidth;
	
	int width = 100;	
	int height = 100;
	
	int positionX;
	int positionY;
		
	public GameDashboard() {
		super();
		this.buttons = new HashMap<>();
		this.setLayout(null);
	}
	/*
	 * Add a basic button according to a set of parameters
	 */
	public void addButton(String id, String name, int width, int height,
			int posX, int posY) {
		GameButton button = new GameButton(id, name, width, height, posX, posY);
		buttons.put(id, button);
		this.add(button.getJButton());
		this.updateUI();
	}

	/*
	 * Add a specific button that is derived from GameButton and is created before.
	 */
	public void addButton(GameButton gameButton) {
		buttons.put(gameButton.getId(), gameButton);
		this.add(gameButton.getJButton());
		this.updateUI();
	}
	
	public GameButton getButton(String id) {
		return buttons.get(id);
	}
	
	public void delButton(String id) {
		GameButton button = buttons.get(id);
		if (button != null) {
			this.remove(button.getJButton());
			buttons.remove(id);
		}
		this.updateUI();
	}

	public void hideAll() {
		for (GameButton button : buttons.values()) {
			button.setStatus(STATUS.HIDE);
			this.remove(button.getJButton());
		}
		this.updateUI();
	}

	public void showAll() {
		for (GameButton button : buttons.values()) {
			button.setStatus(STATUS.SHOW);
			this.add(button.getJButton());
		}
		this.updateUI();
	}

	public void deleteAll() {
		for (String id : buttons.keySet()) {
			delButton(id);
		}
		this.updateUI();
	}

	public void flipStatus(String id) {
		GameButton button = buttons.get(id);
		if (button != null) {
			if (button.getStatus().equals(STATUS.HIDE)) {
				button.setStatus(STATUS.SHOW);
				this.add(button.getJButton());
			} else if (button.getStatus().equals(STATUS.SHOW)) {
				button.setStatus(STATUS.HIDE);
				this.remove(button.getJButton());
			}
		}
		this.updateUI();
	}

	public void show(String id) {
		GameButton button = buttons.get(id);
		if (button != null) {
			button.setStatus(STATUS.SHOW);
				this.add(button.getJButton());
		}
		this.updateUI();
	}
	
	public void hide(String id) {
		GameButton button = buttons.get(id);
		if (button != null) {
			button.setStatus(STATUS.HIDE);
				this.remove(button.getJButton());
		}
		this.updateUI();
	}

}
