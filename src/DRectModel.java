import java.awt.Color;
import java.awt.Rectangle;


public class DRectModel extends DShapeModel {
	
	private Rectangle r;

	public DRectModel(int x, int y, int width, int height, Color color) {
		super(x, y, width, height, color);
		setRect(new Rectangle(x,y,width,height));
	}

	public Rectangle getRect() {
		return r;
	}

	public void setRect(Rectangle r) {
		this.r = r;
	}
	
	public void setX(int x) {
		super.setX(x);
		r.setRect(x, getY(), getWidth(), getHeight());
	}
	public void setY(int y) {
		super.setY(y);
		r.setRect(getX(), y, getWidth(), getHeight());
	}
	public void setWidth(int width) {
		super.setWidth(width);
		r.setRect(getX(), getY(), width, getHeight());
	}
	public void setHeight(int height) {
		super.setHeight(height);
		r.setRect(getX(), getY(), getWidth(), height);
	}

}
