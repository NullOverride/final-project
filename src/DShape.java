import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


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

}
