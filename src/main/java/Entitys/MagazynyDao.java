package Entitys;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

public class MagazynyDao {
	
	public Dao<Magazyny,String> getDao(ConnectionSource source) throws SQLException{
		Dao<Magazyny,String> dao = DaoManager.createDao(source , Magazyny.class);
		return 	dao;
	}

}
