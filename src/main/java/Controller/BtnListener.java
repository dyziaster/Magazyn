package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import FrontEnd.App;
import Model.Model;

public class BtnListener implements ActionListener {

	private Model model;
	private App app;

	public BtnListener(Model m, App appFrame) {
		model = m;
		app = appFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("edit")) {
			System.out.println("BUTTON EVENT ..................... EDIT");
			app.writeCellToText();

		}

	}

}
