
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;


public class Canvas extends JPanel {
	private ArrayList<DShape> collection;
	private DShape selected;

	public Canvas(){
		collection = new ArrayList<DShape>();
		setSelected(null);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(DShape shape : collection) {
			shape.draw(g);
			if(shape.equals(selected))
			{
				int knobWH = 18;
				for(Point p : shape.getKnobs())
				{
					g.setColor(Color.black);
					g.fillRect(((int)p.getX())-9, ((int)p.getY())-9, knobWH, knobWH);
				}
			}
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
			setSelected(rect);
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
			setSelected(oval);
		}
		else if(shape instanceof DLineModel)
        {
            DLine line = new DLine();
            line.setColor(shape.getColor());
            line.setX(shape.getX());
            line.setY(shape.getY());
            line.setWidth(shape.getWidth());
            line.setHeight(shape.getHeight());
            collection.add(line);
            setSelected(line);
        }
		
		repaint();
	}

	public DShape getSelected() {
		return selected;
	}
	
	public void setSelected(DShape shape)
	{
		this.selected = shape;
	}

	public void setSelected(int x, int y) {
		for(DShape shape: collection)
		{
			if (x >= shape.getX() && x <= shape.getX() + shape.getWidth() && y >= shape.getY() && y <= shape.getY() + shape.getHeight())
			{
				this.selected = shape;
			}
		}	
	}
	public void changeColor(Color color) {
		for(DShape shape: collection)
		{
			if (getSelected().equals(shape))
			{
				shape.setColor(color);
			}
		}
		repaint();
	}


}
