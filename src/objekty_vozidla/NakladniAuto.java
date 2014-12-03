package objekty_vozidla;

import java.util.ArrayList;
import java.util.List;

import objekty_budovy.HospodaSudova;
import objekty_budovy.Prekladiste;
import semestralka.StaticData;
import simulace.Simulace;

/**
 * Trida pro vytvareni objektu typu NakladniAuto. Uchovava mnozstvi prevazeneho piva (plne/prazdne sudy), cilovou hospodu a cas, kdy tam dorazi a kdy se vrati zpet do pivovaru.
 * @author Vlada47 & Shag0n
 *
 */
public class NakladniAuto {
	
	/**
	 * Prumerna rychlost nakladniho auta.
	 */
	public static final double RYCHLOST = 70;
	
	/**
	 * Maximalni kapacita auta v sudech.
	 */
	public static final int KAPACITA = 30;
	
	/**
	 * Identifikator nakladniho auta v ramci prekladiste.
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
	 * Aktualne nastavena cilova hospoda.
	 */
	private int cilovaHospoda;
	
	/**
	 * Prekladiste, ze ktereho auto vyjelo.
	 */
	private int startovniPrekladiste;
	
	/**
	 * Den, kdy ma auto dorazit do aktualne nastavene cilove hospody.
	 */
	private int denDorazeniDoHospody;
	
	/**
	 * Hodina, kdy ma auto dorazit do aktualne nastavene cilove hospody.
	 */
	private int hodinaDorazeniDoHospody;
	
	/**
	 * Den, kdy ma auto prelozit sudy v cilove hospode.
	 */
	private int denPrelozeniSudu;
	
	/**
	 * Hodina, kdy ma auto prelozit sudy v cilove hospode.
	 */
	private int hodinaPrelozeniSudu;
	
	/**
	 * Den, kdy se ma auto vratit z hospody zpet do prekladiste.
	 */
	private int denNavratuDoPrekladiste;
	
	/**
	 * Hodina, kdy se ma auto vratit z hospody zpet do prekladiste.
	 */
	private int hodinaNavratuDoPrekladiste;
	
	/**
	 * Indikator, zda je auto aktualne na ceste nebo ceka v prekladisti.
	 */
	private boolean naCeste;
	
	/**
	 * ArrayList s hospodami, ktere auto behem simulace obslouzilo.
	 */
	final private List<Integer> hospody;
	
	/**
	 * ArrayList s mnozstvim plnych sudu, ktere auto zavezlo hospodam behem simulace.
	 */
	final private List<Integer> zavezenePlneSudy;
	
	/**
	 * ArrayList s mnozstvim prazdnych sudu, ktere auto odvezlo z hospod behem simulace.
	 */
	final private List<Integer> odvezenePrazdneSudy;
	
	/**
	 * Konstruktor objektu NakladniAuto - nastavuje se zde ID podle vstupu a mnozstvi plnych/prazdnych sudu na 0.
	 * Take se inicalizuji ArrayListy pro navstivene hospody a sudu, ktere se v nich prelozilo.
	 * @param id
	 */
	public NakladniAuto(int id) {
		this.ID = id;
		this.pocetPrazdnychSudu = 0;
		this.pocetPlnychSudu = 0;
		this.setNaCeste(false);
		
		this.hospody = new ArrayList<Integer>();
		this.zavezenePlneSudy = new ArrayList<Integer>();
		this.odvezenePrazdneSudy = new ArrayList<Integer>();
	}
	
	/**
	 * Metoda, ktera spousti metody pri splneni casovych podminek dorazeni do hospody nebo navratu do prekladiste.
	 * Zaroven o tom podava zpravy na vystup.
	 */
	public void provedAkci() {
		if((this.hodinaDorazeniDoHospody == Simulace.hodina) && (this.denDorazeniDoHospody == Simulace.den)) {
			System.out.println("Nakladni auto "+this.ID+" z prekladiste "+this.startovniPrekladiste+" dovezlo "+this.pocetPlnychSudu+" sudu do hospody "+this.cilovaHospoda+".");
		}
		if((this.hodinaPrelozeniSudu == Simulace.hodina) && (this.denPrelozeniSudu == Simulace.den)) {
			vylozPlneSudy();
			nalozPrazdneSudy();
		}
		if((this.hodinaNavratuDoPrekladiste == Simulace.hodina) && (this.denNavratuDoPrekladiste == Simulace.den)) {
			System.out.println("Nakladni auto "+this.ID+" se vratilo do prekladiste "+this.startovniPrekladiste+".");
			vylozPrazdneSudy();
			this.setNaCeste(false);
			
			Prekladiste p = Simulace.prekladiste[this.startovniPrekladiste];
			p.setPocetAutNaCeste(p.getPocetAutNaCeste()-1);
		}
	}
	
