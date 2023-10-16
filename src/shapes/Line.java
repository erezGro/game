package shapes;

import java.awt.Graphics2D;

public class Line extends Shape {
	private int x1, y1, x2, y2;
	
	public Line(String id, int x1, int y1, int x2, int y2) {
		super(id);
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.setWeight(3);
	}
	
	public int getX1() {
		return x1;
	}
	public void setX1(int x) {
		this.x1 = x;
	}
	public int getY1() {
		return y1;
	}
	public void setY1(int y) {
		this.y1 = y;
	}

	public int getX2() {
		return x2;
	}
	public void setX2(int x) {
		this.x2 = x;
	}
	public int getY2() {
		return y2;
	}
	public void setY2(int y) {
		this.y2 = y;
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		g.drawLine(x1, y1, x2, y2);
	}
	
	@Override
	public boolean isInArea(int x, int y) {
		if (x < Math.min(x1, x2) || x > Math.max(x1,x2) || y < Math.min(y1, y2) || y > Math.max(y1,y2)) {
			return false;
		}
		return false;
	}
	
	@Override
	public void move(int dx, int dy) {
		this.x1 += dx;
		this.x2 += dx;
		this.y1 += dy;
		this.y2 += dy;
	}
	
	@Override
	public void moveToLocation(int x, int y) {
		this.x2 += (x-x1);
		this.y2 += (y-y1);
		this.x1 = x;
		this.y1 = y;
	}

	// @Override
	// public int x1CircPosition() {
	// 	// TODO Auto-generated method stub
	// 	throw new UnsupportedOperationException("Unimplemented method 'x1CircPosition'");
	// }

	// @Override
	// public int x2CircPosition() {
	// 	// TODO Auto-generated method stub
	// 	throw new UnsupportedOperationException("Unimplemented method 'x2CircPosition'");
	// }

	// @Override
	// public int y1CircPosition() {
	// 	// TODO Auto-generated method stub
	// 	throw new UnsupportedOperationException("Unimplemented method 'y1CircPosition'");
	// }

	// @Override
	// public int y2CircPosition() {
	// 	// TODO Auto-generated method stub
	// 	throw new UnsupportedOperationException("Unimplemented method 'y2CircPosition'");
	// }

	// @Override
	// public int getRadius() {
	// 	// TODO Auto-generated method stub
	// 	throw new UnsupportedOperationException("Unimplemented method 'getRadius'");
	// }	
	

}
