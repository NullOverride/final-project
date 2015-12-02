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
		int stringlen = (int) g.getFontMetrics().getStringBounds("Hello", g).getWidth();
		int start = getWidth()/2 - stringlen/2;
		Font myFont = new Font("Dialog", Font.PLAIN, 24);
		g.drawString(input, start + getX(), start + getY());
		g.setFont(myFont);
		
		
		
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
