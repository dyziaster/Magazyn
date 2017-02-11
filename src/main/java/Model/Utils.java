package Model;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class Utils {
	

	public static final String COMMAND_EDIT = "edit";
	public static final String COMMAND_SAVE = "save";
	public static final String COMMAND_CANCEL = "cancel";
	public static final String COMMAND_NEW = "new";

	public static final String t_cfg_doc = "";
	public static final String t_doc = "";
	public static final String t_glazura = "";
	public static final String t_kontrahent = "";
	public static final String t_magazyn = "";
	public static final String t_narzedzie = "";
	public static final String t_nazwa_lac = "";
	public static final String t_nazwa_tab = "";
	public static final String t_obszar = "";
	public static final String t_panstwo = "panstwo";
	public static final String t_rejon_pol = "";
	public static final String t_rodzaj_doc = "";
	public static final String t_rozmiar = "";
	public static final String t_towary = "";
	public static final String t_typ_doc = "";
	public static final String t_typ_kontrahent = "";
	public static final String COMMAND_TEXT = "text";

	public static void printResultSet(ResultSet resultSet) {

		ResultSetMetaData rsmd;
		try {
			rsmd = resultSet.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (resultSet.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					if (i > 1)
						System.out.print(",  ");
					String columnValue = resultSet.getString(i);
					System.out.print(columnValue + " " + rsmd.getColumnName(i));
				}
				System.out.println("");
			}
			resultSet.beforeFirst();
		} catch (SQLException e) {
			Logger.e(Logger.getMethodName(), e.getMessage());
		}
	}

	public static List<String> getNthColumnRecordsFrom(ResultSet resultSet, int column) {
		List<String> list = new ArrayList<>();
		try {
			ResultSetMetaData rsmd = resultSet.getMetaData();
			//int columnsNumber = rsmd.getColumnCount();
			while (resultSet.next()) {
				System.out.println(resultSet.getString(column));
				list.add(resultSet.getString(column));

			}
			
			resultSet.beforeFirst();

		} catch (SQLException e) {
			Logger.e(Logger.getMethodName(), e.getMessage());
		}

		System.out.println(list);
		return list;
	}
	
	public static Map<String, Integer> getIdNameMapFrom(ResultSet resultSet) {
		Map<String, Integer> map = new HashMap<>();
		try {
			ResultSetMetaData rsmd = resultSet.getMetaData();
			//int columnsNumber = rsmd.getColumnCount();
			while (resultSet.next()) {
				map.put(resultSet.getString(2),resultSet.getInt(1));
			}
			
			resultSet.beforeFirst();

		} catch (SQLException e) {
			Logger.e(Logger.getMethodName(), e.getMessage());
		}

		Logger.i(Logger.getMethodName(),map);
		return map;
	}
	
	public static List<String> getTableNamesFromRS(ResultSet rs) {

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
	
	public static String getSqlValuesStringFromList(List<String> list, String tableName, List<String> list2) {
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
	
	public static DefaultTableModel getTableModelFromRS(ResultSet rs) {
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
		System.out.println(columnNames);
		return new DefaultTableModel(data, columnNames);

	}
	
	public static void adjustColumnsOf(JTable table){

		TableColumnModel columnModel = table.getColumnModel();
		
		int lmax = 2;
		for (int i = 0; i < columnModel.getColumnCount(); i++) {
			lmax = table.getColumnName(i).toString().length();
			int lmaxx = 0;
			for (int j = 0; j < table.getRowCount(); j++) {
				Object value = table.getValueAt(j, i);
				if (value != null)
					lmaxx = value.toString().length();
				if (lmaxx > lmax)
					lmax = lmaxx;
			}
			columnModel.getColumn(i).setPreferredWidth(lmax * 8);
		}
		
		((DefaultTableModel)table.getModel()).fireTableDataChanged();
	}

}
