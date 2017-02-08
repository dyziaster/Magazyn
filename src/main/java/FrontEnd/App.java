package FrontEnd;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Component;
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
import javax.swing.text.DefaultCaret;

import Controller.Controller;
import Controller.ListListener;
import Model.Utils;

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
	private JPanel panelMain;
	private JPanel panelTable;
	private JPanel panelBottom;
	private JPanel panelButtons;
	private JTextField editTextField;
    private JTextArea output;
    
	private String selectedCellString;
	private JButton editBtn;
	private JButton newBtn;
	private JButton cancelBtn;
	private JButton saveBtn;
	
	private String currentRecordValue = "";
	private JComboBox cBox;

	private DefaultTableModel dtm;
	private TableColumnModel tcm;

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

		DefaultTableModel tableModel = (DefaultTableModel) table.getModel(); /////////// can
																				/////////// it
																				/////////// go
																				/////////// with
																				/////////// this.getvalueat
																				/////////// ???

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

	public void setTableData(DefaultTableModel model) {

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
		
		((DefaultTableModel)table.getModel()).fireTableDataChanged();
	}

	public void setTableList(Object[] list) {
		this.list.setListData(list);
	}
	public void setTableList(List<String> list) {
		this.list.setListData(list.toArray());
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
		panelBottom = new JPanel();
		panelButtons = new JPanel();
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

		BoxLayout bl = new BoxLayout(panelButtons, BoxLayout.Y_AXIS);
		panelButtons.setLayout(bl);

		newBtn = new JButton("New");
		newBtn.setActionCommand(Utils.COMMAND_NEW);
		panelButtons.add(newBtn);

		editBtn = new JButton("Edit");
		editBtn.setActionCommand(Utils.COMMAND_EDIT);
		panelButtons.add(editBtn);

		cancelBtn = new JButton("Cancel");
		panelButtons.add(cancelBtn);
		cancelBtn.setActionCommand(Utils.COMMAND_CANCEL);

		saveBtn = new JButton("Save");
		saveBtn.setActionCommand(Utils.COMMAND_SAVE);
		panelButtons.add(saveBtn);

		editTextField = new JTextField();
		panelButtons.add(editTextField);
		//editTextField.setColumns(10);
		editTextField.setMaximumSize( 
			     new Dimension(Integer.MAX_VALUE, editTextField.getPreferredSize().height) );
		editTextField.setActionCommand("textField");
		

		cBox = new JComboBox();
		cBox.setActionCommand("cBox");
		cBox.setMaximumSize( 
			     new Dimension(Integer.MAX_VALUE, cBox.getPreferredSize().height) );
		panelButtons.add(cBox);
		

        output = new JTextArea();
        output.setColumns(10);
        output.setRows(10);
//        output.setLineWrap(true);
        output.setWrapStyleWord(true);
        DefaultCaret carret = (DefaultCaret)output.getCaret();
        carret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        
        panelBottom.setLayout(new BorderLayout());
        JScrollPane scrollJTextArea = new JScrollPane(output);
        scrollJTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        panelBottom.add(scrollJTextArea,BorderLayout.PAGE_END);

		BorderLayout mgr = new BorderLayout();
		mgr.setVgap(15);
		panelMain.setLayout(mgr);
		panelMain.add(scrollTable, BorderLayout.CENTER);
		// panelMain.add(panelTable,BorderLayout.CENTER);
		panelMain.add(scrollList, BorderLayout.WEST);
		panelMain.add(panelBottom, BorderLayout.PAGE_END);
		panelMain.add(panelButtons, BorderLayout.EAST);


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

	public void buttonToggle(String button) {

		Component[] components = panelButtons.getComponents();
		for (Component c : components) {
			if (c instanceof JButton) {
				JButton jb = (JButton) c;
				if (button.equals(jb.getActionCommand())) {
					if (jb.isEnabled())
						jb.setEnabled(false);
					else
						jb.setEnabled(true);
				}
			}
		}
	}

	public void buttonEnable(String button) {

		Component[] components = panelButtons.getComponents();
		for (Component c : components) {
			if (c instanceof JButton) {
				JButton jb = (JButton) c;
				if (button.equals(jb.getActionCommand())) {
					jb.setEnabled(true);
				}
			}
		}
	}

	public void buttonDisable(String button) {

		Component[] components = panelButtons.getComponents();
		for (Component c : components) {
			if (c instanceof JButton) {
				JButton jb = (JButton) c;
				if (button.equals(jb.getActionCommand())) {
					jb.setEnabled(false);
				}
			}
		}
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
		Object value = table.getModel().getValueAt(row, column);
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

	public void selectTextField() {
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

		DefaultTableModel dtm = (DefaultTableModel)table.getModel();
		
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

	public JTextArea getAppender() {
		return output;
	}
}
