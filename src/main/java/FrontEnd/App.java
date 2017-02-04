package FrontEnd;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import Controller.Controller;
import Controller.ListListener;

import javax.swing.JList;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class App extends JFrame {

	private static final int sizeConstant = 2;

	private static App window;
	private JList list;
	private JTable table;
	private Controller controller;
	private JLabel lbl;
	private DefaultTableModel dtm;
	private JTextField textField;
	private TableColumnModel tcm;
	private JTextField textField_1;

	private JPanel panelMain;
	private JPanel panelTable;
	private JPanel panelConsole;
	private JTextField editTextField;
	// public void setTextListener(ActionListener a){
	// textField.addActionListener(a);
	// }

	private String selectedCellString;
	private JButton editBtn;
	private JButton newBtn;
	private JButton cancelBtn;
	private JButton saveBtn;

	private String currentRecordValue = "";
	private JComboBox cBox;

	// public void setLabelText(String text){
	// lbl.setText(text);
	// }

	public void setListListener(ListSelectionListener lsm) {
		list.getSelectionModel().addListSelectionListener(lsm);
	}

	public void setController(Controller c) {
		controller = c;
	}

	public void setTableData(Object[][] data, Object[] columns) { // null
																	// pointers
																	// mass of
																	// them

		DefaultTableModel tableModel = (DefaultTableModel) table.getModel(); /////////// can it go with this.getvalueat ???

		tableModel.setDataVector(data, columns);
		TableColumnModel model = table.getColumnModel();
		int lmax = 2;
		for (int i = 0; i < tcm.getColumnCount(); i++) {
			lmax = columns[i].toString().length();
			int lmaxx = 0;
			for (int j = 0; j < tableModel.getRowCount(); j++) {
				Object value = this.getValueAt(j, i);
				if (value != null)
					lmaxx = value.toString().length();
				if (lmaxx > lmax)
					lmax = lmaxx;
			}
			model.getColumn(i).setPreferredWidth(lmax * 8);
		}

		tableModel.fireTableDataChanged();
	}

	public void setTableList(Object[] list) {
		this.list.setListData(list);

	}

	/**
	 * Launch the application.
	 */

	public static App getWindow() {
		return window;
	}

	public JTable getTable() {
		return table;
	}

	/**
	 * Create the application.
	 */

	public App() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setMinimumSize(new Dimension(screenSize.width / sizeConstant, screenSize.height / sizeConstant));

		panelMain = new JPanel();
		panelConsole = new JPanel();
		JTextArea jta = new JTextArea();
		jta.setPreferredSize(new Dimension(0, 200));

		panelTable = new JPanel();

		String sampleTab³es[] = { "tab1", "tab2", "tab3" };

		Object sampleData[][] = { { "Row1-Column1", "Row1-Column2", "Row1-Column3" },
				{ "Row2-Column1", "Row2-Column2", "Row2-Column3" } };
		Object sampleColumns[] = { "Column One", "Column Two", "Column Three" };

		list = new JList(sampleTab³es);
		ListSelectionModel lsm = list.getSelectionModel();
		lsm.setSelectionMode(lsm.SINGLE_SELECTION);
		// lsm.addListSelectionListener(controller.getListListener());
		JScrollPane scrollList = new JScrollPane(list);

		dtm = new DefaultTableModel(sampleData, sampleColumns);
		table = new JTable(dtm);
		tcm = table.getColumnModel();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(false);
		table.setCellSelectionEnabled(true);
		table.getTableHeader().setReorderingAllowed(false);
		JScrollPane scrollTable = new JScrollPane(table);

		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		JMenu menuFile = new JMenu("File");
		menuBar.add(menuFile);

		JMenuItem mntmNewMenuItem = new JMenuItem("New menu item");
		menuFile.add(mntmNewMenuItem);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("New menu item");
		menuFile.add(mntmNewMenuItem_1);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("New menu item");
		menuFile.add(mntmNewMenuItem_2);

		JMenu menuEdit = new JMenu("Edit");
		menuBar.add(menuEdit);

		JMenu menuHelp = new JMenu("Help");
		menuBar.add(menuHelp);

		editBtn = new JButton("Edit");
		editBtn.setActionCommand("edit");
		panelConsole.add(editBtn);

		newBtn = new JButton("New");
		newBtn.setActionCommand("new");
		panelConsole.add(newBtn);

		cancelBtn = new JButton("Cancel");
		panelConsole.add(cancelBtn);
		cancelBtn.setActionCommand("cancel");

		saveBtn = new JButton("Save");
		saveBtn.setActionCommand("save");
		panelConsole.add(saveBtn);

		editTextField = new JTextField();
		panelConsole.add(editTextField);
		editTextField.setColumns(10);
		editTextField.setActionCommand("textField");

		// ADDING SCROLLPANE TO PANEL , EXTENDING NOT WORKING
		// panelTable.add(scrollTable);
		// panelTable.add(panelEditNew);
		// panelTable.add(panelCancelSave);

		BorderLayout mgr = new BorderLayout();
		mgr.setVgap(15);
		panelMain.setLayout(mgr);
		panelMain.add(scrollTable, BorderLayout.CENTER);
		// panelMain.add(panelTable,BorderLayout.CENTER);
		panelMain.add(scrollList, BorderLayout.WEST);
		panelMain.add(panelConsole, BorderLayout.PAGE_END);

		cBox = new JComboBox();
		cBox.setActionCommand("cBox");
		panelConsole.add(cBox);

		this.setContentPane(panelMain);
		this.setSize(800, 600);

	}

	public Set<Object> getColumnRecords() {
		Set<Object> list = new HashSet<>();
		int column = table.getSelectedColumn();

		for (int i = 0; i < table.getRowCount(); i++) {
			list.add(this.getValueAt(i, column).toString());
		}

		return list;
	}

	public void setBtnListeners(ActionListener listener) {

		editBtn.addActionListener(listener);
		saveBtn.addActionListener(listener);
		cancelBtn.addActionListener(listener);
		newBtn.addActionListener(listener);
		editTextField.addActionListener(listener);
		cBox.addActionListener(listener);
	}

	public int getSelectedRow() {
		return table.getSelectedRow();
	}

	public int getSelectedColumn() {
		return table.getSelectedColumn();
	}

	public String getSelectedCellString() {
		return (this.getValueAt(table.getSelectedRow(), table.getSelectedColumn()).toString());
	}

	public void setSelectedCellString(String selectedCellString) {
		this.selectedCellString = selectedCellString;
	}

	public String getSelectedCellColumnName() {
		return table.getColumnName(getSelectedColumn());
	}

	public void setTextField(String s) {
		editTextField.setText(s);
	}

	public void setTextField(Object s) {
		editTextField.setText(s.toString());
	}

	public String getTextField() {
		return editTextField.getText();
	}

	public String getCurrentRecordValue() {
		return currentRecordValue;
	}

	public String getValueAt(int row, int column) {
		Object value = dtm.getValueAt(row, column);
		if (value == null)
			return "";
		else
			return value.toString();
	}

	public void writeCellToText() {

		int row = table.getSelectedRow();
		int column = table.getSelectedColumn();

		if (row < 0 || column < 0) {
			System.out.println("ROWS COLS SELECTED........" + row + " " + column + "...<0.....EXITING");
			return;
		}

		int id_ = getIdNumber(row);
		System.out.println("ROWS COLS SELECTED........" + row + " " + column);
		editTextField.setText(this.getValueAt(row, column));
		this.selectTextField();
	}
	
	public void selectTextField(){
		editTextField.grabFocus();
		editTextField.selectAll();
	}

	private int getIdNumber(int row) {

		return 0;
	}

	public void setTableListener(ListSelectionListener tableListener) {
		table.getColumnModel().getSelectionModel().addListSelectionListener(tableListener);
		table.getSelectionModel().addListSelectionListener(tableListener);

	}

	public void writeTextToCell() {
		String text = editTextField.getText();
		dtm.setValueAt(text, table.getSelectedRow(), table.getSelectedColumn());
		editTextField.setText("");
		dtm.fireTableDataChanged();
	}

	public void populateCbox(Iterable<Object> columnRecords) {
		for (Object o : columnRecords) {
			cBox.addItem(o);
		}

	}

	public void clearCbox() {
		cBox.removeAllItems();
	}

	public void turnOffTableSelection() {
		table.setRowSelectionAllowed(false);
		table.setColumnSelectionAllowed(false);
		table.setEnabled(false);
	}

	public void turnOnTableSelection() {
		table.setRowSelectionAllowed(true);
		table.setColumnSelectionAllowed(true);
		table.setEnabled(true);
	}
}
