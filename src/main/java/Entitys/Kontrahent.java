package Entitys;

import com.j256.ormlite.field.DatabaseField;

public class Kontrahent {

	@DatabaseField(id = true)
	private int id;
	@DatabaseField
	private String nazwa;
	@DatabaseField
	private String adres;
	@DatabaseField
	private String panstwo;
	@DatabaseField
	private int nrWet;
	@DatabaseField
	private KontrahentTyp typ;

}


enum KontrahentTyp {

}

