import java.awt.Color;
import java.awt.Graphics;


public class DShape {

	DShapeModel dSM;
	
	public DShape(){
		dSM = new DShapeModel(0, 0, 0, 0, Color.gray);
	}
	public void draw(Graphics g) {
		g.setColor(dSM.getColor());
		g.drawRect(dSM.getX(), dSM.getY(), dSM.getWidth(), dSM.getHeight());
	}

}
