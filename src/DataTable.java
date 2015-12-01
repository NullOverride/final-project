import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;


public class DataTable extends AbstractTableModel {

	public String[] colNames = { "X", "Y", "Width", "Height" };
	
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
			return data.get(shape).getX();
		case 1:
			return data.get(shape).getY();
		case 2:
			return data.get(shape).getWidth();
		case 3:
			return data.get(shape).getHeight();
		}

		return null;
	}
	
	public void addRow(DShapeModel shape) {
		data.add(shape);
		fireTableDataChanged();
	}
	
	public void deleteRow(DShapeModel shape)
	{
		for(DShapeModel s : data)
		{
			if (shape.equals(s))
			{
				data.remove(s);
			}
		}
		fireTableDataChanged();
	}
	
	public String getColumnName(int col)
	{
		return colNames[col];
	}
	
}
