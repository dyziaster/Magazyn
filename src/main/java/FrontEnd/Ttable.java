package FrontEnd;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class Ttable extends JScrollPane{
	
	private DefaultTableModel dtm;
	private JTable table;
	private TableColumnModel tcm;
	private Ttable scrollTable;
	
	public Ttable(){
		this(null);
		
	}

	public Ttable(DefaultTableModel tableModel){

		scrollTable = this;
		table = new JTable();
		setTableData(table,tableModel);
		tcm = table.getColumnModel();
		
		table.setRowSelectionAllowed(true);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		
		table.setFillsViewportHeight(true);
		table.getTableHeader().setReorderingAllowed(false);
		scrollTable.setViewportView(table);

	}
	
	public void setTableData(JTable table,DefaultTableModel model) {

		TableColumnModel columnModel = table.getColumnModel();
		table.setModel(model);

		int lmax = 2;
		for (int i = 0; i < columnModel.getColumnCount(); i++) {
			lmax = model.getColumnName(i).toString().length();
			int lmaxx = 0;
			for (int j = 0; j < model.getRowCount(); j++) {
				Object value = this.getValueAt(j, i);
				if (value != null)
					lmaxx = value.toString().length();
				if (lmaxx > lmax)
					lmax = lmaxx;
			}
			columnModel.getColumn(i).setPreferredWidth(lmax * 8);
		}

		((DefaultTableModel) table.getModel()).fireTableDataChanged();
	}
	


	public String getValueAt(int row, int column) {
		Object value = table.getModel().getValueAt(row, column);
		if (value == null)
			return "";
		else
			return value.toString();
	}

}
