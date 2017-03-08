package Controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JMenuItem;

import FrontEnd.App;
import FrontEnd.QuerryFrame;
import Model.Logger;
import Model.Model;
import Model.Utils;

public class TableMenuListener implements ActionListener {

	private Model model;
	private App app;

	public TableMenuListener(Model model, App app) {
		super();
		this.model = model;
		this.app = app;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			String frameName = ((JMenuItem) e.getSource()).getText();
			String form = e.getActionCommand();
			String querry = "select * from cds_michal.t_btn_sql where form_name ='" + form + "';";
			ResultSet rs = model.executeQuerry(querry);
			List<String> listQuerrys = Utils.getColumnRecordsFrom(rs, "form_sql");
			List<String> listObject = Utils.getColumnRecordsFrom(rs, "Object");
			List<String> listNames = Utils.getColumnRecordsFrom(rs, "form_opis_btn");
			new QuerryFrame(model, listObject, listNames, listQuerrys, frameName);

		} catch (SQLException e1) {
			Logger.e(Logger.getMethodName(), "Menu Event failed " + e1.getMessage());
		}
	}

}
