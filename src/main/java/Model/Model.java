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

public class Model {

	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String DATABASE = "cds_michal";
	private static final String TIMEZONE = "?serverTimezone=UTC";
	private static final String MULTIPLEQUERY = "allowMultiQueries=true";
	private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/" + DATABASE + TIMEZONE+"&" + MULTIPLEQUERY;

	// Database credentials
	private static final String USER = "michal";
	private static final String PASS = "dyzio";
	private static Object[][] currentTableModel;

	private List<String> tableNames;

	private Connection conn = null;
	private String lastSelectedTable;
	private Object[] columnNames;
	private String idString;

	public Object[] getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(Object[] columnNames) {
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
//		finally{
//			conn.close();
//		}

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

	public Object[] getTableNames() throws SQLException {
		String querry = "SELECT table_name FROM information_schema.tables where table_schema='" + DATABASE + "'";
		System.out.println("STATEMENT = " + querry);
		ResultSet rs = executeQuery(querry);
		if (rs == null)
			System.out.println("RS IS NULL ....");
		tableNames = tableNamesFromMap(resultSetToArrayList(rs));
		return tableNames.toArray();
	}

	public List<String> getTableNamesList() {
		return tableNames;
	}

	public static Object[][] getDataFromListMap(List<HashMap<String, Object>> list) {
		Object data[][] = new Object[list.size()][];
		for (int i = 0; i < list.size(); i++) {
			data[i] = list.get(i).values().toArray();
		}
		currentTableModel = data;
		return data;
	}

	public static Object[] getColumnNamesFromListMap(List<HashMap<String, Object>> list) {
		return (list.get(0)).keySet().toArray();
	}

	public static List<HashMap<String, Object>> resultSetToArrayList(ResultSet rs) {
		ResultSetMetaData md = null;
		List<HashMap<String, Object>> list = null;
		try {
			md = rs.getMetaData();
			int columns = md.getColumnCount();
			list = new ArrayList<>(50);
			while (rs.next()) {
				HashMap<String, Object> row = new HashMap<>(columns);
				for (int i = 1; i <= columns; ++i) {
					row.put(md.getColumnName(i), rs.getObject(i));
				}
				list.add(row);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	public void setLastSelectedTable(String tableName) {
		lastSelectedTable = tableName;
	}

	public String getLastSelectedTable() {
		return lastSelectedTable;
	}

	public int getIdnumber(int selectedRow) {
		int idPosition = 0;
		for(Object s : columnNames){
			System.out.println("SEARCHING ID FROM STRING ................."+s);
			if((s.toString()).substring(0, 3).equals("id_")){
				idString = s.toString();
				break;
			}
			else{
				idPosition++;
			}
		}
		return Integer.valueOf((currentTableModel[selectedRow][idPosition].toString()));
	}

	public String getIdString() {
		
		return idString;
	}
	
	public String getSqlValuesStringFromList(List<String> list, String tableName){
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO "+tableName+" VALUES (");
		for(String s : list){
			sb.append("'"+s+"',");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(");");
		return sb.toString();
	}

	public String getSqlValuesStringFromList(List<String> list, String tableName, Object[] columnNames2) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO "+tableName+" (");
		
		for(Object s : columnNames2){
			sb.append(""+s.toString()+",");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(") VALUES (");
		
		for(String s : list){
			sb.append("'"+s+"',");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(");");
		return sb.toString();
	}
	
	

}
