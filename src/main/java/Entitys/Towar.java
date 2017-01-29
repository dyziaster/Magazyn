package Entitys;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Towar {
	
	@DatabaseField(id = true)
	private int id;
	@DatabaseField
	private boolean deleteTowar;
	@DatabaseField
	private TowarTyp type;
	@DatabaseField
	private TowarRodzaj rodzaj;
	@DatabaseField
	private String nazwa1;
	@DatabaseField
	private String nazwa2;
	@DatabaseField
	private String nazwaLacinska;
	@DatabaseField
	private String opis;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isDeleteTowar() {
		return deleteTowar;
	}
	public void setDeleteTowar(boolean deleteTowar) {
		this.deleteTowar = deleteTowar;
	}
	public TowarTyp getType() {
		return type;
	}
	public void setType(TowarTyp type) {
		this.type = type;
	}
	public TowarRodzaj getRodzaj() {
		return rodzaj;
	}
	public void setRodzaj(TowarRodzaj rodzaj) {
		this.rodzaj = rodzaj;
	}
	public String getNazwa1() {
		return nazwa1;
	}
	public void setNazwa1(String nazwa1) {
		this.nazwa1 = nazwa1;
	}
	public String getNazwa2() {
		return nazwa2;
	}
	public void setNazwa2(String nazwa2) {
		this.nazwa2 = nazwa2;
	}
	public String getNazwaLacinska() {
		return nazwaLacinska;
	}
	public void setNazwaLacinska(String nazwaLacinska) {
		this.nazwaLacinska = nazwaLacinska;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	
	

}
