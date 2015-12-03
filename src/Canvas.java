
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;


public class Canvas extends JPanel {
	private ArrayList<DShape> collection;
	private DShape selected;
	private final int KNOB_SIZE = 9;

	public Canvas(){
		collection = new ArrayList<DShape>();
		setSelected(null);
	}

	public ArrayList<DShape> getCollection() 
	{
		return collection;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(DShape shape : collection) {
			shape.draw(g);
			if(shape.equals(selected) && !(shape instanceof DLine))
			{
				int knobWH = 18;
				for(Point p : shape.getKnobs())
				{
					g.setColor(Color.black);
					g.fillRect(((int)p.getX())-KNOB_SIZE, ((int)p.getY())-KNOB_SIZE, knobWH, knobWH);
				}
			}
			else if(shape.equals(selected) && (shape instanceof DLine))
			{
				int knobWH = 18;
				for(Point p : shape.getLineKnobs())
				{
					g.setColor(Color.black);
					g.fillRect(((int)p.getX())-KNOB_SIZE, ((int)p.getY())-KNOB_SIZE, knobWH, knobWH);
				}
			}
		}
	}
	
	public void addShape(DShape shape) {
		collection.add(shape);
		setSelected(shape);
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

	public void moveSelectedToFront() {
		for (int i = 0; i < collection.size() - 1; i++)
		{
			if(collection.get(i).equals(selected))
			{
				DShape temp = collection.get(i);
				collection.set(i, collection.get(i + 1));
				collection.set(i + 1, temp);
//				collection.get(i).setID(i + 1);
//				collection.get(i + 1).setID(temp.getID());
				break;
			}
		}
		repaint();
	}
	
	public void moveSelectedToBack() {
		for (int i = 1; i < collection.size(); i++)
		{
			if(collection.get(i).equals(selected))
			{
				DShape temp = collection.get(i);
				collection.set(i, collection.get(i - 1));
				collection.set(i - 1, temp);
//				collection.get(i).setID(i - 1);
//				collection.get(i - 1).setID(temp.getID());
				break;
			}
		}
		repaint();
	}
	
	public void removeSelected() {
		for(int i = 0; i < collection.size(); i++)
		{
			if (collection.get(i).equals(getSelected()))
			{
				for(DShape shape : collection)
				{
					if(shape.getID() > collection.get(i).getID())
					{
						shape.setID(shape.getID() - 1);
					}
				}
				collection.remove(i);
			}
		}
		repaint();
	}
	
	public void reset() {
		collection = new ArrayList<DShape>();
		setSelected(null);
	}
	
	public void setNewLocation(int x, int y)
	{
		selected.getShapeModel().setX(x);
		selected.getShapeModel().setY(y);
	}

	public void updateShape(DShape shape) {
		for(DShape d : collection)
		{
			if(d.getID() == shape.getID())
			{
				d.setAll(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight(), shape.getColor(), shape.getID());
			}
		}
		repaint();
	}

	public void removeShape(DShape shape) {
		for(int i = 0; i < collection.size(); i++)
		{
			if (shape.getID() == collection.get(i).getID())
			{
				for(DShape s : collection)
				{
					if(s.getID() > collection.get(i).getID())
					{
						s.setID(shape.getID() - 1);
					}
				}
				collection.remove(i);
			}
		}
		repaint();
	}
	
}
