import java.awt.Color;
import java.awt.Graphics;


public class DRect extends DShape {

	// Shapes do not have object data!
	// Todo: pointer to DShapeModel that stores coordinate information (x, y, width, height)

	DRectModel dRModel;
	
	public DRect(){
		dRModel = new DRectModel(0, 0, 0, 0, Color.GRAY);
	}

	
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.fillRect(getX(), getY(), getWidth(), getHeight());
	}

}
