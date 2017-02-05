package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import com.mysql.cj.api.mysqla.result.Resultset;

public class Model {

	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String DATABASE = "cds_michal";
	private static final String TIMEZONE = "?serverTimezone=UTC";
	private static final String MULTIPLEQUERY = "allowMultiQueries=true";
	private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/" + DATABASE + TIMEZONE + "&" + MULTIPLEQUERY;
	// Database credentials
	private static final String USER = "michal";
	private static final String PASS = "dyzio";
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

		boolean connected = false;

		try {
			Class.forName(JDBC_DRIVER);

			// STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			connected = true;
			System.out.println("Connected database successfully...");

		} catch (ClassNotFoundException e) {
			System.out.println("CLASS NOT FOUND EXCEPTION ............");
			e.printStackTrace();
		} catch (SQLException ee) {
			System.out.println("sql EXCEPTION ............");
			ee.printStackTrace();
		}
		// finally{
		// conn.close();
		// }

		return connected;

	}

	public void executeUpdate(String query) {
		Statement stmt = null;
		System.out.println("Creating statement2...");
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println("MODEL.QUERYDATABASE " + e.getMessage());
			e.printStackTrace();
		}

	}

	public ResultSet executeQuery(String query) {
		Statement stmt = null;
		System.out.println("Creating statement...");
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("MODEL.QUERYDATABASE " + e.getMessage());
		}
		// System.out.println("query ok...");

		return rs;
	}

	public List<String> getTableNamesList(){
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

				System.out.println("VALUECHANGED...................." + data);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		currentTableModel = new DefaultTableModel(data, columnNames);
		this.setColumnNames(columnNames);
		return currentTableModel;

	}
	
	public List<String> getTableNamesFromRS(ResultSet rs){
		

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
}
