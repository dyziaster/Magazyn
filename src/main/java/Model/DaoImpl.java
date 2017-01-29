package Model;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import Entitys.Account;
import Entitys.DaoInterface;

import com.j256.ormlite.dao.Dao;

public class DaoImpl {

	public String[] getTables(){
		return null;
	}
	
	public String[][] getData(ConnectionSource source, String entity) throws ClassNotFoundException, SQLException{
		
		Class<?> c = Class.forName(entity.toString());
		DatabaseTableConfig<?> dtc = new DatabaseTableConfig<>();
		
		Dao<?, ?> dao = DaoManager.createDao(source, c);
		List<?> list = dao.queryForAll();
		Object data[][] = new Object[list.size()][];
	       for(int i =0;i<list.size();i++){
	    	   data[i] = ((DaoInterface)list.get(i)).getAll();
	       }
		return null;
		
	}
}
