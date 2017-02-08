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
	public static final String DATABASE = "cds_michal";
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

		Logger.i("CONNECTING..............");
		boolean connected = false;

		try {
			Class.forName(JDBC_DRIVER);

			// STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			connected = true;
			System.out.println("Connected database successfully...");

			Logger.i("CONNECTED..............");

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

	public void executeUpdate(String query) {
		Logger.i(Logger.getMethodName(), query);
		Statement stmt = null;
		System.out.println("Creating statement2...");
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			Logger.e(Logger.getMethodName(), e.getMessage());
		}

	}

	public ResultSet executeQuery(String query) {
		Logger.i(Logger.getMethodName(), query);
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("MODEL.QUERYDATABASE " + e.getMessage());
		}
		return rs;
	}

	public List<String> getTableNamesList() {
		String querry = "SELECT table_name FROM information_schema.tables where table_schema='" + DATABASE + "'";
		ResultSet rs = executeQuery(querry);
		if (rs == null)
			System.out.println("RS IS NULL ....");
		return this.getTableNamesFromRS(rs);
	}

	public void setLastSelectedTable(String tableName) {
		lastSelectedTable = tableName;
	}

	public String getLastSelectedTable() {
		return lastSelectedTable;
	}

	public int getIdnumber(int row) {
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
		return Integer.valueOf((currentTableModel.getValueAt(row, column).toString()));
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

	public String getSqlValuesStringFromList(List<String> list, String tableName, List<String> list2) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO " + tableName + " (");

		for (String s : list2) {
			sb.append("" + s.toString() + ",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(") VALUES (");

		for (String s : list) {
			sb.append("'" + s + "',");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(");");
		return sb.toString();
	}

	public DefaultTableModel getTableModelFromRS(ResultSet rs) {

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		currentTableModel = new DefaultTableModel(data, columnNames);
		this.setColumnNames(columnNames);
		return currentTableModel;

	}

	public List<String> getTableNamesFromRS(ResultSet rs) {

		ResultSetMetaData metaData = null;
		Vector<String> vector = new Vector<String>();
		try {
			metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
					vector.add((rs.getObject(columnIndex)).toString());
				}
			}
		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vector;

	}

	public Map<String,String> getForeignKeysOf(String tableName) {
		ResultSetMetaData rsmetaData;
		DatabaseMetaData metaData;
		Vector<String> vector = new Vector<String>();
		Map<String,String> map  = new HashMap<>();
		try {
			metaData = conn.getMetaData();
			ResultSet foreignKeys = metaData.getImportedKeys(conn.getCatalog(), null, tableName);
			rsmetaData = foreignKeys.getMetaData();
			int columnCount = rsmetaData.getColumnCount();
			while (foreignKeys.next()) {
				map.put( foreignKeys.getString("FKCOLUMN_NAME"),foreignKeys.getString("PKTABLE_NAME"));
//				    for (int i = 1; i <= columnCount; i++) {
//				        if (i > 1) System.out.print(",  ");
//				        String columnValue = foreignKeys.getString(i);
//				        System.out.print(columnValue + " " + rsmetaData.getColumnName(i));
//				    }
//				    System.out.println("");
				
//				}
			}
			
			
		} catch (SQLException e) {
			Logger.e(Logger.getMethodName(), e.getMessage());
		}
		
		Logger.i(vector);
		return map;
	}

	public List<String> getColumnNamesWithoutID() {
		List<String> list = this.getColumnNames();
		if(list.get(0).contains("id_"))  // Removing id table from adding to it
			list.remove(0);
		return list;
	}
}
