package Model;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTextArea;

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

}
