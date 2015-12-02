import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class DText extends DShape{

	DTextModel dTModel;
	private String input;
	
	public DText(){
		dTModel = new DTextModel(0, 0, 0, 0, Color.GRAY);
	}

	public void draw(Graphics g) {
		g.setColor(getColor());
		//int stringlen = (int) g.getFontMetrics().getStringBounds("Hello", g).getWidth();
		int start = getHeight()/2;
		Font myFont = new Font("Dialog", Font.PLAIN, 24);
		g.setFont(myFont);
		g.drawString(input, getX(), start + getY());
	}
	public String getInput()
	{
		return input;
	}
	public void setInput(String i)
	{
		input = i;
	}
}