import java.awt.Color;
import java.awt.Graphics;


public class DOval extends DShape {
	
	DOvalModel dOModel;
	
	public DOval() {
		dOModel = new DOvalModel(0, 0, 0, 0, Color.GRAY);
	}
	
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.fillOval(getX(), getY(), getWidth(), getHeight());
	}

}
