package Controller;

import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import FrontEnd.App;
import Model.Logger;
import Model.Model;
import Model.Utils;

public class Controller {

	private App appFrame;
	private Model model;
	private ListListener listListener;
	private TableListener tableListener;
	private CustomListener textListener;
	private BtnListener btnListener;
	private TableMenuListener menuListener;
	private HelpMenuListener helpMenuListener; 

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
		menuListener = new TableMenuListener(m, appFrame);
		helpMenuListener = new HelpMenuListener(m, appFrame);

		onStart();
	}

	public void connect() throws ClassNotFoundException, SQLException {
		model.connectToDatabase();
	}

	public ResultSet queryDatabase(String query) throws SQLException {
		return model.executeQuerry(query);
	}

	public void onStart() {
		Logger.setJta((appFrame.getAppender()));
		Logger.i("CONTROLLER.ONSTART .............................................");

//		appFrame.buttonDisable(Utils.COMMAND_NEW);
//		appFrame.buttonDisable(Utils.COMMAND_CANCEL);
//		appFrame.buttonDisable(Utils.COMMAND_SAVE);
//		appFrame.buttonDisable(Utils.COMMAND_EDIT);
//		appFrame.buttonDisable(Utils.COMMAND_TEXT);

		appFrame.setVisible(true);
		if (model.connectToDatabase()) {
			appFrame.setTableList(model.getTableNamesList());
			appFrame.setTableData(null, null);
			appFrame.setListListener(getListListener());
			appFrame.setTableListener(getTableListener());
			appFrame.setBtnListeners(getBtnListener());
			appFrame.setHelpListeners(getHelpListener());
			appFrame.setMenuItems(this.getMenuItems(),this.getMenuItemsNames(),menuListener);
		}

	}
	

	private ActionListener getHelpListener() {
		return helpMenuListener;
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
	
	public List<String> getMenuItems() {
		
		ResultSet rs = model.executeQuerry("select form_name from cds_michal.t_form_cfg;");
		List<String> list = Utils.getNthColumnRecordsFrom(rs, 1);
		
		return list;
	}
	public List<String> getMenuItemsNames() {
		
		ResultSet rs = model.executeQuerry("select form_discription from cds_michal.t_form_cfg;");
		List<String> list = Utils.getNthColumnRecordsFrom(rs, 1);
		
		return list;
	}

	public static void refreshTables(Model model, App appFrame) {
		ResultSet rs = model.executeQuerry("SELECT * FROM " + model.getLastSelectedTable());
		DefaultTableModel dtm = Utils.getTableModelFromRS(rs,model); // writes to columns, currenttablemodel
		appFrame.setTableData(dtm);
	}

}
