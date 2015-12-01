import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;


public class DShape {

	DShapeModel dSM;
	
	public DShape(){
		dSM = new DShapeModel(0, 0, 0, 0, Color.gray);
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
	
	public ArrayList<Point> getKnobs() {
		ArrayList<Point> points = new ArrayList<Point>();
		points.add(new Point(getX()+1,getY()+1)); //Top Left
		points.add(new Point(getX()+1,getY()+getHeight()-1)); //Bottom Left
		points.add(new Point(getX()+getWidth()-1,getY()+1)); //Top Right
		points.add(new Point(getX()+getWidth()-1,getY()+getHeight()-1)); //Bottom Right
		return points;
	}

}
