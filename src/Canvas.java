
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;


public class Canvas extends JPanel {
	ArrayList<DShape> collection;

	public Canvas(){
		collection = new ArrayList<DShape>();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(DShape shape : collection) {
			shape.draw(g);
		}
	}
	
	public void addShape(DShapeModel shape) {
		if (shape instanceof DRectModel) 
		{
			DRect rect = new DRect();
			rect.setColor(shape.getColor());
			rect.setX(shape.getX());
			rect.setY(shape.getY());
			rect.setWidth(shape.getWidth());
			rect.setHeight(shape.getHeight());
			collection.add(rect);
		}
		else if(shape instanceof DOvalModel)
		{
			DOval oval = new DOval();
			oval.setColor(shape.getColor());
			oval.setX(shape.getX());
			oval.setY(shape.getY());
			oval.setWidth(shape.getWidth());
			oval.setHeight(shape.getHeight());
			collection.add(oval);
		}
		repaint();
	}


}
