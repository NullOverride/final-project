import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;


public class DShape {

	private DShapeModel dSM;
	
	public DShape(){
		dSM = new DShapeModel(0, 0, 0, 0, Color.gray, 0);
	}
	public void draw(Graphics g) {
		g.setColor(dSM.getColor());
		g.drawRect(dSM.getX(), dSM.getY(), dSM.getWidth(), dSM.getHeight());
	}
	public Rectangle getBounds()
	{
		return dSM.getBoundsRect();
	}


	public void setColor(Color color) {
		dSM.setColor(color);
	}
	public void setX(int x) {
		dSM.setX(x);
	}
	public void setY(int y) {
		dSM.setY(y);
	}
	public void setWidth(int width) {
		dSM.setWidth(width);
	}
	public void setHeight(int height) {
		dSM.setHeight(height);
	}
	public void setID(int ID) {
		dSM.setID(ID);
	}
	public void setAll(int x, int y, int width, int height, Color color, int ID)
	{
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setColor(color);
		setID(ID);
	}
	
	
	public Color getColor() {
		return dSM.getColor();
	}
	public int getX() {
		return (int) dSM.getX();
	}
	public int getY() {
		return (int) dSM.getY();
	}
	public int getWidth() {
		return (int) dSM.getWidth();
	}
	public int getHeight() {
		return (int) dSM.getHeight();
	}
	public int getID() {
		return dSM.getID();
	}
	public DShapeModel getShapeModel() {
		return dSM;
	}
	
	public ArrayList<Rectangle> getKnobs() {
		ArrayList<Rectangle> knobs = new ArrayList<Rectangle>();
		knobs.add(new Rectangle(getX()-9,getY()-9,18,18)); //Top Left
		knobs.add(new Rectangle(getX()-9,getY()+getHeight()-9,18,18)); //Bottom Left
		knobs.add(new Rectangle(getX()+getWidth()-9,getY()-9,18,18)); //Top Right
		knobs.add(new Rectangle(getX()+getWidth()-9,getY()+getHeight()-9,18,18)); //Bottom Right
		return knobs;
	}
	public ArrayList<Rectangle> getLineKnobs(){
		ArrayList<Rectangle> knobs = new ArrayList<Rectangle>();
		knobs.add(new Rectangle(getX()-9, getY()-9,18,18));
		knobs.add(new Rectangle(getWidth()-9, getHeight()-9,18,18));
		return knobs;
	}


}
