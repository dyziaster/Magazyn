package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;

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
	
	
	public Controller(){
		this(null);
	}
	
	public Controller(Model m) {
		this(m,null);
	}

	public Controller(Model m, App app) {

		this.model = m;
		this.appFrame = app;
		listListener = new ListListener(m,appFrame);
		textListener = new CustomListener(m,appFrame);
		tableListener = new TableListener();
		
	}

	public void connect() throws ClassNotFoundException, SQLException{
		model.connectToDatabase();
	}
	
	public ResultSet queryDatabase(String query) throws SQLException{
		return model.queryDatabase(query);
	}
	
	public void onStart() throws SQLException, ClassNotFoundException{
		model.connectToDatabase();
		appFrame.setTableList(model.getTableNames());
		System.out.println("tables:"+model.getTableNamesList());
		appFrame.setTableData(null, null);
		appFrame.setListListener(getListListener());
		appFrame.setTextListener(getTextListener());
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

}
