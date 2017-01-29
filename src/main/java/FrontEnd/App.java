package FrontEnd;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Controller.Controller;
import Controller.ListListener;

import javax.swing.JList;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class App extends JFrame {
	private static App window;
	private JList list;
	private JTable table;
	private Controller controller;
	private JLabel lbl;
	private DefaultTableModel dtm;
	private JTextField textField;
	
	public void setTextListener(ActionListener a){
		textField.addActionListener(a);
	}
	
	public void setLabelText(String text){
		lbl.setText(text);
	}

	public void setListListener(ListSelectionListener lsm) {
		list.getSelectionModel().addListSelectionListener(lsm);
	}

	public void setController(Controller c) {
		controller = c;
	}

	public void setTableData(Object[][] data, Object[] columns) {

		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();


		 String dataa[][] = { { "Row1-asdasColumn1", "Row1-Column2", "Row1-Column3" },
				{ "Row2-Column1", "Row2-Column2", "Row2-Column3" } };
		 String columnss[] = { "Column asdasOne", "Column Two", "Column Three" };
		
		tableModel.setDataVector(data, columns);
		tableModel.fireTableDataChanged();
		
		// repaint
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
		setBounds(100, 100, 506, 378);

		JPanel panel = new JPanel();
		this.getContentPane().add(panel, BorderLayout.CENTER);
		String sampleTab³es[] = { "tab1", "tab2", "tab3" };
		panel.setLayout(null);

		Object sampleData[][] = { { "Row1-Column1", "Row1-Column2", "Row1-Column3" },
				{ "Row2-Column1", "Row2-Column2", "Row2-Column3" } };
		Object sampleColumns[] = { "Column One", "Column Two", "Column Three" };

		list = new JList(sampleTab³es);
		ListSelectionModel lsm = list.getSelectionModel();
		lsm.setSelectionMode(lsm.SINGLE_SELECTION);
		// lsm.addListSelectionListener(controller.getListListener());
		JScrollPane scrollList = new JScrollPane(list);
		scrollList.setBounds(1, 0, 100, 241);
		panel.add(scrollList);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(136, 0, 250, 241);
		panel.add(tabbedPane);
		dtm = new DefaultTableModel(sampleData, sampleColumns);
		table = new JTable(dtm);
		JScrollPane scrollTable = new JScrollPane(table);
		tabbedPane.addTab("New tab", null, scrollTable, null);
		
		lbl = new JLabel("New label");
		lbl.setBounds(388, 75, 46, 26);
		panel.add(lbl);
		
		textField = new JTextField();
		textField.setBounds(136, 264, 250, 20);
		textField.setActionCommand("text");
		panel.add(textField);
		textField.setColumns(10);

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
	}
}
