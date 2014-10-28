package semestralka;

public class Kamion {
	
	public final double RYCHLOST = 90.0;
	public final int KAPACITA = 100;
	
	private int ID;
	private int pocetPlnychSudu;
	private int pocetPrazdnychSudu;
	private double vzdalenostTam;
	private double vzdalenostZpet;
	private int cilovePrekladiste;
	
	public Kamion(int ID) {
		this.ID = ID;
		this.pocetPrazdnychSudu = 0;
		this.pocetPlnychSudu = 0;
	}
	
	public void vykonejCestu() {
		if(this.vzdalenostTam > 0) {
			Prekladiste p = Simulace.prekladiste[this.cilovePrekladiste];
			double zbyvajiciCas = 1.0 - (this.vzdalenostTam / this.RYCHLOST);
			
			if(zbyvajiciCas <= 0) this.vzdalenostTam -= this.RYCHLOST;
			else this.vzdalenostTam = 0.0;
			
			if(zbyvajiciCas > 0) {
				for(int i = 0; i < this.pocetPlnychSudu; i++) {
					vylozPlnySud();
					p.pridejPlnySud();
					zbyvajiciCas -= StaticData.HODIN_NA_SUD;
					if(zbyvajiciCas <= 0) break;
				}
			}
			
			if(zbyvajiciCas > 0) {
				for(int i = 0; i < p.getPocetPrazdnychSudu(); i++) {
					p.odeberPrazdnySud();
					nalozPrazdnySud();
					zbyvajiciCas -= StaticData.HODIN_NA_SUD;
					if(zbyvajiciCas <= 0) break;
				}
			}
			
			if(zbyvajiciCas > 0) {
				this.vzdalenostZpet -= (this.RYCHLOST*zbyvajiciCas);
			}
			
			if(zbyvajiciCas > 0) {
				Pivovar piv = Simulace.pivovar;
				piv.dostupneKamiony.add(this);
				piv.kamionyNaCeste.remove(this);
			}
		}
		else {
			Prekladiste p = Simulace.prekladiste[this.cilovePrekladiste];
			double zbyvajiciCas = 1.0;
			
			for(int i = 0; i < this.pocetPlnychSudu; i++) {
				vylozPlnySud();
				p.pridejPlnySud();
				zbyvajiciCas -= StaticData.HODIN_NA_SUD;
				if(zbyvajiciCas <= 0) break;
			}
			
			if(zbyvajiciCas > 0) {
				for(int i = 0; i < p.getPocetPrazdnychSudu(); i++) {
					p.odeberPrazdnySud();
					nalozPrazdnySud();
					zbyvajiciCas -= StaticData.HODIN_NA_SUD;
					if(zbyvajiciCas <= 0) break;
				}
			}
			
			if(zbyvajiciCas > 0) {
				this.vzdalenostZpet -= (this.RYCHLOST*zbyvajiciCas);
			}
			
			if(zbyvajiciCas > 0) {
				Pivovar piv = Simulace.pivovar;
				piv.dostupneKamiony.add(this);
				piv.kamionyNaCeste.remove(this);
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
	
	public void setCil(int IDPrekladiste) {
		this.cilovePrekladiste = IDPrekladiste;
	}
	
	public int getCilovePrekladiste() {
		return this.cilovePrekladiste;
	}
	
	public boolean nalozPlneSudy(int pocet) {
		if(this.pocetPlnychSudu+pocet <= this.KAPACITA) {
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
}
