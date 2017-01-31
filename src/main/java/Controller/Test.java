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
	
	public static void createGui() throws ClassNotFoundException, SQLException{
		Model m = new Model();
		App app = new App();
		new Controller(m,app);
		
		
	}
}