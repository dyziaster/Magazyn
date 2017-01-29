package Entitys;

import com.j256.ormlite.field.DatabaseField;

public class Obszar {

	@DatabaseField(id = true)
	private int id;
	@DatabaseField
	private String nazwaObszaru;
	@DatabaseField
	private String obszar;

}
