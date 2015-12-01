import java.awt.Color;
import java.awt.Graphics;


public class DLine extends DShape {

	DLineModel dLModel;

	public DLine() {
		dLModel = new DLineModel(0, 0, 0, 0, Color.GRAY);
	}

	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawLine(getX(), getY(), getWidth(), getHeight());
	}
}