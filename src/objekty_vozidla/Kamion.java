package objekty_vozidla;

import java.util.ArrayList;
import java.util.List;

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
	final private List<Integer> prekladiste;
	
	/**
	 * ArrayList s mnozstvim plnych sudu, ktere kamion zavezl prekladistim behem simulace.
	 */
	final private List<Integer> zavezenePlneSudy;
	
	/**
	 * ArrayList s mnozstvim prazdnych sudu, ktere kamion odvezl z prekladist behem simulace.
	 */
	final private List<Integer> odvezenePrazdneSudy;
	
	/**
	 * Konstruktor objektu Kamion - nastavuje se zde ID podle vstupu a pocty plnych i prazdnych sudu na 0.
	 * Take se inicializuji ArrayListy pro zaznam obslouzenych prekladist a pocty prelozenych plnych a prazdnych sudu.
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
	 * Zaroven tyto udalosti vypisuje na vystup.
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
	
	/**
	 * Metoda pro ziskani identifikatoru kamionu.
	 * @return - id kamionu
	 */
	public int getID() {
		return this.ID;
	}
	
	/**
	 * Metoda pro ziskani aktualniho poctu prevazenych plnych sudu.
	 * @return - pocet plnych sudu
	 */
	public int getPocetPlnychSudu() {
		return this.pocetPlnychSudu;
	}
	
	/**
	 * Metoda pro ziskani aktualniho poctu prevazenych prazdnych sudu.
	 * @return - pocet prazdnych sudu
	 */
	public int getPocetPrazdnychSudu() {
		return this.pocetPrazdnychSudu;
	}
	
	/**
	 * Metoda pro ziskani indikatoru, zda je kamion prave na ceste.
	 * @return - indikace, zda je kamion na ceste
	 */
	public boolean isNaCeste() {
		return this.naCeste;
	}
	
	/**
	 * Metoda pro nastaveni indikace, zda je kamion prave na ceste.
	 * @param naCeste - indikace, zda je kamion na ceste
	 */
	public void setNaCeste(boolean naCeste) {
		this.naCeste = naCeste;
	}
	
	/**
	 * Metoda pro nastaveni ciloveho prekladiste.
	 * @param idPrekladiste - cilove prakladiste
	 */
	public void setCilovePrekladiste(int idPrekladiste) {
		this.cilovePrekladiste = idPrekladiste;
	}
	
	/**
	 * Metoda pro ziskani aktualniho ciloveho prekladiste.
	 * @return - aktualni cilove prekladiste
	 */
	public int getCilovePrekladiste() {
		return this.cilovePrekladiste;
	}
	
	/**
	 * Metoda pro nastaveni dnu, kdy kamion dorazi do aktualniho ciloveho prekladiste.
	 * @param denDorazeniDoPrekladiste - den, kdy dorazi do prekladiste
	 */
	public void setDenDorazeniDoPrekladiste(int denDorazeniDoPrekladiste) {
		this.denDorazeniDoPrekladiste = denDorazeniDoPrekladiste;
	}
	
	/**
	 * Metoda pro nastaveni hodiny, kdy kamion dorazi do aktualniho ciloveho prekladiste.
	 * @param hodinaDorazeniDoPrekladiste - hodina, kdy dorazi do prekladiste
	 */
	public void setHodinaDorazeniDoPrekladiste(int hodinaDorazeniDoPrekladiste) {
		this.hodinaDorazeniDoPrekladiste = hodinaDorazeniDoPrekladiste;
	}
	
	/**
	 * Metoda pro nastaveni dnu, kdy se kamion vrati do pivovaru.
	 * @param denNavratuDoPivovaru - den, kdy se kamion vrati do pivovaru
	 */
	public void setDenNavratuDoPivovaru(int denNavratuDoPivovaru) {
		this.denNavratuDoPivovaru = denNavratuDoPivovaru;
	}
	
	/**
	 * Metoda pro nastaveni hodiny, kdy se kamion vrati do pivovaru.
	 * @param hodinaNavratuDoPivovaru - hodina, kdy se kamion vrati do pivovaru 
	 */
	public void setHodinaNavratuDoPivovaru(int hodinaNavratuDoPivovaru) {
		this.hodinaNavratuDoPivovaru = hodinaNavratuDoPivovaru;
	}
	
	/**
	 * Metoda pro nastaveni dnu, kdy kamion prelozi sudy v aktualnim cilovem prekladisti.
	 * @param denPrelozeniSudu - den, kdy kamion prelozi sudy
	 */
	public void setDenPrelozeniSudu(int denPrelozeniSudu) {
		this.denPrelozeniSudu = denPrelozeniSudu;
	}
	
	/**
	 * Metoda pro nastaveni hodiny, kdy kamion prelozi sudy v aktualnim cilovem prekladisti.
	 * @param denPrelozeniSudu - hodina, kdy kamion prelozi sudy
	 */
	public void setHodinaPrelozeniSudu(int hodinaPrelozeniSudu) {
		this.hodinaPrelozeniSudu = hodinaPrelozeniSudu;
	}
	
	/**
	 * Metoda pro ziskani pole s obslouzenymi prekladisti behem simulace.
	 * @return - pole s obslouzenymi prekladisti
	 */
	public List<Integer> getPrekladiste() {
		return prekladiste;
	}

	/**
	 * Metoda pro ziskani pole s dovezenymi plnymi sudy behem simulace.
	 * @return - pole s dovezenymi plnymi sudy
	 */
	public List<Integer> getZavezenePlneSudy() {
		return zavezenePlneSudy;
	}

	/**
	 * Metoda pro ziskani pole s odvezenymi prazdnymi sudy behem simulace.
	 * @return - pole odvezenymi prazdnymi sudy
	 */
	public List<Integer> getOdvezenePrazdneSudy() {
		return odvezenePrazdneSudy;
	}
	
	/**
	 * Metoda pro nalozeni plnych sudu v pivovaru.
	 * @param pocet - pocet plnych sudu k nalozeni
	 */
	public void nalozPlneSudy(int pocet) {
		this.pocetPlnychSudu += pocet;
	}
	
	/**
	 * Metoda pro nalozeni prazdnych sudu v obsluhovanem prekladisti. Kamion nalozi pocet sudu, ktery mu vrati prislusna metoda prekladiste.
	 * Zaroven si ulozi tento pocet do zaznamoveho pole pro pocty prazdnych sudu.
	 */
	private void nalozPrazdneSudy() {
		Prekladiste p = Simulace.prekladiste[this.cilovePrekladiste];
		int pocet = p.odevzdejPrazdneSudy();
		
		this.odvezenePrazdneSudy.add(pocet);
		
		this.pocetPrazdnychSudu += pocet;
	}
	
	/**
	 * Metoda pro vylozeni plnych sudu v obsluhovanem prekladisti. Kamion preda svuj pocet plnych sudu prislusne metode prekladiste.
	 * Zaroven si ulozi tento pocet do zaznamoveho pole pro pocty plnych sudu.
	 */
	private void vylozPlneSudy() {
		Prekladiste p = Simulace.prekladiste[this.cilovePrekladiste];
		p.prijmiPlneSudy(this.pocetPlnychSudu);
		
		this.prekladiste.add(this.cilovePrekladiste);
		this.zavezenePlneSudy.add(this.pocetPlnychSudu);
		
		this.pocetPlnychSudu = 0;
	}
	
	/**
	 * Metoda, ktera odstrani prazdne sudy. Je spoustena po navratu do pivovaru.
	 */
	private void vylozPrazdneSudy() {
		this.pocetPrazdnychSudu = 0;
	}
	
	/**
	 * Metoda, ktera vytvari retezec o stavu kamionu - jestli je na ceste a kolik veze plnych/prazdnych sudu.
	 * @return - retezec s informacemi
	 */
	public String getVypis() {
		if(naCeste) {
			return "Kamion "+this.ID+" je na ceste a veze "+this.pocetPlnychSudu+" plnych a "+this.pocetPrazdnychSudu+" prazdnych sudu.";
		}
		else {
			return "Kamion "+this.ID+" je k dispozici v pivovaru.";
		}
	}
}
