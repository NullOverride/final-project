import java.awt.Color;
import java.awt.Graphics;


public class DRect extends DShape {

	// Shapes do not have object data!
	// Todo: pointer to DShapeModel that stores coordinate information (x, y, width, height)

	DRectModel dRModel;
	
	public DRect(){
		dRModel = new DRectModel(0, 0, 0, 0, Color.GRAY);
	}


	public void setColor(Color color) {
		dRModel.setColor(color);
	}
	public void setX(int x) {
		dRModel.setX(x);
	}
	public void setY(int y) {
		dRModel.setY(y);
	}
	public void setWidth(int width) {
		dRModel.setWidth(width);
	}
	public void setHeight(int height) {
		dRModel.setHeight(height);
	}
	
	
	public Color getColor() {
		return dRModel.getColor();
	}
	public int getX() {
		return (int) dRModel.getX();
	}
	
	public int getY() {
		return (int) dRModel.getY();
	}
	
	public int getWidth() {
		return (int) dRModel.getWidth();
	}
	
	public int getHeight() {
		return (int) dRModel.getHeight();
	}
	
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.fillRect(getX(), getY(), getWidth(), getHeight());
	}

}
