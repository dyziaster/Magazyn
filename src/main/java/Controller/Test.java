package Controller;

import java.awt.EventQueue;
import java.sql.SQLException;

import javax.swing.SwingUtilities;

import FrontEnd.App;
import Model.Model;

public class Test {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {

		Thread t = Thread.currentThread();
		System.out.println(t.getName());
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		        try {
					createGui();
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		});
	}
	// BUG IN UPDATE THERE COLUMN HAS MORE THAN 1 SAME VALUES <-------------------------------------- solved
	// BUG null pointer <-------------------------------------- solved
	// Column names must be set in model.
	// column names of what ?????
	// make it getColnames(String tablename)
	// columnNames bug in model - it was removing id column from orginal LIst of columns daaaaa?...
	// solved
	// columnNames and table model debug it ...(gettablemodelfromrs)
	// tworzyc wg nazwy kolumny nie numeru kolumny ------> solved
	// populate checkbox triggers action event
	
	public static void createGui() throws ClassNotFoundException, SQLException{
		Model m = new Model();
		App app = new App();
		new Controller(m,app);
		
	}
}
