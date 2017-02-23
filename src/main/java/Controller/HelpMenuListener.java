package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import FrontEnd.App;
import Model.Model;

public class HelpMenuListener implements ActionListener {

	private Model model;
	private App app;

	public HelpMenuListener(Model model, App app) {
		super();
		this.model = model;
		this.app = app;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String command = e.getActionCommand();
		
		switch(command){
		case "toggleConsole":
			app.consoleToggle();
			break;
		}
		
	}
	

}