	/**
	 * Metoda k ziskani id nakladniho auta.
	 * @return - id auta
	 */
	public int getID() {
		return this.ID;
	}
	
	/**
	 * Metoda k ziskani aktualniho mnozstvi plnych sudu vezene nakladnim autem.
	 * @return - mnozstvi plnych sudu vezene autem
	 */
	public int getPocetPlnychSudu() {
		return this.pocetPlnychSudu;
	}
	
	/**
	 * Metoda k ziskani aktualniho mnozstvi prazdnych sudu vezene nakladnim autem.
	 * @return - mnozstvi prazdnych sudu vezene autem
	 */
	public int getPocetPrazdnychSudu() {
		return this.pocetPrazdnychSudu;
	}
	
	/**
	 * Metoda k ziskani indikatoru, jestli je auto prave na ceste.
	 * @return - indikace, zda je auto na ceste
	 */
	public boolean isNaCeste() {
		return this.naCeste;
	}
	
	/**
	 * Metoda k nastaveni indikatoru, jestli je auto na ceste.
	 * @param naCeste - indikator, zda je auto na ceste
	 */
	public void setNaCeste(boolean naCeste) {
		this.naCeste = naCeste;
	}
	
	/**
	 * Metoda k nastaveni aktualni cilove hospody.
	 * @param cilovaHospoda - aktualni cilova hospoda
	 */
	public void setCilovaHospoda(int cilovaHospoda) {
		this.cilovaHospoda = cilovaHospoda;
	}
	
	/**
	 * Metoda k ziskani aktualni cilove hospody.
	 * @return - aktualni cilova hospoda.
	 */
	public int getCilovaHospoda() {
		return this.cilovaHospoda;
	}
	
	/**
	 * Metoda k nastaveni prekladiste, ze ktereho auto vyjizdi.
	 * @param startovniPrekladiste - id prekladiste, ze ktereho auto vyjizdi
	 */
	public void setStartovniPrekladiste(int startovniPrekladiste) {
		this.startovniPrekladiste = startovniPrekladiste;
	}
	
	/**
	 * Metoda k ziskani prekladiste, ze ktereho auto vyjizdi.
	 * @return - id prekladiste, ze ktereho auto vyjizdi
	 */
	public int getStartovniPrekladiste() {
		return this.startovniPrekladiste;
	}
	
	/**
	 * Metoda k nastaveni dnu, kdy ma auto dorazit do aktualni cilove hospody.
	 * @param denDorazeniDoHospody - den, kdy auto dorazi do hospody
	 */
	public void setDenDorazeniDoHospody(int denDorazeniDoHospody) {
		this.denDorazeniDoHospody = denDorazeniDoHospody;
	}
	
	/**
	 * Metoda k nastaveni hodiny, kdy ma auto dorazit do aktualni cilove hospody.
	 * @param hodinaDorazeniDoHospody - hodina, kdy auto dorazi do hospody
	 */
	public void setHodinaDorazeniDoHospody(int hodinaDorazeniDoHospody) {
		this.hodinaDorazeniDoHospody = hodinaDorazeniDoHospody;
	}
	
	/**
	 * Metoda k nastaveni dnu, kdy ma auto prelozit sudy v aktualni cilove hospode.
	 * @param denPrelozeniSudu - den, kdy auto prelozi sudy v hospode
	 */
	public void setDenPrelozeniSudu(int denPrelozeniSudu) {
		this.denPrelozeniSudu = denPrelozeniSudu;
	}
	
	/**
	 * Metoda k nastaveni hodiny, kdy ma auto prelozit sudy v aktualni cilove hospode.
	 * @param hodinaPrelozeniSudu - hodina, kdy auto prelozi sudy v hospode
	 */
	public void setHodinaPrelozeniSudu(int hodinaPrelozeniSudu) {
		this.hodinaPrelozeniSudu = hodinaPrelozeniSudu;
	}
	
	/**
	 * Metoda k nastaveni dnu, kdy se ma auto vratit do prekladiste
	 * @param denNavratuDoPrekladiste - den, kdy se auto vrati do prekladiste
	 */
	public void setDenNavratuDoPrekladiste(int denNavratuDoPrekladiste) {
		this.denNavratuDoPrekladiste = denNavratuDoPrekladiste;
	}
	
