package Entitys;

import com.j256.ormlite.field.DatabaseField;

public class Narzedzie {

	@DatabaseField(id = true)
	private int id;
	@DatabaseField
	private String nazwa;
	@DatabaseField
	private String opis;
	
}
