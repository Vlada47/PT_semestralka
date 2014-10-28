package semestralka;

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
	
	public NakladniAuto(int ID) {
		this.ID = ID;
		this.pocetPrazdnychSudu = 0;
		this.pocetPlnychSudu = 0;
	}
	
	public void vykonejCestu() {
		if(this.vzdalenostTam > 0) {
			Hospoda h = Simulace.hospody[this.cilovaHospoda];
			double zbyvajiciCas = 1.0 - (this.vzdalenostTam / this.RYCHLOST);
			
			if(zbyvajiciCas <= 0) this.vzdalenostTam -= this.RYCHLOST;
			else this.vzdalenostTam = 0.0;
			
			if(zbyvajiciCas > 0) {
				for(int i = 0; i < h.mnozstviObjednat; i++) {
					vylozPlnySud();
					h.pridejPlnySud();
					zbyvajiciCas -= StaticData.HODIN_NA_SUD;
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
			Hospoda h = Simulace.hospody[this.cilovaHospoda];
			double zbyvajiciCas = 1.0;
			
			for(int i = 0; i < h.mnozstviObjednat; i++) {
				vylozPlnySud();
				h.pridejPlnySud();
				zbyvajiciCas -= StaticData.HODIN_NA_SUD;
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
	
	public void setStartCil(int IDPrekladiste, int IDHospoda) {
		this.startovniPrekladiste = IDPrekladiste;
		this.cilovaHospoda = IDHospoda;
	}
	
	public int getCilovaHospoda() {
		return this.cilovaHospoda;
	}
	
	public int getStartovniPrekladiste() {
		return this.startovniPrekladiste;
	}
	
	public boolean nalozPlneSudy(int pocet) {
		if(this.pocetPlnychSudu+this.pocetPrazdnychSudu+pocet <= 30) {
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
}
