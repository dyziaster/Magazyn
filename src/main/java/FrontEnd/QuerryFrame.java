package FrontEnd;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Model.Model;
import Model.Utils;

public class QuerryFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -257577428894780457L;
	private static final int SIZE_CONSTANT = 2;
	private Model model;
	private List<String> listQuerrys;
	private List<String> listObject;
	private List<String> listNames;

	private String sqlButton = "";
	private String sqlMenu = "";
	private JPanel panel;

	private JMenu menuFile;
	private JMenuBar menuBar;
	private DefaultTableModel dtm;
	private JTable table;
	private String frameName = "";

	public QuerryFrame() {
	}


	public QuerryFrame(Model model, List<String> listObject, List<String> listNames, List<String> listQuerrys) {

		this(model,listObject,listNames,listQuerrys,"");

	}
	
	public QuerryFrame(Model model, List<String> listObject, List<String> listNames, List<String> listQuerrys, String frameName) {

		this.model = model;
		this.listQuerrys = listQuerrys;
		this.listObject = listObject;
		this.listNames = listNames;
		this.frameName = frameName;

		init();

	}

	private void init() {

//		this.setModal(true);
//		this.setAlwaysOnTop(true);
		this.setTitle(frameName);
		panel = new JPanel();
		dtm = new DefaultTableModel();
		table = new JTable(dtm);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(false);
		table.setCellSelectionEnabled(true);

		table.setRowSelectionAllowed(false);
		table.setColumnSelectionAllowed(false);
		table.setEnabled(false);

		table.getTableHeader().setReorderingAllowed(false);
		JScrollPane scrollTable = new JScrollPane(table);

		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		menuFile = new JMenu("menu");
		menuBar.add(menuFile);

		for (int i = 0; i < listObject.size(); i++) {

			String object = listObject.get(i);
			String sql = listQuerrys.get(i);
			String name = listNames.get(i);

			switch (object) {
			case "btn":
				createButton(name, sql);

				break;
			case "menu":
				createMenu(name, sql);

				break;
			default:
				break;
			}

		}

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setMinimumSize(new Dimension(screenSize.width / SIZE_CONSTANT, screenSize.height / SIZE_CONSTANT));

		this.setLayout(new BorderLayout());
		this.getContentPane().setLayout(new BorderLayout());
		;
		this.getContentPane().add(panel, BorderLayout.PAGE_START);
		this.getContentPane().add(scrollTable, BorderLayout.CENTER);

		setVisible(true);

	}

	private void createMenu(String name, String sql) {
		JMenuItem mi = new JCheckBoxMenuItem(name);
		mi.setActionCommand(sql);
		mi.addActionListener(new SqlListener());
		menuFile.add(mi);

	}

	private void createButton(String name, String sql) {
		JButton btn = new JButton(name);
		btn.setActionCommand(sql);
		btn.addActionListener(new SqlListener());
		panel.add(btn);
	}

	private void unSelectAll() {
		for (int i = 0; i < menuFile.getItemCount(); i++) {
			menuFile.getItem(i).setSelected(false);
		}
	}

	private void selectItem(JCheckBoxMenuItem item) {
		item.setSelected(true);
	}

	class SqlListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ResultSet rs = null;
			Object o = e.getSource();

			if (o instanceof JButton) {
				sqlButton = ((JButton) o).getActionCommand();
				rs = model.executeQuerry(sqlButton + " " + sqlMenu);

			} else if (o instanceof JMenuItem) {
				unSelectAll();
				selectItem((JCheckBoxMenuItem) o);
				sqlMenu = ((JMenuItem) o).getActionCommand();
				rs = model.executeQuerry(sqlButton + " " + sqlMenu);

			}

			table.setModel(Utils.getTableModelFromRS(rs));
			Utils.adjustColumnsOf(table);
			((DefaultTableModel) table.getModel()).fireTableDataChanged();

		}
	}

}
