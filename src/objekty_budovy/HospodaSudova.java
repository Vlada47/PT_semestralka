package objekty_budovy;

import io.InputOutput;

import java.util.ArrayList;

import objekty_ostatni.Pozice;

public class HospodaSudova {

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
			InputOutput.zapisVysledek();
			System.exit(1);
		}
		else {
			this.pocetPlnychSudu -= this.denniSpotreba.get(0);
			this.pocetPrazdnychSudu += this.denniSpotreba.get(0);
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
	
	public Pozice getPosition() {
		return pozice;
	}
	
	public void addDenniSpotreba(int mnozstvi) {
		this.denniSpotreba.add(mnozstvi);
	}
	
	public void pridejPlneSudy(int mnozstvi) {
		this.pocetPlnychSudu += mnozstvi;
	}
	
	public int odeberPrazdneSudy(int pocet) {
		if(this.pocetPrazdnychSudu >= pocet) {
			this.pocetPrazdnychSudu -= pocet;
			return pocet;
		}
		else {
			int mnozstvi = this.pocetPrazdnychSudu;
			this.pocetPrazdnychSudu = 0;
			return mnozstvi;
		}
	}
	
	public String getVypis() {
		return "Sudova hospoda "+this.ID+" ma "+this.pocetPlnychSudu+" plnych a "+this.pocetPrazdnychSudu+" prazdnych sudu.";
	}
}
