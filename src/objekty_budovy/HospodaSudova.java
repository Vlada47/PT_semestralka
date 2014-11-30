package objekty_budovy;

import io.InputOutput;

import java.util.ArrayList;

import objekty_ostatni.Pozice;
import semestralka.StaticData;
import simulace.Simulace;

public class HospodaSudova {

	public final Pozice pozice;
	private final int ID;
	private int pocetPlnychSudu;
	private int pocetPrazdnychSudu;
	private ArrayList<Integer> denniSpotreba;
	private int idPrekladiste;
	private String[] dovezeneObjednavky;
	private String[] vozidla;

	public HospodaSudova(int ID, Pozice pozice) {
		this.ID = ID;
		this.pozice = pozice;
		this.pocetPlnychSudu = 6;
		this.pocetPrazdnychSudu = 0;
		this.denniSpotreba = new ArrayList<Integer>();
		this.dovezeneObjednavky = new String[StaticData.POCET_DNU];
		this.vozidla = new String[StaticData.POCET_DNU];
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
	
	public String[] getDovezeneObjednavky() {
		return this.dovezeneObjednavky;
	}
	
	public String[] getVozidla() {
		return this.vozidla;
	}
	
	public void addDenniSpotreba(int mnozstvi) {
		this.denniSpotreba.add(mnozstvi);
	}
	
	public void pridejPlneSudy(int mnozstvi) {
		this.pocetPlnychSudu += mnozstvi;
		
		this.dovezeneObjednavky[Simulace.den-1] += mnozstvi+"/";
	}
	
	public void ulozNakladniAuto(int idAuta, int idPrekladiste) {
		this.vozidla[Simulace.den-1] += "Auto "+idAuta+" z prekladiste "+idPrekladiste+"/";
	}
	
	public int odeberPrazdneSudy() {
		int mnozstvi = this.pocetPrazdnychSudu;
		this.pocetPrazdnychSudu = 0;
		return mnozstvi;
	}
	
	public String getVypis() {
		return "Sudova hospoda "+this.ID+" ma "+this.pocetPlnychSudu+" plnych a "+this.pocetPrazdnychSudu+" prazdnych sudu.";
	}
}
