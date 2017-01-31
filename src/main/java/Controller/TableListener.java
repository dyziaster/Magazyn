package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import FrontEnd.App;
import Model.Model;

public class TableListener implements ListSelectionListener {
	private App appFrame;
	private Model model;

	public TableListener(Model m, App appFrame) {

		this.appFrame = appFrame;
		this.model = m;
	}


		@Override
		public void valueChanged(ListSelectionEvent event) {
			
			 if (event.getValueIsAdjusting()) {
	                return;
	            }	
			

			
		}
		
		
       
	

}
