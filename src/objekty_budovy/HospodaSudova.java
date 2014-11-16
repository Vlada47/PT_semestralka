package objekty_budovy;

import java.util.ArrayList;

import objekty_ostatni.Pozice;

public class HospodaSudova extends Budova {

	public final Pozice pozice;
	private final int ID;
	private int pocetPlnychSudu;
	private int pocetPrazdnychSudu;
	private ArrayList<Integer> denniSpotreba;
	private int idPrekladiste;

	public HospodaSudova(int ID, Pozice pozice) {
		this.ID = ID;
		this.pozice = pozice;
		this.pocetPlnychSudu = 6;
		this.pocetPrazdnychSudu = 0;
		this.denniSpotreba = new ArrayList<Integer>();
	}
	
	public void spotrebujPivo(){
		if(this.pocetPlnychSudu < this.denniSpotreba.get(0)) {
			System.err.println("Sudova hospoda "+this.ID+" nema dostatek piva!");
			System.exit(1);
		}
		else {
			this.pocetPlnychSudu -= this.denniSpotreba.get(0);
			this.denniSpotreba.remove(0);
		}	
	}
	
	public int getID() {
		return this.ID;
	}
	
	public void setIdPrekladiste(int id) {
		this.idPrekladiste = id;
	}
	
	public int getIdPrekladiste() {
		return this.idPrekladiste;
	}
	
	public int getPocetPrazdnychSudu() {
		return this.pocetPrazdnychSudu;
	}
	
	public int getPocetPlnychSudu() {
		return this.pocetPlnychSudu;
	}
	
	public Pozice getPosition() {
		return pozice;
	}
	
	public void addDenniSpotreba(int mnozstvi) {
		this.denniSpotreba.add(mnozstvi);
	}
	
	public void pridejPlneSudy(int mnozstvi) {
		this.pocetPlnychSudu += mnozstvi;
	}
	
	public int odeberPrazdneSudy() {
		int mnozstvi = this.pocetPrazdnychSudu;
		this.pocetPrazdnychSudu = 0;
		return mnozstvi;
	}
}
