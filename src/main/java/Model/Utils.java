package Model;

import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JComboBox;
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
	public static final String COMMAND_VIEW = "view";
	public static final String COMMAND_NEWDOC = "newdoc";

	public static void printResultSet(ResultSet resultSet) {
		
		try {
			if (!resultSet.isBeforeFirst()){
				Logger.e(Logger.getMethodName(), " cannot print empty resultset");
				return;
			}
			ResultSetMetaData rsmd;
			rsmd = resultSet.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (resultSet.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					if (i > 1)
						System.out.print(",  ");
					String columnValue = resultSet.getString(i);
					System.out.print(rsmd.getColumnName(i) + " " + columnValue);
				}
				System.out.println("");
			}
			resultSet.beforeFirst();
		} catch (SQLException e) {
			Logger.e(Logger.getMethodName(), e.getMessage());
		}
	}

	public static List<String> getColumnRecordsFrom(ResultSet resultSet, String column) {
		List<String> list = new ArrayList<>();

		try {
			ResultSetMetaData rsmd = resultSet.getMetaData();
			// int columnsNumber = rsmd.getColumnCount();
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

	public static List<String> getNthColumnRecordsFrom(ResultSet resultSet, int column) {
		List<String> list = new ArrayList<>();
		try {
			ResultSetMetaData rsmd = resultSet.getMetaData();
			// int columnsNumber = rsmd.getColumnCount();
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

	public static String getFirstRecordFromRS(ResultSet rs) {
		return getNthColumnRecordsFrom(rs, 1).get(0);
	}

	public static Map<String, Integer> getIdNameMapFrom(ResultSet resultSet) {
		Map<String, Integer> map = new LinkedHashMap<>();
		try {
			ResultSetMetaData rsmd = resultSet.getMetaData();
			// int columnsNumber = rsmd.getColumnCount();
			while (resultSet.next()) {
				map.put(resultSet.getString(2), resultSet.getInt(1));
			}
			resultSet.beforeFirst();

		} catch (SQLException e) {
			Logger.e(Logger.getMethodName(), e.getMessage());
		}

		Logger.i(Logger.getMethodName(), map);
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

	public static String getSqlValuesStringFromList(List<String> values, String tableName, List<String> columns) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO " + tableName + " (");

		for (String s : columns) {
			sb.append("" + s.toString() + ",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(") VALUES (");

		for (String s : values) {
			sb.append("'" + s + "',");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(");");
		return sb.toString();
	}

	public static DefaultTableModel getTableModelFromRS(ResultSet rs) {
		Logger.i(Logger.getMethodName(), "creating table model for View");
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
			rs.beforeFirst();
		} catch (SQLException e) {
			Logger.e(Logger.getMethodName(), e.getMessage());
		}
		System.out.println(columnNames);
		return new MyTModel(data, columnNames);

	}

	public static void adjustColumnsOf(JTable table) {

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

		((DefaultTableModel) table.getModel()).fireTableDataChanged();
	}

	public static String getSqlValuesStringFromList(List<String> list, String tableName) { // parser
																							// for
																							// returned
																							// Strings
																							// from
																							// NewFrame
																							// .
																							// move
																							// to
																							// utils
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO " + tableName + " VALUES (");
		for (String s : list) {
			sb.append("'" + s + "',");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(");");
		return sb.toString();
	}

	public static DefaultTableModel getTableModelFromRS(ResultSet rs, Model model) { // proper
																						// method
		Logger.i(Logger.getMethodName(), "creating table model for View");
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

		DefaultTableModel dtm = new DefaultTableModel(data, columnNames);
		model.setCurrentTableModel(dtm);
		model.setColumnNames(columnNames);

		return dtm;

	}

	public static List<String> getColumnNamesWithoutID(List<String> colums) { // uses
																				// columnNames
		List<String> list = new ArrayList<>();
		list.addAll(colums);
		if (list.get(0).contains("id_")) // Removing id table from adding to it
			list.remove(0);
		return list;
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

	public static void setComboPosition(JComboBox<Object> cb,int position){
		ActionListener al = null;
		if (cb.getActionListeners().length > 0) {
			al = cb.getActionListeners()[0];
			cb.removeActionListener(al);
			cb.setSelectedIndex(position);
			cb.addActionListener(al);
		} else {
			cb.setSelectedIndex(position);
		}
	}
	
	public static void addToComboBox(JComboBox<Object> cb, Iterable<?> list) {
		ActionListener al = null;
		if (cb.getActionListeners().length > 0) {
			al = cb.getActionListeners()[0];
			cb.removeActionListener(al);
			cb.addItem("");
			for (Object o : list) {
				cb.addItem(o);
			}
			cb.addActionListener(al);
		} else {
			cb.addItem("");
			for (Object o : list) {
				cb.addItem(o);
			}
		}
	}
	
	public static void removeAllFromComboBox(JComboBox<Object> cb) {
		ActionListener al = null;
		if (cb.getActionListeners().length > 0) {
			al = cb.getActionListeners()[0];
			cb.removeActionListener(al);
			cb.removeAllItems();
			cb.addActionListener(al);
		} else {
			cb.removeAllItems();
		}
	}

}
