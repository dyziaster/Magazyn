package FrontEnd;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
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
	private JPanel panelButtons;
	private JButton delBtn;
	private JButton newBtn;
	private JLabel labelIlosc;
	private JLabel labelNetto;
	private JLabel ilosc;
	private JLabel netto;
	private JLabel labelBrutto;
	private JLabel brutto;

	public Ttable() {
		this(null, null, null);

	}

	public Ttable(Document document, DefaultTableModel tableModel, List<String> ids) {

		panel = this;
		panelButtons = new JPanel();
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

		labelIlosc = new JLabel("ilosc op");
		ilosc = new JLabel("");
		labelNetto = new JLabel("netto");
		netto = new JLabel("");
		labelBrutto = new JLabel("brutto");
		brutto = new JLabel("");
		
		editBtn = new JButton("edit");
		editBtn.addActionListener(document);
		editBtn.setActionCommand("TABLE_EDIT");
		
		delBtn = new JButton("delete");
		delBtn.addActionListener(document);
		delBtn.setActionCommand("TDOCS_DELETE");
		

		newBtn = new JButton("New");
		newBtn.setActionCommand("TDOCS_NEW");
		newBtn.addActionListener(document);

		panelButtons.add(labelIlosc);
		panelButtons.add(ilosc);
		panelButtons.add(labelNetto);
		panelButtons.add(netto);
		panelButtons.add(labelBrutto);
		panelButtons.add(brutto);
		panelButtons.add(delBtn);
		panelButtons.add(editBtn);
		panelButtons.add(newBtn);

		panel.add(scrollTable, BorderLayout.CENTER);
		panel.add(panelButtons, BorderLayout.PAGE_END);
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
		delBtn.setEnabled(false);
		newBtn.setEnabled(false);
		table.setEnabled(false);
	}

	public void enableTable() {
		delBtn.setEnabled(true);
		editBtn.setEnabled(true);
		newBtn.setEnabled(true);
		table.setEnabled(true);
	}

	public void clear() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		ilosc.setText("");
		netto.setText("");
		brutto.setText("");
	}

	public void setTableIdMap(List<String> columnRecordsFrom) {
		ids = columnRecordsFrom;
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		int row = table.getSelectedRow();
		if(row < 0)
			return;
		String id = ids.get(row);
		document.writeTdocs(Integer.valueOf(id));
		table.grabFocus();
	}
	
	public void setNewBtnEnabled(boolean b) {
		newBtn.setEnabled(b);
	}
	
	public void setDelBtnEnabled(boolean b) {
		delBtn.setEnabled(b);
	}

	public int getSelectedRow() {
		return table.getSelectedRow();
	}

	public void setEditBtnEnabled(boolean b) {
		editBtn.setEnabled(b);		
	}
	
	public void setIlosc(String quantity){
		ilosc.setText(quantity);
	}
	public void setNetto(String quantity){
		netto.setText(quantity);
	}
	public void setBrutto(String quantity){
		brutto.setText(quantity);
	}
	

}
