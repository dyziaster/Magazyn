package Entitys;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

public class AccountDao {
	public Dao<Account,String> getDao(ConnectionSource source) throws SQLException{
		Dao<Account,String> dao = DaoManager.createDao(source , Account.class);
		return 	dao;
	}
}
