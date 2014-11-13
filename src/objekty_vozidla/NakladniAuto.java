package objekty_vozidla;

import objekty_budovy.HospodaSudova;
import objekty_budovy.Prekladiste;
import semestralka.StaticData;
import simulace.Simulace;

public class NakladniAuto {
	
	public final double RYCHLOST = 70;
	public final int KAPACITA = 30;
	
	private int ID;
	private int pocetPlnychSudu;
	private int pocetPrazdnychSudu;
	private double vzdalenostTam;
	private double vzdalenostZpet;
	private int startovniPrekladiste;
	private int cilovaHospoda;
	private int indexCiloveHospody;
	
	public NakladniAuto(int ID) {
		this.ID = ID;
		this.pocetPrazdnychSudu = 0;
		this.pocetPlnychSudu = 0;
	}
	
	public void vykonejCestu() {
		if(this.vzdalenostTam > 0) {
			HospodaSudova h = Simulace.sudoveHospody[this.indexCiloveHospody];
			double zbyvajiciCas = 1.0 - (this.vzdalenostTam / this.RYCHLOST);
			
			if(zbyvajiciCas <= 0) this.vzdalenostTam -= this.RYCHLOST;
			else this.vzdalenostTam = 0.0;
			
			if(zbyvajiciCas > 0) {
				for(int i = 0; i < this.pocetPlnychSudu; i++) {
					vylozPlnySud();
					h.pridejPlnySud();
					zbyvajiciCas -= StaticData.HODIN_NA_SUD;
					if(this.pocetPlnychSudu <= 0) System.out.println("Nakladni auto "+this.ID+" z prekladiste "+this.startovniPrekladiste+" dovezlo objednavku do hospody "+this.cilovaHospoda+".");
					if(zbyvajiciCas <= 0) break;
				}
			}
			
			if(zbyvajiciCas > 0) {
				for(int i = 0; i < h.getPocetPrazdnychSudu(); i++) {
					h.odeberPrazdnySud();
					nalozPrazdnySud();
					zbyvajiciCas -= StaticData.HODIN_NA_SUD;
					if(zbyvajiciCas <= 0) break;
				}
			}
			
			if(zbyvajiciCas > 0) {
				this.vzdalenostZpet -= (this.RYCHLOST*zbyvajiciCas);
			}
			
			if(zbyvajiciCas > 0) {
				Prekladiste p = Simulace.prekladiste[this.startovniPrekladiste];
				p.prijmiPrazdneSudy(vylozPrazdneSudy());
				p.dostupnaAuta.add(this);
				p.autaNaCeste.remove(this);
			}
		}
		else {
			HospodaSudova h = Simulace.sudoveHospody[this.indexCiloveHospody];
			double zbyvajiciCas = 1.0;
			
			for(int i = 0; i < this.pocetPlnychSudu; i++) {
				vylozPlnySud();
				h.pridejPlnySud();
				zbyvajiciCas -= StaticData.HODIN_NA_SUD;
				if(this.pocetPlnychSudu <= 0) System.out.println("Nakladni auto "+this.ID+" z prekladiste "+this.startovniPrekladiste+" dovezlo objednavku do hospody "+this.cilovaHospoda+".");
				if(zbyvajiciCas <= 0) break;
			}
			
			if(zbyvajiciCas > 0) {
				for(int i = 0; i < h.getPocetPrazdnychSudu(); i++) {
					h.odeberPrazdnySud();
					nalozPrazdnySud();
					zbyvajiciCas -= StaticData.HODIN_NA_SUD;
					if(zbyvajiciCas <= 0) break;
				}
			}
			
			if(zbyvajiciCas > 0) {
				this.vzdalenostZpet -= (this.RYCHLOST*zbyvajiciCas);
			}
			
			if(zbyvajiciCas > 0) {
				Prekladiste p = Simulace.prekladiste[this.startovniPrekladiste];
				p.prijmiPrazdneSudy(vylozPrazdneSudy());
				p.dostupnaAuta.add(this);
				p.autaNaCeste.remove(this);
			}
		}
	}
	
	public int getID() {
		return this.ID;
	}
	
	public void setVzdalenost(double vzdalenost) {
		this.vzdalenostTam = vzdalenost;
		this.vzdalenostZpet = vzdalenost;
	}
	
	public double getVzdalenostTam() {
		return this.vzdalenostTam;
	}
	
	public double vzdalenostZpet() {
		return this.vzdalenostZpet;
	}
	
	public void setStartCil(int IDPrekladiste, int IDHospoda, int indexHospody) {
		this.startovniPrekladiste = IDPrekladiste;
		this.cilovaHospoda = IDHospoda;
		this.indexCiloveHospody = indexHospody;
	}
	
	public int getCilovaHospoda() {
		return this.cilovaHospoda;
	}
	
	public int getStartovniPrekladiste() {
		return this.startovniPrekladiste;
	}
	
	public boolean nalozPlneSudy(int pocet) {
		if(this.pocetPlnychSudu+pocet <= 30) {
			this.pocetPlnychSudu += pocet;
			return true;
		}
		else {
			return false;
		}
	}
	
	private void nalozPrazdnySud() {
		this.pocetPrazdnychSudu++;
	}
	
	private void vylozPlnySud() {
		this.pocetPlnychSudu--;
	}
	
	private int vylozPrazdneSudy() {
		int plneSudy = this.pocetPlnychSudu;
		this.pocetPlnychSudu = 0;
		return plneSudy;
	}
	
	public int getPocetPlnychSudu() {
		return this.pocetPlnychSudu;
	}
	
	public int getPocetPrazdnychSudu() {
		return this.pocetPrazdnychSudu;
	}
}
