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
	private static DefaultTableModel currentTableModel;

	private List<String> tableNames;

	private Connection conn = null;
	private String lastSelectedTable;
	private List<String> columnNames;
	private String idString;

	public List<String> getColumnNames() {
		return columnNames;
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

	public List<String> getTableNamesList() {
		String querry = "SELECT table_name FROM information_schema.tables where table_schema='" + DATABASE + "'";
		ResultSet rs = executeQuerry(querry);
		if (rs == null)
			Logger.e(Logger.getMethodName(), "result set returns null from "+querry);
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
		for (Object s : columnNames) {
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

	public String getSqlValuesStringFromList(List<String> list, String tableName) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO " + tableName + " VALUES (");
		for (String s : list) {
			sb.append("'" + s + "',");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(");");
		return sb.toString();
	}



	public DefaultTableModel getTableModelFromRS(ResultSet rs) {
		Logger.i(Logger.getMethodName(),"creating table model for View");
		ResultSetMetaData metaData;
		Vector<String> columnNames = null;
		Vector<Vector<Object>> data = null;
		try {
			metaData = rs.getMetaData();
			// names of columns
			columnNames = new Vector<String>();
			int columnCount = metaData.getColumnCount();
			for (int column = 1; column <= columnCount; column++) {
				columnNames.add(metaData.getColumnName(column));
			}

			// data of the table
			data = new Vector<Vector<Object>>();
			while (rs.next()) {
				Vector<Object> vector = new Vector<Object>();
				for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
					vector.add(rs.getObject(columnIndex));
				}
				data.add(vector);

			}

		} catch (SQLException e) {
			Logger.e(Logger.getMethodName(), e.getMessage());
		}
		this.setColumnNames(columnNames);
		currentTableModel = new DefaultTableModel(data, columnNames);
		return currentTableModel;

	}



	public Map<String,String> getForeignKeysOf(String tableName) {
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

	public List<String> getColumnNamesWithoutID() {
		List<String> list = new ArrayList<>();
		list.addAll(this.getColumnNames());
		if(list.get(0).contains("id_"))  // Removing id table from adding to it
			list.remove(0);
		return list;
	}

	public List<String> getMenuItems() {
		
		ResultSet rs = this.executeQuerry("select form_name from cds_michal.t_form_cfg;");
		List<String> list = Utils.getNthColumnRecordsFrom(rs, 1);
		
		return list;
	}
}
