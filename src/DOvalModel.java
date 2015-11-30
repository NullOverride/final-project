import java.awt.Color;
import java.awt.geom.Ellipse2D;

public class DOvalModel extends DShapeModel
{
	public DOvalModel(int x, int y, int width, int height, Color color) 
	{
		super(x, y, width, height, color);
		setOval(new Ellipse2D.Double(x,y, width, height));
	}
	private Ellipse2D o;

	public Ellipse2D getOval() {
		return o;
	}

	public void setOval(Ellipse2D o) {
		this.o = o;
	}
	
	public void setX(int x) {
		super.setX(x);
		o.setFrame(x, getY(), getWidth(), getHeight());
	}
	public void setY(int y) {
		super.setY(y);
		o.setFrame(getX(), y, getWidth(), getHeight());
	}
	public void setWidth(int width) {
		super.setWidth(width);
		o.setFrame(getX(), getY(), width, getHeight());
	}
	public void setHeight(int height) {
		super.setHeight(height);
		o.setFrame(getX(), getY(), getWidth(), height);
	}

}
