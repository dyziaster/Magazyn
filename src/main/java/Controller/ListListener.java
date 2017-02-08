package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import FrontEnd.App;
import Model.Logger;
import Model.Model;

public class ListListener implements ListSelectionListener {

	private App appFrame ;
	private Model model;
	
	public ListListener(Model m, App appFrame) {
		this.appFrame = appFrame;
		this.model = m;
		}
	@Override
	public void valueChanged(ListSelectionEvent e) {
		ListSelectionModel lsm = (ListSelectionModel) e.getSource();
		if (lsm.isSelectionEmpty() || e.getValueIsAdjusting()) {
			System.out.println("Selecting list ...");
		} else {
			// Find out which indexes are selected.
			int minIndex = lsm.getMinSelectionIndex();
			int maxIndex = lsm.getMaxSelectionIndex();
			for (int i = minIndex; i <= maxIndex; i++) {
				if (lsm.isSelectedIndex(i)) {
				//	appFrame.setLabelText(String.valueOf(i));
					
					String tableName = model.getTableNamesList().get(i);
					//Logger.i("FKS......"+model.getForeignKeysOf(tableName));
					model.setLastSelectedTable(tableName);
					ResultSet rs = model.executeQuery("SELECT * FROM "+tableName);
					
					appFrame.setTableData(model.getTableModelFromRS(rs));
					this.setButtons();
					
				}
			}

		}

	}
	
	private void setButtons(){
		appFrame.buttonEnable("edit");
		appFrame.buttonEnable("new");
		appFrame.buttonDisable("cancel");
		appFrame.buttonDisable("save");
	}
	

}
