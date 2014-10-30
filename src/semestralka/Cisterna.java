package semestralka;

public class Cisterna {
	
	public final double RYCHLOST = 60.0;
	public final int KAPACITA = 50;
	
	private int ID;
	private int naklad;
	private double vzdalenostTam;
	private double vzdalenostZpet;
	private int cilovaHospoda;
	private int indexCiloveHospody;
	
	public Cisterna(int ID) {
		this.ID = ID;
		this.naklad = 0;
	}
	
	public void vykonejCestu() {
		if(this.vzdalenostTam > 0) {
			HospodaTankova h = Simulace.tankoveHospody[this.indexCiloveHospody];
			double zbyvajiciCas = 1.0 - (this.vzdalenostTam / this.RYCHLOST);
			
			if(zbyvajiciCas <= 0) this.vzdalenostTam -= this.RYCHLOST;
			else this.vzdalenostTam = 0.0;
			
			if(zbyvajiciCas > 0) {
				for(int i = 0; i < this.naklad; i++) {
					odcerpejHektolitr();
					h.pridejHektolitr();;
					zbyvajiciCas -= StaticData.HODIN_NA_HEKTOLITR;
					if(this.naklad <= 0) System.out.println("Cisterna "+this.ID+" dovezla objednavku do hospody "+this.cilovaHospoda+".");
					if(zbyvajiciCas <= 0) break;
				}
			}
			
			if(zbyvajiciCas > 0) {
				this.vzdalenostZpet -= (this.RYCHLOST*zbyvajiciCas);
			}
			
			if(zbyvajiciCas > 0) {
				Simulace.pivovar.dostupneCisterny.add(this);
				Simulace.pivovar.cisternyNaCeste.remove(this);
			}
		}
		else {
			HospodaTankova h = Simulace.tankoveHospody[this.indexCiloveHospody];
			double zbyvajiciCas = 1.0;
			
			for(int i = 0; i < this.naklad; i++) {
				odcerpejHektolitr();
				h.pridejHektolitr();;
				zbyvajiciCas -= StaticData.HODIN_NA_HEKTOLITR;
				if(this.naklad <= 0) System.out.println("Cisterna "+this.ID+" dovezla objednavku do hospody "+this.cilovaHospoda+".");
				if(zbyvajiciCas <= 0) break;
			}
			
			if(zbyvajiciCas > 0) {
				this.vzdalenostZpet -= (this.RYCHLOST*zbyvajiciCas);
			}
			
			if(zbyvajiciCas > 0) {
				Simulace.pivovar.dostupneCisterny.add(this);
				Simulace.pivovar.cisternyNaCeste.remove(this);
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
	
	public void setCil(int IDHospoda, int indexHospody) {
		this.cilovaHospoda = IDHospoda;
		this.indexCiloveHospody = indexHospody;
	}
	
	public int getCilovaHospoda() {
		return this.cilovaHospoda;
	}
	
	public boolean nacerpejPivo(int pocet) {
		if(this.naklad+pocet <= this.KAPACITA) {
			this.naklad += pocet;
			return true;
		}
		else {
			return false;
		}
	}
	
	private void odcerpejHektolitr() {
		this.naklad--;
	}
}
