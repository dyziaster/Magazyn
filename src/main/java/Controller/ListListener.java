package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import FrontEnd.App;
import Model.Logger;
import Model.Model;
import Model.Utils;

public class ListListener implements ListSelectionListener {

	private App appFrame;
	private Model model;

	public ListListener(Model m, App appFrame) {
		this.appFrame = appFrame;
		this.model = m;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		try {
			ListSelectionModel lsm = (ListSelectionModel) e.getSource();
			if (lsm.isSelectionEmpty() || e.getValueIsAdjusting()) {
				;
			} else {
				// Find out which indexes are selected.
				int minIndex = lsm.getMinSelectionIndex();
				int maxIndex = lsm.getMaxSelectionIndex();
				for (int i = minIndex; i <= maxIndex; i++) {
					if (lsm.isSelectedIndex(i)) {

						String tableName = model.getTableNamesList().get(i);
						Logger.i(Logger.getMethodName(), "selected" + tableName);
						model.setLastSelectedTable(tableName);
						Controller.refreshTables(model, appFrame);

					}
				}

			}
		} catch (SQLException e1) {
			Logger.e(Logger.getMethodName(), "table select failed " + e1.getMessage());
		} catch (Exception e2) {
			Logger.e(Logger.getMethodName(), "table select failed " + e2.getMessage());
		}
	}

	private void setButtons() {
		appFrame.buttonEnable(Utils.COMMAND_EDIT);
		appFrame.buttonEnable(Utils.COMMAND_NEW);
		appFrame.buttonDisable(Utils.COMMAND_CANCEL);
		appFrame.buttonDisable(Utils.COMMAND_SAVE);
	}

}
