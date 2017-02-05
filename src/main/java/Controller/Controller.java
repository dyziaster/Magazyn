package Controller;

import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.event.ListSelectionListener;

import FrontEnd.App;
import Model.Model;

public class Controller {

	private App appFrame;
	private Model model;
	private ListListener listListener;
	private TableListener tableListener;
	private CustomListener textListener;
	private BtnListener btnListener;

	public Controller() {
		this(null);
	}

	public Controller(Model m) {
		this(m, null);
	}

	public Controller(Model m, App app) {

		this.model = m;
		this.appFrame = app;
		listListener = new ListListener(m, appFrame);
		textListener = new CustomListener(m, appFrame);
		tableListener = new TableListener(m, appFrame);
		btnListener = new BtnListener(m, appFrame);

		onStart();

	}

	public void connect() throws ClassNotFoundException, SQLException {
		model.connectToDatabase();
	}

	public ResultSet queryDatabase(String query) throws SQLException {
		return model.executeQuery(query);
	}

	public void onStart() {

		appFrame.buttonDisable("new");
		appFrame.buttonDisable("cancel");
		appFrame.buttonDisable("edit");
		appFrame.buttonDisable("save");

		appFrame.setVisible(true);
		if (model.connectToDatabase()) {
			appFrame.setTableList(model.getTableNamesList());
			appFrame.setTableData(null, null);
			appFrame.setListListener(getListListener());
			appFrame.setTableListener(getTableListener());
			appFrame.setBtnListeners(getBtnListener());

			// appFrame.setTextListener(getTextListener());
		}
	}

	private ActionListener getBtnListener() {
		return btnListener;
	}

	private ListSelectionListener getTableListener() {
		return tableListener;
	}

	public ListSelectionListener getListListener() {
		return listListener;
	}

	public CustomListener getTextListener() {
		return textListener;
	}

	public void setView(App app) {
		this.appFrame = app;
		System.out.println(app);
	}

	public static void refreshTables(Model model, App appFrame) {
		ResultSet rs = model.executeQuery("SELECT * FROM " + model.getLastSelectedTable());
		appFrame.setTableData(model.getTableModelFromRS(rs));
	}

}
