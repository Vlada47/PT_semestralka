package objekty_vozidla;

import java.util.ArrayList;

import objekty_budovy.Pivovar;
import objekty_budovy.Prekladiste;
import simulace.Simulace;

/**
 * Trida pro vytvareni objektu typu Kamion. Uchovava pocet plnych a prazdnych sudu, cilove prekladiste a cas, kdy tam dorazi a kdy se vrati zpet do pivovaru.
 * @author Vlada47 & Shag0n
 *
 */
public class Kamion {
	
	/**
	 * Prumerna rychlost nakladniho kamionu.
	 */
	public static final double RYCHLOST = 90.0;
	
	/**
	 * Maximalni kapacita kamionu v sudech.
	 */
	public static final int KAPACITA = 100;
	
	/**
	 * Identifikator kamionu.
	 */
	private final int ID;
	
	/**
	 * Aktualne prevazene mnozstvi plnych sudu.
	 */
	private int pocetPlnychSudu;
	
	/**
	 * Aktualne prevazene mnozstvi prazdnych sudu.
	 */
	private int pocetPrazdnychSudu;
	
	/**
	 * Aktualne nastavene cilove prekladiste.
	 */
	private int cilovePrekladiste;
	
	/**
	 * Den, kdy kamion dorazi do aktualne nastaveneho ciloveho prekladiste.
	 */
	private int denDorazeniDoPrekladiste;
	
	/**
	 * Hodina, kdy kamion dorazi do aktualne nastaveneho ciloveho prekladiste.
	 */
	private int hodinaDorazeniDoPrekladiste;
	
	/**
	 * Den, kdy kamion prelozi sudy v aktualne nastavenem cilovem prekladisti.
	 */
	private int denPrelozeniSudu;
	
	/**
	 * Hodina, kdy kamion prelozi sudy v aktualne nastavenem cilovem prekladisti.
	 */
	private int hodinaPrelozeniSudu;
	
	/**
	 * Den, kdy se kamion vrati do pivovaru.
	 */
	private int denNavratuDoPivovaru;
	
	/**
	 * Hodina, kdy se kamion vrati do pivovaru.
	 */
	private int hodinaNavratuDoPivovaru;
	
	/**
	 * Indikator, zda je kamion aktualne na ceste nebo ceka v pivovaru.
	 */
	private boolean naCeste;
	
	/**
	 * ArrayList s prekladisti, ktere kamion behem simulace obslouzil.
	 */
	private ArrayList<Integer> prekladiste;
	
	/**
	 * ArrayList s mnozstvim plnych sudu, ktere kamion zavezl prekladistim behem simulace.
	 */
	private ArrayList<Integer> zavezenePlneSudy;
	
	/**
	 * ArrayList s mnozstvim prazdnych sudu, ktere kamion odvezl z prekladist behem simulace.
	 */
	private ArrayList<Integer> odvezenePrazdneSudy;
	
	/**
	 * Konstruktor objektu Kamion - nastavuje se zde ID podle vstupu a pocty plnych i prazdnych sudu na 0.
	 * @param id - jedinecny identifikator konkretniho kamionu
	 */
	public Kamion(int id) {
		this.ID = id;
		this.pocetPrazdnychSudu = 0;
		this.pocetPlnychSudu = 0;
		this.setNaCeste(false);
		
		this.prekladiste = new ArrayList<Integer>();
		this.zavezenePlneSudy = new ArrayList<Integer>();
		this.odvezenePrazdneSudy = new ArrayList<Integer>();
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
	
	public void setCilovePrekladiste(int idPrekladiste) {
		this.cilovePrekladiste = idPrekladiste;
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

	public void setDenPrelozeniSudu(int denPrelozeniSudu) {
		this.denPrelozeniSudu = denPrelozeniSudu;
	}

	public void setHodinaPrelozeniSudu(int hodinaPrelozeniSudu) {
		this.hodinaPrelozeniSudu = hodinaPrelozeniSudu;
	}
	
	/**
	 * @return the prekladiste
	 */
	public ArrayList<Integer> getPrekladiste() {
		return prekladiste;
	}

	/**
	 * @return the zavezenePlneSudy
	 */
	public ArrayList<Integer> getZavezenePlneSudy() {
		return zavezenePlneSudy;
	}

	/**
	 * @return the odvezenePrazdneSudy
	 */
	public ArrayList<Integer> getOdvezenePrazdneSudy() {
		return odvezenePrazdneSudy;
	}
	
	public void nalozPlneSudy(int pocet) {
		this.pocetPlnychSudu += pocet;
	}
	
	private void nalozPrazdneSudy() {
		Prekladiste p = Simulace.prekladiste[this.cilovePrekladiste];
		int pocet = p.odevzdejPrazdneSudy();
		
		this.odvezenePrazdneSudy.add(pocet);
		
		this.pocetPrazdnychSudu += pocet;
	}

	private void vylozPlneSudy() {
		Prekladiste p = Simulace.prekladiste[this.cilovePrekladiste];
		p.prijmiPlneSudy(this.pocetPlnychSudu);
		
		this.prekladiste.add(this.cilovePrekladiste);
		this.zavezenePlneSudy.add(this.pocetPlnychSudu);
		
		this.pocetPlnychSudu = 0;
	}
	
	private void vylozPrazdneSudy() {
		this.pocetPrazdnychSudu = 0;
	}
	
	public String getVypis() {
		if(naCeste) {
			return "Kamion "+this.ID+" je na ceste a veze "+this.pocetPlnychSudu+" plnych a "+this.pocetPrazdnychSudu+" prazdnych sudu.";
		}
		else {
			return "Kamion "+this.ID+" je k dispozici v pivovaru.";
		}
	}
}
