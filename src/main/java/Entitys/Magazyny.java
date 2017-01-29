package Entitys;

import com.j256.ormlite.field.DatabaseField;

public class Magazyny {

	@DatabaseField(id = true)
	private int id;
	@DatabaseField
	private String nazwaMagazyny;

}
