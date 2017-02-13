package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import FrontEnd.App;
import FrontEnd.QuerryFrame;
import Model.Model;
import Model.Utils;

public class MenuListener implements ActionListener {


	private Model model;
	private App app;
	
	

	public MenuListener(Model model, App app) {
		super();
		this.model = model;
		this.app = app;
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		
		String form = e.getActionCommand();

		ResultSet rs = model.executeQuerry("select * from t_btn_sql where form_name ='"+form+"';");
	//	Utils.printResultSet(rs);
		
		new QuerryFrame(model,Utils.getNthColumnRecordsFrom(rs, 3),Utils.getNthColumnRecordsFrom(rs, 5),Utils.getNthColumnRecordsFrom(rs, 4));
		
	}

}
