import java.awt.Color;
import java.awt.Graphics;


public class DRect extends DShape {

	DRectModel dRModel;
	
	public DRect(){
		dRModel = new DRectModel(0, 0, 0, 0, Color.GRAY);
	}
	
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.fillRect(getX(), getY(), getWidth(), getHeight());
	}

}
