package Mvc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener{
	
	private View v;
	private Model m;

	
	
	public Controller(Model m) {
		super();
		this.m = m;
	}


	public void setV(View v) {
		this.v = v;
	}


	public void setM(Model m) {
		this.m = m;
	}


	public void actionPerformed(ActionEvent e) {

		switch(e.getActionCommand()){
		
		case "1":
			v.brighter();
			break;
		case "2":
			v.darker();
			break;
		
		}
		
	}

}
