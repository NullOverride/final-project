import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class DText extends DShape{

	DTextModel dTModel;
	
	public DText(){
		dTModel = new DTextModel(0, 0, 0, 0, Color.GRAY);
	}

	public void draw(Graphics g) {
		g.setColor(getColor());
		Font myFont = new Font("Dialog", Font.PLAIN, 24);
		g.drawString("Hello", getX(), getY());
		g.setFont(myFont);
	}
}
