import java.awt.Color;
import java.awt.Graphics;


public class DRect extends DShape {

	DRectModel dRModel;
	
	public DRect(){
		dRModel = new DRectModel(0, 0, 0, 0, Color.GRAY, 0);
	}
	
	public void draw(Graphics g) {
		g.setColor(getColor());
		
		boolean HeightIsNegative=false;
		boolean WidthIsNegative =false;
		
		int tempWidth = 0;
		int tempHeight= 0;
		if(getWidth()<0) {
			setWidth(Math.abs(getWidth()));
			tempWidth = getWidth();
			WidthIsNegative = true;
		}
		if(getHeight()<0){
			setHeight(Math.abs(getHeight()));
			tempHeight = getHeight();
			HeightIsNegative = true;
		}
		
		g.fillRect(getX()-tempWidth, getY()-tempHeight, getWidth(), getHeight());
		
		if(HeightIsNegative)setHeight(getHeight()*(-1));
		if(WidthIsNegative) setWidth(getWidth()*(-1));
	}

}
