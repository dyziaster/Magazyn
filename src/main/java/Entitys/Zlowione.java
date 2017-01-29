package Entitys;

import com.j256.ormlite.field.DatabaseField;

public class Zlowione {

	@DatabaseField(id = true)
	private int id;
	@DatabaseField
	private String nazwaZlowione;
	@DatabaseField
	private String opis;

}
