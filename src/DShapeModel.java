import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;


public class DShapeModel {
	
	private int x;
	private int y;
	private int width;
	private int height;
	private Color color;
	private int ID;
	
	// Constructor
	
	public DShapeModel(int x, int y, int width, int height, Color color, int ID){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.setColor(color);
		this.ID = ID;
	}

	// Getters
	
	public Rectangle getBoundsRect() {
		return new Rectangle(x, y, width, height);
	}
	public Color getColor() {
		return color;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public int getID() {
		return ID;
	}
	
	// Setters
	
	public void setBoundsRect(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	public void setBoundsRect(Rectangle rect) {
		this.x = (int) rect.getX();
		this.y = (int) rect.getY();
		this.width = (int) rect.getWidth();
		this.height = (int) rect.getHeight();
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public void setID(int ID)
	{
		this.ID = ID;
	}
}
