package shapes;

import java.awt.Graphics2D;

public class Circle extends FilledShape{
	private int radius;
	private int posX;
	private int posY;
	
	public Circle(String id, int posX, int posY, int radius) {
		super(id);
		this.posX = posX;
		this.posY = posY;
		this.radius = radius;
	}

	public int getRadius() {
		return radius;
	}
	public void setRadius(int radius) {
		this.radius = radius;
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
	
	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		g.drawArc(posX - radius, posY - radius, 2 * radius, 2*radius, 0, 360);
		if (isFilled()) {
			g.setColor(getFillColor());
			g.fillOval(posX-radius, posY-radius, 2 * radius, 2*radius);
		}
	}
	
	@Override
	public boolean isInArea(int x, int y) {
		// System.out.println("Checking if "+x+" is inside "+posX);
		return (Math.sqrt(Math.pow(x-posX, 2) + Math.pow(y-posY, 2)) < radius );
	}

// @Override
// 	public int[] circPosition(int x1, int y1,int x2, int y2) {

// 		x1=this.posX-radius/2;
// 		x2=this.posX-radius/2;

// 		y1=this.posY-radius/2;
// 		y2=this.posY-radius/2;
// 				int[] res = { x1,  y1, x2,  y2};
// 				return res;
// 	}

		public int x1CircPosition() {

		int x1=this.posX;//-radius;//*4;
				return x1;
	}

	public int x2CircPosition() {

		int x2=this.posX;//+radius;//*2;
				return x2;
	}

	public int y1CircPosition() {
		int y1=this.posY;//-radius;//*4;
				return y1;
	}
	
	public int y2CircPosition() {
		int y2=this.posY;//radius;//*2;
				return y2;
	}
	@Override
	public void move(int dx, int dy) {
		this.posX += dx;
		this.posY += dy;
	}
	
	@Override
	public void moveToLocation(int x, int y) {
		this.posX = x;
		this.posY = y;
	}	
	
}
