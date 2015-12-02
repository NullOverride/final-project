import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;


public class DataTable extends AbstractTableModel {

	public String[] colNames = { "ID", "X", "Y", "Width", "Height" };
	
	public ArrayList<DShapeModel> data;
	
	public DataTable() {
		data = new ArrayList<>();
	}
	
	@Override
	public int getColumnCount() {
		return colNames.length;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int shape, int property) {
		switch(property)
		{
		case 0:
			return data.get(shape).getID();
		case 1:
			return data.get(shape).getX();
		case 2:
			return data.get(shape).getY();
		case 3:
			return data.get(shape).getWidth();
		case 4:
			return data.get(shape).getHeight();
		}
		return null;
	}
	
	public void addRow(DShapeModel shape) {
		data.add(shape);
		fireTableDataChanged();
	}
	
	public void removeRow(DShapeModel shape)
	{
		for(int i = 0; i < data.size(); i++)
		{
			if (data.get(i).getID() == shape.getID())
			{
				data.remove(i);
				break;
			}
		}
		fireTableDataChanged();
	}
	
	public void moveRowUp(DShapeModel shape) {
		for(int i = 1; i < data.size(); i++)
		{
			if (data.get(i).getID() == shape.getID())
			{
				DShapeModel temp = data.get(i);
				data.set(i, data.get(i - 1));
				data.set(i - 1, temp);
				fireTableRowsUpdated(i - 1, i);
				break;
			}
		}		
	}
	
	public void moveRowDown(DShapeModel shape) {
		for(int i = 0; i < data.size() - 1; i++)
		{
			if (data.get(i).getID() == shape.getID())
			{
				DShapeModel temp = data.get(i);
				data.set(i, data.get(i + 1));
				data.set(i + 1, temp);
				fireTableRowsUpdated(i, i + 1);
			}
		}
	}
	
	public void updateRow(DShapeModel shape)
	{
		for(int i = 0; i < data.size(); i++)
		{
			if(data.get(i).getID() == shape.getID())
			{
				data.set(i, shape);
				fireTableRowsUpdated(i, i);
				break;
			}
		}
	}
	
	public String getColumnName(int col)
	{
		return colNames[col];
	}
	
	public void reset() {
		data = new ArrayList<>();
		fireTableDataChanged();
	}

}
