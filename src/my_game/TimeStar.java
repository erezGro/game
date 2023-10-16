package my_game;

import game.Game;
import shapes.Image;
import game.ShapeListener;



public class TimeStar implements ShapeListener{
	public enum Direction{
		RIGHT (0,-50),
		LEFT(-1,50),
		UP (0,-50),
		DOWN(0,50);
		
		private final int xVec, yVec;
		private Direction(int xVec, int yVec) {
			this.xVec = xVec;
			this.yVec = yVec;
		}
		public int xVec() {
			return xVec;
		}
		public int yVec() {
			return yVec;
		}
	}
	

	private String imageID = "Time Star";
	private int rotation = 0;	// In degrees
	private Point location;
	private Direction directionPolicy = Direction.RIGHT;
	private Direction direction = Direction.RIGHT;

	public Point getLocation() {
		return this.location;
	}
	
	public void setLocation(Point location) {
		this.location = location;
	}
	
	public void setImageID(String id) {
		this.imageID = id;
	}
	
	public String getImageID() {
		return this.imageID;
		//return "space_ship";
	}
	
	public String getImageName() {
//		return "resources/asteroid_small.png";
        return "resources/asteroid_small.png";

	}
	
	
	public int getRotation() {
		return rotation;
	}	

	public void moveLocation(int dx, int dy) {
		this.location.x += dx;
		this.location.y += dy;
		Image i = (Image) (Game.UI().canvas().getShape(imageID));
		i.move(dx,dy);
	}
	
	public void setRotation(int rotation) {
		this.rotation = rotation;
		Image i = (Image) (Game.UI().canvas().getShape(imageID));
		i.setRotation(rotation);
	}	

	public void changeImage() {
		Game.UI().canvas().changeImage(imageID, "resources/spaceship3.jpg", 360, 360);;
		Game.UI().canvas().moveToLocation(imageID,150,150);
	}
	
	@Override
	public void shapeMoved(String shapeID, int dx, int dy) {
		moveLocation(dx, dy);
	}

	@Override
	public void shapeStartDrag(String shapeID) {

	}

	@Override
	public void shapeEndDrag(String shapeID) {

	}



	@Override
	public void shapeClicked(String shapeID, int x, int y) {
	}

	@Override
	public void shapeRightClicked(String shapeID, int x, int y) {
	}

	@Override
	public void mouseEnterShape(String shapeID, int x, int y) {

	}

	@Override
	public void mouseExitShape(String shapeID, int x, int y) {
	}
		
	public void setDirectionPolicy(Direction direction) {
		directionPolicy = direction;
	}
	
	public Direction getDirection() {
		return direction;
	}

	public Direction getPolicy() {
		return directionPolicy;
	}

	public void move() {
		
			Point desired = new Point(location.x + directionPolicy.xVec(), location.y + directionPolicy.yVec());
			direction = directionPolicy;
			location.x = desired.x;
			location.y = desired.y;
			
		
	}

}	

