package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import game.Game;
import shapes.Image;
import shapes.Shape;
import shapes.Shape.STATUS;
import shapes.TextLabel;

public class GameCanvas extends JPanel  {
	
	private static final long serialVersionUID = 1L;
	
	private final Map<String, Shape> shapes;

	private Shape[] sortedShapes;
	
	private boolean resort = true;

	int borderWidth;
	
	int positionX;
	int positionY;

	private ImageIcon backgroundImage = null;
		
	public GameCanvas() {
		super();
		this.setBackground(Color.WHITE);
		this.shapes = new HashMap<>();
		// Not relevant -> will be assigned by the default values in the 
		this.setLayout(null);
		addListeners();
	}

	public void setBackgroundImage(String imageFile) {
		backgroundImage = new ImageIcon(imageFile);
	}

	public void addShape(final Shape shape) {
		resort = true;
		shapes.put(shape.getId(), shape);
		this.repaint();
	}
	
	public Shape getShape(final String id) {
		return shapes.get(id);
	}

	public void changeImage(final String id, final String src, final int width, final int height)
	{
		final Shape shape = shapes.get(id);
		if(shape == null){
			return;
		}
		if (!(shape instanceof Image)) {
			return;
		}
		final Image image = (Image) shape;
		this.remove(image.getImg());
		image.setImage(src, width, height);
		this.add(image.getImg());
		this.repaint();
	} 
			
	public void moveShape(final String id, final int dx, final int dy) {
		final Shape shape = shapes.get(id);
		if (shape != null) {
			shape.move(dx, dy);
			this.repaint();
			if (shape.getshapeListener() != null) {
				shape.getshapeListener().shapeMoved(id, dx, dy);
			}
		}
	}

	public void moveToLocation(final String id, final int cordX, final int cordY) {
		final Shape shape = shapes.get(id);
		if (shape != null) {
			shape.moveToLocation(cordX, cordY);
			//shape.setLocation(cordX, cordY);
			this.repaint();
		}
	}
	
	public void deleteShape(final String id) {
		final Shape shape = shapes.get(id);
		if (shape != null) {
			hide(id);
			if (shape instanceof Image) {
				final Image image = (Image) shape;
				this.remove(image.getImg());
			}	
			shapes.remove(id);
		}
		resort = true;
		this.repaint();
	}

	public void hideAll() {
		for (final Shape shape : shapes.values()) {
			shape.setStatus(STATUS.HIDE);
		}
		resort = true;
		this.repaint();
	}

	public void showAll() {
		for (final Shape shape : shapes.values()) {
			shape.setStatus(STATUS.SHOW);
		}
		resort = true;
		this.repaint();
	}

	public void deleteAll() {
		Shape shape;
		for (final String id : shapes.keySet()) {
			shape = shapes.get(id);
			if (shape != null) {
				hide(id);
			}
			if (shape instanceof Image) {
				final Image image = (Image) shape;
				this.remove(image.getImg());
			}	
		}
		shapes.clear();
		resort = true;
		this.repaint();
	}

	public void flipStatus(final String id) {
		final Shape shape = shapes.get(id);
		if (shape != null) {
			if (shape.getStatus() == STATUS.HIDE) {
				shape.setStatus(STATUS.SHOW);
			} else if (shape.getStatus() == STATUS.SHOW) {
				shape.setStatus(STATUS.HIDE);
			}
		}
		resort = true;
		this.repaint();
	}

	public void show(final String id) {
		final Shape shape = shapes.get(id);
		if (shape != null) {
			shape.setStatus(STATUS.SHOW);
		}
		resort = true;
		this.repaint();
	}
	
	public void hide(final String id) {
		final Shape shape = shapes.get(id);
		if (shape != null) {
			shape.setStatus(STATUS.HIDE);
		}
		resort = true;
		this.repaint();
	}
	
