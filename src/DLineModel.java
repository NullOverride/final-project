import java.awt.Color;
import java.awt.geom.Line2D;


public class DLineModel extends DShapeModel{

	private Line2D l;
	
	public DLineModel(int x, int y, int width, int height, Color color, int ID) {
		super(x, y, width, height, color, ID);
		setLine(new Line2D.Double(x,y,width,height));
	}

	public Line2D getLine() {
		return l;
	}

	public void setLine(Line2D l) {
		this.l = l;
	}
	
	public void setX(int x) {
		super.setX(x);
		l.setLine(x, getY(), getWidth(), getHeight());
	}
	public void setY(int y) {
		super.setY(y);
		l.setLine(getX(), y, getWidth(), getHeight());
	}
	public void setWidth(int width) {
		super.setWidth(width);
		l.setLine(getX(), getY(), width, getHeight());
	}
	public void setHeight(int height) {
		super.setHeight(height);
		l.setLine(getX(), getY(), getWidth(), height);
	}

}
