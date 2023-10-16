package buttons;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;


import game.Game;
import game.GameContent;
import shapes.Shape.STATUS;

public class GameButton {
	
	protected JButton button;
	protected final String id;
	protected int width;
	protected int height;
	protected int posX;
	protected int posY;
	protected STATUS status;
	protected GameContent content;
	
	public GameButton(String id, String name, int width, int height, int posX, int posY) {
		this.id = id;
		this.button = new JButton();
		setText(name);
		this.width = width;
		this.height = height;
		setSize(width, height);
		this.posX = posX;
		this.posY = posY;
		setLocation(posX, posY);
		this.button.setFont(new Font("Ariel", Font.BOLD, 16));
		this.status = STATUS.SHOW;
		this.content = Game.Content();
		this.button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonAction();
			}
		});

	}
	public void setSize(int width, int height) {
		this.button.setSize(width, height);
	}
	public void setLocation(int posX, int posY) {
		this.button.setLocation(posX, posY);
	}
	public void setText(String name) {
		this.button.setText(name);
	}
	public String getText() {
		return this.button.getText();
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	public String getId() {
		return id;
	}
	public STATUS getStatus() {
		return status;
	}
	public void setStatus(STATUS status) {
		this.status =status;
	}	
	
	public JButton getJButton() {
		return this.button;
	}
	
	public void setButtonColor(Color color){
		this.button.setBackground( color);	
	}
	//This function is a placeholder and should be overriden in derived specific buttons
	public void buttonAction() {
		System.out.println(id + " clicked");
		Game.UI().frame().requestFocus();
	}
		
}