	/**
	 * Metoda k nastaveni hodiny, kdy se ma auto vratit do prekladiste
	 * @param hodinaNavratuDoPrekladiste - hodina, kdy se auto vrati do prekladiste
	 */
	public void setHodinaNavratuDoPrekladiste(int hodinaNavratuDoPrekladiste) {
		this.hodinaNavratuDoPrekladiste = hodinaNavratuDoPrekladiste;
	}
	
	/**
	 * Metoda k ziskani pole behem simulace obslouzenych hospod.
	 * @return - pole s obslouzenymi hospodami
	 */
	public List<Integer> getHospody() {
		return hospody;
	}

	/**
	 * Metoda pro ziskani mnozstvi behem simulace zavezenych plnych sudu.
	 * @return - pole s mnozstvim zavezenych plnych sudu
	 */
	public List<Integer> getZavezenePlneSudy() {
		return zavezenePlneSudy;
	}

	/**
	 * Metoda pro ziskani mnozstvi behem simulace odvezenych prazdnych sudu.
	 * @return - pole s mnozstvim odvezenych prazdnych sudu
	 */
	public List<Integer> getOdvezenePrazdneSudy() {
		return odvezenePrazdneSudy;
	}
	
	/**
	 * Metoda k nalozeni plnych sudu v prekladisti.
	 * @param pocet - pocet nakladanych plnych sudu
	 */
	public void nalozPlneSudy(int pocet) {
		this.pocetPlnychSudu += pocet;
	}
	
	/**
	 * Metoda k nalozeni prazdnych sudu v hospode. Nejprve je pomoci metody pocitejIndexHospody urcen index do pole se sudovymi hospodami.
	 * Pote se nalozi pocet prazdnych sudu, ktery vrati prislusna metoda hospody. Nakonec se provede zaznam do prislusnych listu.
	 */
	private void nalozPrazdneSudy() {
		HospodaSudova h = Simulace.sudoveHospody[spocitejIndexHospody()];
		int pocet = h.odeberPrazdneSudy();
		
		this.odvezenePrazdneSudy.add(pocet);
		
		this.pocetPrazdnychSudu += pocet;
	}
	
	/**
	 * Metoda pro vylozeni plnych sudu v hospode. Nejprve je pomoci metody pocitejIndexHospody urcen index do pole se sudovymi hospodami.
	 * Pote se vylozi plne sudy do prislusne hospody a provede se zaznam do arraylistu.
	 */
	private void vylozPlneSudy() {
		HospodaSudova h = Simulace.sudoveHospody[spocitejIndexHospody()];
		h.pridejPlneSudy(this.pocetPlnychSudu);
		
		this.hospody.add(this.cilovaHospoda);
		this.zavezenePlneSudy.add(this.pocetPlnychSudu);
		h.ulozNakladniAuto(this.ID, this.startovniPrekladiste);
		
		this.pocetPlnychSudu = 0;
	}
	
	/**
	 * Metoda k vylozeni prazdnych sudu v prekladisti.
	 */
	private void vylozPrazdneSudy() {
		Prekladiste p = Simulace.prekladiste[this.startovniPrekladiste];
		p.prijmiPrazdneSudy(this.pocetPrazdnychSudu);
		this.pocetPrazdnychSudu = 0;
	}
	
	/**
	 * Metoda pro vypocet indexu cilove hospody v poli sudovych hospod. Za kazdych 20 hospod (POMER_HOSPOD) se index do pole snizi o 1 vuci ID hospody.
	 * @return - index hospody v poli sudovych hospod
	 */
	private int spocitejIndexHospody() {
		int index = this.cilovaHospoda - 1;
		int id = this.cilovaHospoda;
		
		while(id > StaticData.POMER_HOSPOD) {
			index--;
			id -= StaticData.POMER_HOSPOD;
		}
		
		return index;
	}
	
	/**
	 * Metoda pro vytvoreni vypisoveho retezce, pokud je potreba zjistit stav vozidla (mnozstvi vezeneho piva).
	 * Rozlisuje situaci, kdy je auto v prekladisti a kdy je na ceste.
	 * @return - retezec s informacemi
	 */
	public String getVypis() {
		if(naCeste) {
			return "Nakladni auto "+this.ID+" je na ceste do/z hospody "+this.cilovaHospoda+" a veze "+this.pocetPlnychSudu+" plnych a "+this.pocetPrazdnychSudu+" prazdnych sudu.";
		}
		else {
			return "Nakladni "+this.ID+" je k dispozici v prekladisti.";
		}
	}
}
