package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

import javax.swing.JComboBox;

import FrontEnd.AddRecordFrame;
import FrontEnd.App;
import Model.Model;

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
		String command = e.getActionCommand();
		if (command.equals("edit") && state != State.editing) {
			System.out.println("BUTTON EVENT ..................... EDIT");
			app.clearCbox();
			app.writeCellToText();
			app.populateCbox(app.getColumnRecords());
			app.turnOffTableSelection();
			state = State.editing;
		} else if (command.equals("save") && state == State.editedState) {
			model.executeUpdate(sb.toString()); 
			Controller.refreshTables(model, app);
			resetStringBuilder();
			state = State.idle;
		} else if (command.equals("cancel") && !(state == State.idle)) {
			Controller.refreshTables(model, app);
			resetStringBuilder();
			app.turnOnTableSelection();
 			state = State.idle; 
			app.setTextField("");
		} else if (command.equals("new") && state == State.idle) {
			System.out.println(".............................NEWRECORD");
			AddRecordFrame arf = new AddRecordFrame(app, model.getColumnNames());
			String insert = model.getSqlValuesStringFromList(arf.getResponse(), model.getLastSelectedTable(), model.getColumnNames());
			System.out.println("INSETING TO TABLE .........................................SQL:"+insert);
			//model.executeUpdate(insert);
			sb.append(insert);
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
			sb.append("UPDATE " + model.getLastSelectedTable() + " SET " + colName + " ='" + record + "' WHERE "+ idString + " = '" + idNumber + "';" + System.lineSeparator());
			System.out.println(sb);
			app.turnOnTableSelection();
			state = State.editedState;
			app.writeTextToCell();
		}
	}

	private void resetStringBuilder() {
		sb.delete(0, sb.length());

	}

}

enum State {
	idle, editing, editedState
}