	protected void addListeners() {
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(final MouseEvent event) {
				final Shape shape = getShapeByXY(event.getX(), event.getY());
				if (shape != null) {
					Game.MouseHandler().shapeReleased(shape, event.getX(), event.getY());
				}
				else {
					Game.MouseHandler().screenReleased(event.getX(), event.getY());
				}
			}

			@Override
			public void mousePressed(final MouseEvent event) {
				final Shape shape = getShapeByXY(event.getX(), event.getY());
				if (shape != null) {
					Game.MouseHandler().shapePressed(shape, event.getX(), event.getY());
				}
				else {
					Game.MouseHandler().screenPressed(event.getX(), event.getY());
				}
			}

			@Override
			public void mouseExited(final MouseEvent event) {
			}

			@Override
			public void mouseEntered(final MouseEvent event) {
			}

			@Override
			public void mouseClicked(final MouseEvent event) {
				final Shape shape = getShapeByXY(event.getX(), event.getY());
				if (shape != null) {
					if (event.getButton() == 1) {// click
						Game.MouseHandler().shapeClicked(shape, event.getX(), event.getY());
					}
					if (event.getButton() == 3) {// rightclick
						Game.MouseHandler().shapeRightClicked(shape, event.getX(), event.getY());
					}
					
				}
				else {
					if (event.getButton() == 1) {// click
						Game.MouseHandler().screenClicked(event.getX(), event.getY());
					}					
					if (event.getButton() == 3) {// rightclick
						Game.MouseHandler().screenRightClicked(event.getX(), event.getY());
					}
				}
			}
		});
		
		this.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(final MouseEvent event) {
				final Shape shape = getShapeByXY(event.getX(), event.getY());
				if (shape != null) {
					Game.MouseHandler().mouseMovedOverShape(shape, event.getX(), event.getY());
				}
				else {
					Game.MouseHandler().mouseMovedOverScreen(event.getX(), event.getY());
				}
			}

			@Override
			public void mouseDragged(final MouseEvent event) {
				System.out.println("in this game dragging shapes is disabled!");
				// IN OUR GAME - WE DON'T WANT SHAPES TO BE DRAGGABLE !!!!
				//---------------------------------------------------------------
				// final Shape shape = getShapeByXY(event.getX(), event.getY());
				// if (shape != null) {
				// 	Game.MouseHandler().mouseDraggedOverShape(shape, event.getX(), event.getY());
				// }
				// else {
				// 	Game.MouseHandler().mouseDraggedOverScreen(event.getX(), event.getY());
				// }
			}
		});
		
		
	}

	private Shape[] getImagesSortedByZOrder() {
		if (resort) {
			sortedShapes = shapes.values().toArray(new Shape[0]);
			Arrays.sort(sortedShapes, (s1,s2) -> {return (s1.getzOrder() - s2.getzOrder());});
			resort = false;
		}
		return sortedShapes;
	}

	private Shape getShapeByXY(final int x, final int y) {
		Shape[] tempShapes = getImagesSortedByZOrder();
		Shape shape;

		// Run over the shapes in reverse order so that top shape is selected 
		// before a bottom shape.
		for (int i = tempShapes.length-1; i >=0 ; i--) {
		   shape = tempShapes[i];
		   if (shape.getStatus() == STATUS.SHOW) {
			   if (shape.isInArea(x, y)) {
					return shape;
				}
			}
		}
		return null;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		 super.paintComponent(g);

		if (backgroundImage != null) {
			g.drawImage(backgroundImage.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
		}

		 Shape[] tempShapes = getImagesSortedByZOrder();
		 Shape shape;
			  
		 // Draw geometric shapes and texts in order
		 for (int i = 0; i < tempShapes.length; i++) {
			shape = tempShapes[i];
			if (shape instanceof Image) {
				continue; // Do not handle Images in this pass
			}
			if (shape.getStatus() == STATUS.SHOW) {
				shape.draw((Graphics2D) g);
				if (shape instanceof TextLabel) {
					TextLabel lbl = (TextLabel) shape;
					this.add(lbl.getLabel());
				}

			}
			if (shape.getStatus() == STATUS.HIDE) {
				if (shape instanceof TextLabel) {
					TextLabel lbl = (TextLabel) shape;
					this.remove(lbl.getLabel());
				}

			}
		}

		// Draw images in reverse order
		for (int i = tempShapes.length-1; i >=  0; i--) {
			shape = tempShapes[i];
			if (!(shape instanceof Image)) {
				continue; // Do not handle Non-Image shapes in this pass
			}
			if (shape.getStatus() == STATUS.SHOW) {
				shape.draw((Graphics2D) g);
				Image image = (Image) shape;
				this.add(image.getImg());

			}
			if (shape.getStatus() == STATUS.HIDE) {
				Image image = (Image) shape;
				this.remove(image.getImg());
			}
		}

	}

}
