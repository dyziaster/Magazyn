package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.JTextField;

import FrontEnd.App;
import Model.Model;

public class CustomListener implements ActionListener {

	private Model model;
	private App app;

	public CustomListener(Model m, App appFrame) {
		this.model = m;
		this.app = appFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("text")) {
			JTextField tf = (JTextField) e.getSource();
			String querry = tf.getText();
			if (!querry.equals("")) {
				String[] arr = querry.split(" ");
				String command = arr[0];
				System.out.println("Comand ..." + command + "...");
				switch (command.toUpperCase()) {
				case "INSERT":
					model.executeQuery(querry);
					break;
				case "SELECT":
					ResultSet rs = model.executeQuery(querry);
					System.out.println(Model.resultSetToArrayList(rs));
					break;
				default:
					model.executeQuery(querry);
				}
			}
			tf.setText("");
		}
	}

}
