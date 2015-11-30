import java.awt.Color;
import java.awt.Graphics;


public class DOval extends DShape {
	
	DOvalModel dOModel;
	public DOval()
	{
		dOModel = new DOvalModel(0, 0, 0, 0, Color.GRAY);
	}
	public void setColor(Color color) {
		dOModel.setColor(color);
	}
	public void setX(int x) {
		dOModel.setX(x);
	}
	public void setY(int y) {
		dOModel.setY(y);
	}
	public void setWidth(int width) {
		dOModel.setWidth(width);
	}
	public void setHeight(int height) {
		dOModel.setHeight(height);
	}
	
	
	public Color getColor() {
		return dOModel.getColor();
	}
	public int getX() {
		return (int) dOModel.getX();
	}
	
	public int getY() {
		return (int) dOModel.getY();
	}
	
	public int getWidth() {
		return (int) dOModel.getWidth();
	}
	
	public int getHeight() {
		return (int) dOModel.getHeight();
	}
	
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.fillOval(getX(), getY(), getWidth(), getHeight());
	}

}
