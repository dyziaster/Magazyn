package Model;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

class MyTModel extends DefaultTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1984450624885710499L;

	public MyTModel(Vector<Vector<Object>> data, Vector<String> columnNames) {
		super(data, columnNames);
	}

	@Override
	public boolean isCellEditable(int x, int y) {
		return false;
	}
}