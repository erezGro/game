package gui;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import shapes.Image;


public class GameUI {
	JFrame frame;
	GameCanvas canvas;
	GameDashboard dashboard;
	
	public GameUI(String gameName, int width, int height) {
		
		frame = new JFrame(gameName);
		JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		canvas = new GameCanvas();
		dashboard = new GameDashboard();
		split.setTopComponent(canvas);
		split.setBottomComponent(dashboard);

		split.setDividerLocation(height*78/100);
		
		frame.getContentPane().add(split);
	}
	
	public GameCanvas canvas() {
		return this.canvas;
	}
	
	public GameDashboard dashboard() {
		return this.dashboard;
	}
	
	public JFrame frame() {
		return this.frame;
	}
	
	public void setSize(int width, int height) {
		frame.setSize(width,  height);
	}
	
	public void setVisible(boolean visible) {
		this.frame.setVisible(visible);
	}
	
	public void setFocusable(boolean focusable) {
		this.frame.setFocusable(focusable);
	}
	

}


