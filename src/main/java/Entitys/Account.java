package Entitys;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName  = "accunts")
public class Account {
	
	private static final String COL1 = "name";
	private static final String COL2 = "password";
	
	@DatabaseField(id = true, columnName = COL1)
	private String name;
	@DatabaseField(columnName = COL2)
	private String password;
	
	public Account(){
		
	}
	
	public String[] getAll(){
		return new String[]{name,password};
	}
	
	public static String[] getColumns(){
		return new String[]{COL1,COL2};
	}
	public Account(String name, String password) {
		super();
		this.name = name;
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	
	
}
