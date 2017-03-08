package Controller;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import org.apache.poi.hssf.model.WorkbookRecordList;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.atp.WorkdayCalculator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;

import FrontEnd.AddRecordFrame;
import FrontEnd.App;
import FrontEnd.Document;
import FrontEnd.QuerryFrame;
import Model.Logger;
import Model.Model;
import Model.Utils;

public class BtnListener implements ActionListener {

	private Model model;
	private App app;
	private StringBuilder sb = new StringBuilder("");
	private State state = State.idle;
	private Object source;

	public BtnListener(Model m, App appFrame) {
		model = m;
		app = appFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		source = e.getSource();
		
		Logger.i(Logger.getMethodName(),command+" "+source);

		switch (command) {
		case Utils.COMMAND_NEW:
			try {
				neww();
			} catch (SQLException e2) {
				Logger.e(Logger.getMethodName(), "New command failed " + e2.getMessage());
			}
			break;
		case Utils.COMMAND_NEWDOC:
			newDoc();
			break;
		case Utils.COMMAND_VIEW:
			view();
			break;
		case Utils.COMMAND_EDIT:
			edit();
			break;
		case Utils.COMMAND_SAVE:
			try {
				save();
			} catch (SQLException e1) {
				Logger.e(Logger.getMethodName(),"save failed"+e1.getMessage());
			}
			break;
		case Utils.COMMAND_CANCEL:
			cancel();
			break;
		case "cBox":
			pick();
			break;
		case Utils.COMMAND_TEXT:
			textEvent();
			break;
		}

		Logger.i("CURRENT SQL STATE : " + sb.toString());
	}

	private void neww() throws SQLException {
		if(model.getColumnNames() == null){
			Logger.e(Logger.getMethodName(), "select Table First");
			return;
		}
		AddRecordFrame arf = new AddRecordFrame(model, model.getColumnNamesWithoutID(model.getLastSelectedTable()), model.getForeignKeysOf(model.getLastSelectedTable()));
		List<String> response = arf.getResponse();
		if (response != null) {
			String insert = Utils.getSqlValuesStringFromList(response, model.getLastSelectedTable(), model.getColumnNamesWithoutID(model.getLastSelectedTable()));
			Logger.i(Logger.getMethodName(), insert);
			sb.append(insert);
			Controller.refreshTables(model, app);
		}
		//Controller.refreshTables(model, app); // WITHOUT THIS ITS A BUG - update
												// - corrected bug

	}

	private void newDoc() {

		new Document(app,model);
	}

	private void view() {
		//ResultSet rs = model.executeQuerry("select * from cds_michal.t_btn_sql");
		//new QuerryFrame(model, Utils.getNthColumnRecordsFrom(rs, 3), Utils.getNthColumnRecordsFrom(rs, 5), Utils.getNthColumnRecordsFrom(rs, 4));

		    Workbook wb = new HSSFWorkbook(); //Excell workbook
		    Sheet sheet = wb.createSheet(); //WorkSheet
		    Row row = sheet.createRow(2); //Row created at line 3
		    TableModel tm = model.getCurrentTableModel();

		    Row headerRow = sheet.createRow(0); //Create row at line 0
		    for(int headings = 0; headings < tm.getColumnCount(); headings++){ //For each column
		        headerRow.createCell(headings).setCellValue(tm.getColumnName(headings));//Write column name
		    }

		    for(int rows = 0; rows < tm.getRowCount(); rows++){ //For each table row
		        for(int cols = 0; cols < tm.getColumnCount(); cols++){ //For each table column
		            row.createCell(cols).setCellValue(tm.getValueAt(rows, cols).toString()); //Write value
		        }

		        //Set the row to the next one in the sequence 
		        row = sheet.createRow((rows + 3)); 
		    }
		    try {
				wb.write(new FileOutputStream(Paths.get("D:\\eclipse_workspace\\Magazyn\\wb.xls").toString()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//Save the file     
		
		    try {
				wb.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	private void edit() {
		try {
			app.clearCbox();
			app.writeCellToText();
			app.populateCbox(app.getColumnRecords()); // triggers cBox event
			app.turnOffTableSelection();
		} catch (Exception ee) {
			Logger.e(Logger.getMethodName(),ee.getMessage());
		}
	}

	private void textEvent() {
		// what
		// if
		// string
		// is
		// null
		String record = app.getTextField();
		String colName = app.getSelectedCellColumnName();
		int row = app.getSelectedRow();
		if(row<0){
			Logger.e(Logger.getMethodName()," Nothing selected");
			app.turnOnTableSelection();
			app.clearCbox();
			return;
		}
		int idNumber = model.getIdnumber(row);
		String idString = model.getIdString();
		System.out.println("ID OF RECORD IS ....................................." + idNumber);
		sb.append("UPDATE " + model.getLastSelectedTable() + " SET " + colName + " ='" + record + "' WHERE " + idString + " = '" + idNumber + "';");
		System.out.println(sb);
		app.turnOnTableSelection();
		app.writeTextToCell();
		app.clearCbox();
	}

	private void pick() {

		JComboBox<Object> cb = (JComboBox<Object>) source;
		app.setTextField(cb.getSelectedItem());
		app.selectTextField();
	}

	private void save() throws SQLException {

		if(sb.toString().equals("")){
			Logger.e(Logger.getMethodName(),"empty SQL");
			return;
		}
		model.executeUpdate(sb.toString());
		Controller.refreshTables(model, app);
		resetStringBuilder();
	}

	private void cancel() {
		if(model.getColumnNames() == null){
			Logger.e(Logger.getMethodName(), "select Table First");
			return;
		}
		Controller.refreshTables(model, app);
		resetStringBuilder();
		app.turnOnTableSelection();
		app.setTextField("");
		app.clearCbox();
	}

	private void resetStringBuilder() {
		sb.delete(0, sb.length());

	}

	private void updateButtonsState() {

		switch (state) {

		case idle:
			app.buttonEnable(Utils.COMMAND_NEW);
			app.buttonEnable(Utils.COMMAND_EDIT);
			app.buttonDisable(Utils.COMMAND_CANCEL);
			app.buttonDisable(Utils.COMMAND_SAVE);
			app.buttonDisable(Utils.COMMAND_TEXT);
			break;
		case editing:
			app.buttonDisable(Utils.COMMAND_EDIT);
			app.buttonDisable(Utils.COMMAND_NEW);
			app.buttonDisable(Utils.COMMAND_CANCEL);
			app.buttonDisable(Utils.COMMAND_SAVE);
			app.buttonEnable(Utils.COMMAND_TEXT);
			break;
		case editedState:
			app.buttonEnable(Utils.COMMAND_EDIT);
			app.buttonEnable(Utils.COMMAND_CANCEL);
			app.buttonEnable(Utils.COMMAND_SAVE);
			app.buttonDisable(Utils.COMMAND_NEW);
			app.buttonDisable(Utils.COMMAND_TEXT);
			break;
		default:
			app.buttonEnable(Utils.COMMAND_EDIT);
			app.buttonEnable(Utils.COMMAND_CANCEL);
			app.buttonEnable(Utils.COMMAND_SAVE);
			app.buttonEnable(Utils.COMMAND_NEW);

		}

	}

}

enum State {
	idle, editing, editedState
}
