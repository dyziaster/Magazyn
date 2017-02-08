package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

import javax.swing.JComboBox;

import FrontEnd.AddRecordFrame;
import FrontEnd.App;
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
			app.clearCbox();
			app.writeCellToText();
			app.populateCbox(app.getColumnRecords());
			app.turnOffTableSelection();
			state = State.editing;
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
		} else if (command.equals(Utils.COMMAND_NEW) && state == State.idle) {
			AddRecordFrame arf = new AddRecordFrame(model, model.getColumnNamesWithoutID(),model.getForeignKeysOf(model.getLastSelectedTable()));
			String insert = model.getSqlValuesStringFromList(arf.getResponse(), model.getLastSelectedTable(), model.getColumnNamesWithoutID());
			Logger.i(Logger.getMethodName(), insert);
			
			sb.append(insert);
			state = State.editedState;
			Controller.refreshTables(model, app);


		} else if (command.equals("cBox") && state == State.editing) {
			JComboBox<Object> cb = (JComboBox<Object>) e.getSource();
			state = State.editing;
			app.setTextField(cb.getSelectedItem());
			app.selectTextField();
		} else if (command.equals("textField") && state == state.editing) { // if string is '' does it need to paste null to database iinstead of ''
			String record = app.getTextField();
			String colName = app.getSelectedCellColumnName();
			int idNumber = model.getIdnumber(app.getSelectedRow());
			String idString = model.getIdString();
			System.out.println("ID OF RECORD IS ....................................."+idNumber);
			sb.append("UPDATE " + model.getLastSelectedTable() + " SET " + colName + " ='" + record + "' WHERE "+ idString + " = '" + idNumber + "';");
			System.out.println(sb);
			app.turnOnTableSelection();
			state = State.editedState;
			app.writeTextToCell();
		}
		
		Logger.i("CURRENT SQL STATE : "+sb.toString());
		updateButtonsState();
	}

	private void resetStringBuilder() {
		sb.delete(0, sb.length());

	}
	
	private void updateButtonsState(){
		
		switch(state){
		
		case idle :
			app.buttonEnable("edit");
			app.buttonEnable("new");
			app.buttonDisable("cancel");
			app.buttonDisable("save");
			break;
		case editing:
			app.buttonDisable("edit");
			app.buttonDisable("new");
			app.buttonDisable("cancel");
			app.buttonDisable("save");
			break;
		case editedState:
			app.buttonEnable("edit");
			app.buttonEnable("cancel");
			app.buttonEnable("save");
			app.buttonDisable("new");
			break;
		default: 
			app.buttonEnable("edit");
			app.buttonEnable("cancel");
			app.buttonEnable("save");
			app.buttonEnable("new");
			
		}
		
	}

}

enum State {
	idle, editing, editedState
}
