package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

import javax.swing.JComboBox;

import FrontEnd.AddRecordFrame;
import FrontEnd.App;
import FrontEnd.QuerryFrame;
import Model.Logger;
import Model.Model;
import Model.Utils;

public class BtnListener implements ActionListener {

	private Model model;
	private App app;
	private StringBuilder sb = new StringBuilder("");
	private State state = State.idle;

	public BtnListener(Model m, App appFrame) {
		model = m;
		app = appFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Logger.i("CLICK BUTTON.............................");
		String command = e.getActionCommand();
		if (command.equals(Utils.COMMAND_EDIT) && state != State.editing) {
			System.out.println("BUTTON EVENT ..................... EDIT");
			try{
				app.clearCbox();
				app.writeCellToText();
				app.populateCbox(app.getColumnRecords());
				app.turnOffTableSelection();
				state = State.editing;
			}
			catch(Exception ee){
				Logger.e(ee.getMessage());
			}
		} else if (command.equals(Utils.COMMAND_SAVE) && state == State.editedState) {
			model.executeUpdate(sb.toString());
			Controller.refreshTables(model, app);
			resetStringBuilder();
			state = State.idle;
		} else if (command.equals(Utils.COMMAND_CANCEL) && !(state == State.idle)) {
			Controller.refreshTables(model, app);
			resetStringBuilder();
			app.turnOnTableSelection();
			state = State.idle;
			app.setTextField("");
			app.clearCbox();
		} else if (command.equals(Utils.COMMAND_NEW) && state == State.idle) {
//			AddRecordFrame arf = new AddRecordFrame(model, model.getColumnNamesWithoutID(),	model.getForeignKeysOf(model.getLastSelectedTable()));
//			List<String> response = arf.getResponse();
//			if (response != null) {
//				String insert = Utils.getSqlValuesStringFromList(response, model.getLastSelectedTable(),model.getColumnNamesWithoutID());
//				Logger.i(Logger.getMethodName(), insert);
//				sb.append(insert);
//				state = State.editedState;
//				Controller.refreshTables(model, app);
//			}
//			Controller.refreshTables(model, app); // 	WITHOUT THIS ITS A BUG - update - corrected bug 
			
			ResultSet rs= model.executeQuerry("select * from cds_michal.t_btn_sql") ;
			
			new QuerryFrame(model,Utils.getNthColumnRecordsFrom(rs, 3),Utils.getNthColumnRecordsFrom(rs, 5),Utils.getNthColumnRecordsFrom(rs, 4));
			
		} else if (command.equals("cBox") && state == State.editing) {
			JComboBox<Object> cb = (JComboBox<Object>) e.getSource();
			state = State.editing;
			app.setTextField(cb.getSelectedItem());
			app.selectTextField();
		} else if (command.equals("textField") && state == state.editing) { // what if string is null
			String record = app.getTextField();
			String colName = app.getSelectedCellColumnName();
			int idNumber = model.getIdnumber(app.getSelectedRow());
			String idString = model.getIdString();
			System.out.println("ID OF RECORD IS ....................................." + idNumber);
			sb.append("UPDATE " + model.getLastSelectedTable() + " SET " + colName + " ='" + record + "' WHERE "+ idString + " = '" + idNumber + "';");
			System.out.println(sb);
			app.turnOnTableSelection();
			state = State.editedState;
			app.writeTextToCell();
			app.clearCbox();
		}

		Logger.i("CURRENT SQL STATE : " + sb.toString());
		updateButtonsState();
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
