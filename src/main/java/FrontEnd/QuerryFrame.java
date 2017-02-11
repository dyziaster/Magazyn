package FrontEnd;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import Model.Model;
import Model.Utils;

public class QuerryFrame extends JDialog {

	private static final int SIZE_CONSTANT = 2;
	private Model model;
	private List<String> listButtons;
	private List<String> listMenu;
	private List<String> listQuerrys;
	private List<String> listObject;
	private List<String> listNames;

	private String sqlButton = "";
	private String sqlMenu = "";  
	private JPanel panel ;
	
	JMenu menuFile;
	private DefaultTableModel dtm;
	private JTable table;
	private TableColumnModel tcm;
	
	public QuerryFrame() {
		
		this(null,Arrays.asList(new String[]{"btn","btn","menu"}),Arrays.asList(new String[]{"btn","btn","menu"}),Arrays.asList(new String[]{"1dd","12dd","3dd"}));

	}
	
	public QuerryFrame(Model model,String listObject, String listNames, String listQuerrys) {

		this.model = model;
		this.listQuerrys = new ArrayList<>();
		this.listObject = new ArrayList<>();
		this.listNames = new ArrayList<>();
		
		this.listQuerrys.add(listQuerrys);
		this.listObject.add(listObject);
		this.listNames.add(listNames);
	
		init();

		
	}

	public QuerryFrame(Model model,List<String> listObject, List<String> listNames, List<String> listQuerrys) {

		this.model = model;
		this.listQuerrys = listQuerrys;
		this.listObject = listObject;
		this.listNames = listNames;
		
		init();

	}

	private void init(){
		

		this.setModal(true);
		this.setAlwaysOnTop(true);
		
		panel =  new JPanel();
		dtm = new DefaultTableModel();
		table = new JTable(dtm);
		tcm = table.getColumnModel();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(false);
		table.setCellSelectionEnabled(true);
		

		table.setRowSelectionAllowed(false);
		table.setColumnSelectionAllowed(false);
		table.setEnabled(false);
		
		table.getTableHeader().setReorderingAllowed(false);
		JScrollPane scrollTable = new JScrollPane(table);

		
		
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		menuFile = new JMenu("menu");
		menuBar.add(menuFile);
		
		
		for(int i =0; i<listObject.size();i++){
			
			String object = listObject.get(i);
			String sql = listQuerrys.get(i);
			String name = listNames.get(i);
			
			switch(object){
			case "btn":
				createButton(name,sql);
				
				
				break;
			case "menu":
				createMenu(name,sql);
				
				break;
			default:
				break;
			}
			
			
		}

		
		
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setMinimumSize(new Dimension(screenSize.width / SIZE_CONSTANT, screenSize.height / SIZE_CONSTANT));
		
		this.setLayout(new BorderLayout());
		this.getContentPane().setLayout(new BorderLayout());;
		this.getContentPane().add(panel,BorderLayout.PAGE_START);
		this.getContentPane().add(scrollTable,BorderLayout.CENTER);

		setVisible(true);
		
	}

	private void createMenu(String name, String sql) {
		JMenuItem mi = new JMenuItem(name);
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

	
	class SqlListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			ResultSet rs=null;
			Object o = e.getSource();
			
			if(o instanceof JButton){
				sqlButton = ((JButton) o).getActionCommand();
				if(sqlMenu.equals(""))
					 rs = model.executeQuerry(sqlButton);
				else
					 rs = model.executeQuerry(sqlButton+" "+sqlMenu);	
				
				table.setModel(Utils.getTableModelFromRS(rs));
				Utils.adjustColumnsOf(table);
				((DefaultTableModel)table.getModel()).fireTableDataChanged();
				
				
			}
			else if(o instanceof JMenuItem){
				sqlMenu = ((JMenuItem) o).getActionCommand();
			}
			
			System.out.println("querry= "+ sqlButton+sqlMenu);
		}
	}



}

