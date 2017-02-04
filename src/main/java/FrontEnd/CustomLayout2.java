package FrontEnd;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import Controller.ListListener;
public class CustomLayout2 {

	public static List<HashMap<String, Object>> resultSetToArrayList(ResultSet rs) throws SQLException {
		ResultSetMetaData md = rs.getMetaData();
		int columns = md.getColumnCount();
		List<HashMap<String, Object>> list = new ArrayList<>(50);
		while (rs.next()) {
			HashMap<String, Object> row = new HashMap<>(columns);
			for (int i = 1; i <= columns; ++i) {
				row.put(md.getColumnName(i), rs.getObject(i));
			}
			list.add(row);
		}

		return list;
	}

	public static List<String> tableNamesFromMap(List<HashMap<String, Object>> list) {

		List<String> tablenames = new ArrayList<>();
		
		for (HashMap<String, Object> m : list) {
			String s = m.values().toString();
			tablenames.add(s.substring(1, s.length() - 1));

		}

		return tablenames;
	}

	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:sqlite:test.db";

	// Database credentials
	static final String USER = "username";
	static final String PASS = "password";

	public static void main(String args[]) throws SQLException, ClassNotFoundException {

		Connection conn = null;
		Statement stmt = null;

		Object rowData[][] = { { "Row1-Column1", "Row1-Column2", "Row1-Column3" },
				{ "Row2-Column1", "Row2-Column2", "Row2-Column3" } };
		Object columnNames[] = { "Column One", "Column Two", "Column Three" };

		Class.forName(JDBC_DRIVER);

		// STEP 3: Open a connection
		System.out.println("Connecting to a selected database...");
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		System.out.println("Connected database successfully...");

		// STEP 4: Execute a query
		System.out.println("Creating statement...");
		stmt = conn.createStatement();

		String sql = "SELECT name FROM sqlite_master WHERE type = 'table'";
		ResultSet rs = stmt.executeQuery(sql);
		// STEP 5: Extract data from result set
		List<HashMap<String, Object>> map = CustomLayout2.resultSetToArrayList(rs);
		

		

		rs.close();

		// JTable table = new JTable(data, Account.getColumns());

		// ==============================================================
		String labels[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
		List<String> tables = new ArrayList<String>();

		String title = "JList Sample";
		JFrame f = new JFrame(title);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JList list = new JList(CustomLayout2.tableNamesFromMap(map).toArray());
		//add listener
		ListSelectionModel lsm=list.getSelectionModel(); 
		lsm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lsm.addListSelectionListener(new ListListener(null,null));
		
		JScrollPane scrollPane = new JScrollPane(list);
		// ===========================================================
		JTabbedPane tabbedPane = new JTabbedPane();

		// Create the "cards".
		JPanel card1 = new JPanel();
		card1.add(new JButton("Button 1"));
		card1.add(new JButton("Button 2"));
		card1.add(new JButton("Button 3"));

		JScrollPane card2 = new JScrollPane(null);

		tabbedPane.addTab("Asdsadas", card1);
		tabbedPane.addTab("Asdasdasdsa", card2);
		// ===========================================================

		JPanel pane = new JPanel();
		pane.add(scrollPane);
		pane.add(tabbedPane);

		// ==========================================================

		// ===========================================================
		Container contentPane = f.getContentPane();
		contentPane.add(pane, BorderLayout.CENTER);

		f.setSize(800, 500);
		f.setVisible(true);
	}

}
