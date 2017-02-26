package Model;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;


import javax.swing.table.DefaultTableModel;



public class Model {

	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DATABASE = "cds_michal";
	private static final String TIMEZONE = "?serverTimezone=UTC";
	private static final String MULTIPLEQUERY = "allowMultiQueries=true";
	private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/" + DATABASE + TIMEZONE + "&" + MULTIPLEQUERY;
	// Database credentials
	private static final String USER = "DAREK";
	private static final String PASS = "krowy";
	private DefaultTableModel currentTableModel;
	private List<String> tableNames;
	private Connection conn = null;
	private String lastSelectedTable;
	private List<String> columnNames;
	private String idString;

	
	
	public DefaultTableModel getCurrentTableModel() {
		return currentTableModel;
	}

	public void setCurrentTableModel(DefaultTableModel currentTableModel) {
		this.currentTableModel = currentTableModel;
	}

	public List<String> getColumnNames() {
		return this.columnNames;
	}

	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}

	public boolean connectToDatabase() {

		Logger.i(Logger.getMethodName(),"Connecting to a selected database...");
		boolean connected = false;

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			connected = true;
			Logger.i(Logger.getMethodName(),"Connected database successfully...");

		} catch (ClassNotFoundException e) {
			Logger.e(Logger.getMethodName(), e.getMessage());
			e.printStackTrace();
		} catch (SQLException ee) {
			Logger.e(Logger.getMethodName(), ee.getMessage());
			ee.printStackTrace();
		}
		// finally{
		// conn.close();
		// }
		return connected;

	}

	public void executeUpdate(String querry) {
		Logger.i(Logger.getMethodName(), querry);
		Statement stmt = null;
		System.out.println("Creating statement2...");
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(querry);
		} catch (SQLException e) {
			Logger.e(Logger.getMethodName(), e.getMessage());
		}

	}

	public ResultSet executeQuerry(String querry) {
		Logger.i(Logger.getMethodName(), querry);
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(querry);
		} catch (SQLException e) {
			Logger.e(Logger.getMethodName(), e.getMessage());
		}
		return rs;
	}

	public List<String> getTableNamesList() { // returns tables names from database
		String querry = "SELECT table_name FROM information_schema.tables where table_schema='" + DATABASE + "'";
		ResultSet rs = executeQuerry(querry);
		if (rs == null)
			Logger.e(Logger.getMethodName(), "tableNames are null sorry"+querry);
		return Utils.getTableNamesFromRS(rs);
	}

	public void setLastSelectedTable(String tableName) {
		lastSelectedTable = tableName;
	}

	public String getLastSelectedTable() {
		return lastSelectedTable;
	}

	public int getIdnumber(int row) { // used to update record based on its ID
		// add functionality that handles no ID column.
		int column = 0;
		for (Object s : this.getColumnNames()) { // uses column names
			System.out.println("SEARCHING ID FROM STRING ................." + s);
			if ((s.toString()).substring(0, 3).equals("id_")) {
				idString = s.toString();
				break;
			} else {
				column++;
			}
		}
		String idValue = currentTableModel.getValueAt(row, column).toString();
		return Integer.valueOf(idValue);
	}

	public String getIdString() {

		return idString;
	}


	public Map<String,String> getForeignKeysOf(String tableName) { // must be in model
		DatabaseMetaData metaData;
		Vector<String> vector = new Vector<String>();
		Map<String,String> map  = new HashMap<>();
		try {
			metaData = conn.getMetaData();
			ResultSet foreignKeys = metaData.getImportedKeys(conn.getCatalog(), null, tableName);
			while (foreignKeys.next()) {
				map.put( foreignKeys.getString("FKCOLUMN_NAME"),foreignKeys.getString("PKTABLE_NAME"));
			}
			
		} catch (SQLException e) {
			Logger.e(Logger.getMethodName(), e.getMessage());
		}
		
		Logger.i(Logger.getMethodName(),vector+"list of foreign keys in table");
		return map;
	}
	
	public List<String> getColumnListFrom(String table) { // uses columnNames
		ResultSet rs = this.executeQuerry("SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME =N'"+table+"';");
		return Utils.getNthColumnRecordsFrom(rs, 4);
	}

	public List<String> getColumnNamesWithoutID() { // uses columnNames
		List<String> list = new ArrayList<>();
		list.addAll(this.getColumnNames());
		if(list.get(0).contains("id_"))  // Removing id table from adding to it
			list.remove(0);
		return list;
	}


}
