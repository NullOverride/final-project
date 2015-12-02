import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;



public class DText extends DShape{


	DTextModel dTModel;
	private String input;
	private String font;
	private int size = 0;


	public DText(){
		dTModel = new DTextModel(0, 0, 0, 0, Color.GRAY, 0);
	}



	public void draw(Graphics g) {
		FontMetrics fm = g.getFontMetrics();
		g.setColor(getColor());
		int x = (getWidth() - fm.stringWidth(input)) / 2;
		int y = (fm.getAscent() + (getHeight() - (fm.getAscent() + fm.getDescent())) / 2);
		Font myFont = new Font(font, Font.PLAIN, getHeight()/2);
		g.setFont(myFont);
		g.drawString(input, getX(), y + getY());

	}

	public String getInput()
	{
		return input;
	}

	public void setInput(String i)
	{
		input = i;
	}

	public void setFont(String f)
	{
		font = f;
	}

	public void setSize(int i)
	{
		size = i;
	}

}