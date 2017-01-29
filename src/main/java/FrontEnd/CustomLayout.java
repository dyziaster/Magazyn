package FrontEnd;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import Entitys.Account;

public class CustomLayout {
	
	  public static void main(String args[]) throws SQLException {
		    Object rowData[][] = { { "Row1-Column1", "Row1-Column2", "Row1-Column3" },
		            { "Row2-Column1", "Row2-Column2", "Row2-Column3" } };
		        Object columnNames[] = { "Column One", "Column Two", "Column Three" };
		        
		        String databaseUrl = "jdbc:sqlite:test.db";
		        // create a connection source to our database
		        ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl);

		        // instantiate the dao
		        Dao<Account, String> accountDao = DaoManager.createDao(connectionSource, Account.class);
		        
		        List<Account> acclist = accountDao.queryForAll();
		        
		       Object data[][] = new Object[acclist.size()][];
		       for(int i =0;i<acclist.size();i++){
		    	   data[i] = acclist.get(i).getAll();
		       }
		       
		 
		        
		        JTable table = new JTable(data, Account.getColumns());
		        
		        
		        //==============================================================
		    String labels[] = { "A", "B", "C", "D","E", "F", "G", "H","I", "J" };
		    GenericRawResults<String[]> tableNames = accountDao.queryRaw("SELECT name FROM sqlite_master WHERE type = 'table'");
		    List<String> tables = new ArrayList<String>();
		    for(String[] s : tableNames){
		    	tables.add(s[0]);
		    }
		    
		    String title = "JList Sample";
		    JFrame f = new JFrame(title);
		    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    JList list = new JList(tables.toArray());
		    JScrollPane scrollPane = new JScrollPane(list);
		    //===========================================================
		    JTabbedPane tabbedPane = new JTabbedPane();
		    
	        //Create the "cards".
	        JPanel card1 = new JPanel();
	        card1.add(new JButton("Button 1"));
	        card1.add(new JButton("Button 2"));
	        card1.add(new JButton("Button 3"));
	 
	        JScrollPane card2 = new JScrollPane(table);
	 
	        tabbedPane.addTab("Asdsadas", card1);
	        tabbedPane.addTab("Asdasdasdsa", card2);
		    //===========================================================
		    
		    JPanel pane = new JPanel();
		    pane.add(scrollPane);
		    pane.add(tabbedPane);
		    
		    //==========================================================
		    JTabbedPane tabbedPane2 = new JTabbedPane();
		    
	        //Create the "cards".
	        JPanel card3 = new JPanel();
	        card3.add(new JButton("Button 1"));
	        card3.add(new JButton("Button 2"));
	 
	        JPanel card4 = new JPanel();
	        card4.add(pane);
	        
	 
	        tabbedPane2.addTab("Asdsadas", card3);
	        tabbedPane2.addTab("Asdasdasdsa", card4);

		    //===========================================================
		    Container contentPane = f.getContentPane();
		    contentPane.add(tabbedPane2, BorderLayout.CENTER);
		    
		    f.setSize(800, 500);
		    f.setVisible(true);
		  }

}
