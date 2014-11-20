package objekty_vozidla;

import objekty_budovy.Pivovar;
import objekty_budovy.Prekladiste;
import simulace.Simulace;

/**
 * Trida pro vytvareni objektu typu Kamion. Uchovava pocet plnych a prazdnych sudu, cilove prekladiste a cas, kdy tam dorazi a kdy se vrati zpet do pivovaru.
 * @author Vlada47
 *
 */
public class Kamion {
	
	public static final double RYCHLOST = 90.0;
	public static final int KAPACITA = 100;
	
	private final int ID;
	private int pocetPlnychSudu;
	private int pocetPrazdnychSudu;
	private int cilovePrekladiste;
	private int denDorazeniDoPrekladiste;
	private int hodinaDorazeniDoPrekladiste;
	private int denPrelozeniSudu;
	private int hodinaPrelozeniSudu;
	private int denNavratuDoPivovaru;
	private int hodinaNavratuDoPivovaru;
	private boolean naCeste;
	
	/**
	 * Konstruktor objektu Kamion - nastavuje se zde ID podle vstupu a pocty plnych i prazdnych sudu na 0.
	 * @param ID - jedinecny identifikator konkretniho kamionu
	 */
	public Kamion(int ID) {
		this.ID = ID;
		this.pocetPrazdnychSudu = 0;
		this.pocetPlnychSudu = 0;
		this.setNaCeste(false);
	}
	
	/**
	 * Metoda, ktera spousti metody pri splneni casovych podminek dorazeni do prekladiste nebo navratu do pivovaru.
	 */
	public void provedAkci() {
		if((this.hodinaDorazeniDoPrekladiste == Simulace.hodina) && (this.denDorazeniDoPrekladiste == Simulace.den)) {
			System.out.println("Kamion "+this.ID+" dovezl svuj naklad sudu do prekladiste "+this.cilovePrekladiste+".");
		}
		if((this.hodinaPrelozeniSudu == Simulace.hodina) && (this.denPrelozeniSudu == Simulace.den)) {
			vylozPlneSudy();
			nalozPrazdneSudy();
			System.out.println("Kamion "+this.ID+" prelozil sudy v prekladisti "+this.cilovePrekladiste+".");
		}
		if((this.hodinaNavratuDoPivovaru == Simulace.hodina) && (this.denNavratuDoPivovaru == Simulace.den)) {
			System.out.println("Kamion "+this.ID+" se vratil do pivovaru");
			vylozPrazdneSudy();
			this.setNaCeste(false);
			
			Pivovar p = Simulace.pivovar;
			p.setPocetKamionuNaCeste(p.getPocetKamionuNaCeste()-1);
		}
	}
	
	public int getID() {
		return this.ID;
	}
	
	public int getPocetPlnychSudu() {
		return this.pocetPlnychSudu;
	}
	
	public int getPocetPrazdnychSudu() {
		return this.pocetPrazdnychSudu;
	}
	
	public boolean isNaCeste() {
		return this.naCeste;
	}

	public void setNaCeste(boolean naCeste) {
		this.naCeste = naCeste;
	}
	
	public void setCilovePrekladiste(int IDPrekladiste) {
		this.cilovePrekladiste = IDPrekladiste;
	}
	
	public int getCilovePrekladiste() {
		return this.cilovePrekladiste;
	}
	
	public void setDenDorazeniDoPrekladiste(int denDorazeniDoPrekladiste) {
		this.denDorazeniDoPrekladiste = denDorazeniDoPrekladiste;
	}
	
	public void setHodinaDorazeniDoPrekladiste(int hodinaDorazeniDoPrekladiste) {
		this.hodinaDorazeniDoPrekladiste = hodinaDorazeniDoPrekladiste;
	}
	
	public void setDenNavratuDoPivovaru(int denNavratuDoPivovaru) {
		this.denNavratuDoPivovaru = denNavratuDoPivovaru;
	}
	
	public void setHodinaNavratuDoPivovaru(int hodinaNavratuDoPivovaru) {
		this.hodinaNavratuDoPivovaru = hodinaNavratuDoPivovaru;
	}
	
	public int getDenDorazeniDoPrekladiste() {
		return this.denDorazeniDoPrekladiste;
	}
	
	public int getHodinaDorazeniDoCile() {
		return this.hodinaDorazeniDoPrekladiste;
	}
	
	public int getDenNavratuDoPivovaru() {
		return this.denNavratuDoPivovaru;
	}
	
	public int getHodinaNavratuDoPivovaru() {
		return this.hodinaNavratuDoPivovaru;
	}
	
	public int getDenPrelozeniSudu() {
		return denPrelozeniSudu;
	}

	public void setDenPrelozeniSudu(int denPrelozeniSudu) {
		this.denPrelozeniSudu = denPrelozeniSudu;
	}

	public int getHodinaPrelozeniSudu() {
		return hodinaPrelozeniSudu;
	}

	public void setHodinaPrelozeniSudu(int hodinaPrelozeniSudu) {
		this.hodinaPrelozeniSudu = hodinaPrelozeniSudu;
	}
	
	public void nalozPlneSudy(int pocet) {
		this.pocetPlnychSudu += pocet;
	}
	
	private void nalozPrazdneSudy() {
		Prekladiste p = Simulace.prekladiste[this.cilovePrekladiste];
		int pocet = p.odevzdejPrazdneSudy();
		this.pocetPrazdnychSudu += pocet;
	}
	
	private void vylozPlneSudy() {
		Prekladiste p = Simulace.prekladiste[this.cilovePrekladiste];
		p.prijmiPlneSudy(this.pocetPlnychSudu);
		this.pocetPlnychSudu = 0;
	}
	
	private void vylozPrazdneSudy() {
		this.pocetPrazdnychSudu = 0;
	}
}
