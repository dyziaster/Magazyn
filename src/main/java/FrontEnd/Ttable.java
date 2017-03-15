package FrontEnd;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class Ttable extends JPanel implements ListSelectionListener {

	private DefaultTableModel dtm;
	private JTable table;
	private TableColumnModel tcm;
	private JScrollPane scrollTable;
	private Ttable panel;
	private JButton editBtn;
	private Document document;
	private List<String> ids;
	private int selectedRow;

	public Ttable() {
		this(null, null, null);

	}

	public Ttable(Document document, DefaultTableModel tableModel, List<String> ids) {

		panel = this;
		panel.setLayout(new BorderLayout());
		this.document = document;
		this.ids = ids;

		table = new JTable();
		if (tableModel != null)
			setTableData(table, tableModel);
		tcm = table.getColumnModel();

		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// table.setColumnSelectionAllowed(true);
		// table.setCellSelectionEnabled(true);

		table.setFillsViewportHeight(true);
		table.getTableHeader().setReorderingAllowed(false);
		scrollTable = new JScrollPane(table);

		editBtn = new JButton("edit");
		editBtn.addActionListener(document);
		editBtn.setActionCommand("TABLE_EDIT");

		panel.add(scrollTable, BorderLayout.CENTER);
		panel.add(editBtn, BorderLayout.PAGE_END);
	}

	public void setTableData(JTable table, DefaultTableModel model) {
		table.getSelectionModel().removeListSelectionListener(this);
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
		table.getSelectionModel().addListSelectionListener(this);
	}

	public String getValueAt(int row, int column) {
		Object value = table.getModel().getValueAt(row, column);
		if (value == null)
			return "";
		else
			return value.toString();
	}

	public void refreshTableModel(DefaultTableModel tableModel) {
		setTableData(table, tableModel);
		tableModel.fireTableDataChanged();
	}

	public void setCursorPos(int x) {
		if (x < 0)
			return;
		else
			table.setRowSelectionInterval(x, x);
	}

	public void disableTable() {
		editBtn.setEnabled(false);
		table.setEnabled(false);
	}

	public void enableTable() {
		editBtn.setEnabled(true);
		table.setEnabled(true);
	}

	public void clear() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
	}

	public void setTableIdMap(List<String> columnRecordsFrom) {
		ids = columnRecordsFrom;
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		String id = ids.get(table.getSelectedRow());
		document.writeTdocs(Integer.valueOf(id));
		table.grabFocus();
	}

	public int getSelectedRow() {
		return table.getSelectedRow();
	}

}
