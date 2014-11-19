package objekty_vozidla;

import objekty_budovy.HospodaTankova;
import semestralka.StaticData;
import simulace.Simulace;

/**
 * Trida pro vytvareni objektu typu Cisterna. Uchovava mnozstvi prevazeneho piva, cilovou hospodu a cas, kdy tam dorazi a kdy se vrati zpet do pivovaru.
 * @author Vlada47 & Shag0n
 *
 */
public class Cisterna {
	
	public static final double RYCHLOST = 60.0;
	public static final int KAPACITA = 50;
	
	private final int ID;
	private int naklad;
	private int cilovaHospoda;
	private int denDorazeniDoHospody;
	private int hodinaDorazeniDoHospody;
	private int denPrecerpaniPiva;
	private int hodinaPrecerpaniPiva;
	private int denNavratuDoPivovaru;
	private int hodinaNavratuDoPivovaru;
	
	/**
	 * Konstruktor objektu Cisterna - nastavuje se zde ID podle vstupu a mnozstvi piva na 0.
	 * @param ID - jedinecny identifikator konkretni cisterny
	 */
	public Cisterna(int ID) {
		this.ID = ID;
		this.naklad = 0;
	}
	
	/**
	 * Metoda, ktera spousti metody pri splneni casovych podminek dorazeni do hospody nebo navratu do pivovaru.
	 */
	public void provedAkci() {
		if((this.hodinaDorazeniDoHospody == Simulace.hodina) && (this.denDorazeniDoHospody == Simulace.den)) {
			System.out.println("Cisterna "+this.ID+" dovezla "+this.naklad+" hl piva do hospody "+this.cilovaHospoda+".");
		}
		if((this.hodinaPrecerpaniPiva == Simulace.hodina) && (this.denPrecerpaniPiva == Simulace.den)) {
			odcerpejPivo();
		}
		if((this.hodinaNavratuDoPivovaru == Simulace.hodina) && (this.denNavratuDoPivovaru == Simulace.den)) {
			System.out.println("Cisterna "+this.ID+" se vratila do pivovaru.");
			Simulace.pivovar.dostupneCisterny.add(this);
			Simulace.pivovar.cisternyNaCeste.remove(this);
		}
	}
	
	public int getID() {
		return this.ID;
	}
	
	public int getNaklad() {
		return this.naklad;
	}
	
	public void setCilovaHospoda(int IDHospoda) {
		this.cilovaHospoda = IDHospoda;
	}
	
	public int getCilovaHospoda() {
		return this.cilovaHospoda;
	}
	
	public void setDenDorazeniDoHospody(int denDorazeniDoHospody) {
		this.denDorazeniDoHospody = denDorazeniDoHospody;
	}
	
	public void setHodinaDorazeniDoHospody(int hodinaDorazeniDoHospody) {
		this.hodinaDorazeniDoHospody = hodinaDorazeniDoHospody;
	}
	
	public void setDenNavratuDoPivovaru(int denNavratuDoPivovaru) {
		this.denNavratuDoPivovaru = denNavratuDoPivovaru;
	}
	
	public void setHodinaNavratuDoPivovaru(int hodinaNavratuDoPivovaru) {
		this.hodinaNavratuDoPivovaru = hodinaNavratuDoPivovaru;
	}
	
	public int getDenDorazeniDoHospody() {
		return this.denDorazeniDoHospody;
	}
	
	public int getHodinaDorazeniDoHospody() {
		return this.hodinaDorazeniDoHospody;
	}
	
	public int getDenNavratuDoPivovaru() {
		return this.denNavratuDoPivovaru;
	}
	
	public int getHodinaNavratuDoPivovaru() {
		return this.hodinaNavratuDoPivovaru;
	}
	
	public int getDenPrecerpaniPiva() {
		return this.denPrecerpaniPiva;
	}

	public void setDenPrecerpaniPiva(int denPrecerpaniPiva) {
		this.denPrecerpaniPiva = denPrecerpaniPiva;
	}

	public int getHodinaPrecerpaniPiva() {
		return this.hodinaPrecerpaniPiva;
	}

	public void setHodinaPrecerpaniPiva(int hodinaPrecerpaniPiva) {
		this.hodinaPrecerpaniPiva = hodinaPrecerpaniPiva;
	}
	
	public void nacerpejPivo(int pocet) {
		this.naklad += pocet;
	}
	
	private void odcerpejPivo() {
		int index = this.cilovaHospoda % StaticData.POMER_HOSPOD;
		HospodaTankova h = Simulace.tankoveHospody[index];
		h.nacerpejPivo(this.naklad);
		this.naklad = 0;
	}
}
