package objekty_vozidla;

import java.util.ArrayList;
import java.util.List;

import objekty_budovy.HospodaTankova;
import objekty_budovy.Pivovar;
import semestralka.StaticData;
import simulace.Simulace;

/**
 * Trida pro vytvareni objektu typu Cisterna. Uchovava mnozstvi prevazeneho piva, cilovou hospodu a cas, kdy tam dorazi a kdy se vrati zpet do pivovaru.
 * @author Vlada47 & Shag0n
 *
 */
public class Cisterna {
	
	/**
	 * Prumerna rychlost cisterny.
	 */
	public static final double RYCHLOST = 60.0;
	
	/**
	 * Maximalni kapacita cisterny v hektolitrech.
	 */
	public static final int KAPACITA = 50;
	
	/**
	 * Identifikator cisterny.
	 */
	private final int ID;
	
	/**
	 * Aktualne prevazeny naklad.
	 */
	private int naklad;
	
	/**
	 * Aktualne nastaveny cilova hospoda.
	 */
	private int cilovaHospoda;
	
	/**
	 * Den, kdy ma cisterna dorazit do aktualne nastavene cilove hospody.
	 */
	private int denDorazeniDoHospody;
	
	/**
	 * Hodina, kdy ma cisterna dorazit do aktualne nastavene cilove hospody.
	 */
	private int hodinaDorazeniDoHospody;
	
	/**
	 * Den, kdy ma cisterna precerpat pivo v cilove hospode.
	 */
	private int denPrecerpaniPiva;
	
	/**
	 * Hodina, kdy ma cisterna precerpat pivo v cilove hospode.
	 */
	private int hodinaPrecerpaniPiva;
	
	/**
	 * Den, kdy se ma cisterna vratit z hospody zpet do pivovaru.
	 */
	private int denNavratuDoPivovaru;
	
	/**
	 * Hodina, kdy se ma cisterna vratit z hospody zpet do pivovaru.
	 */
	private int hodinaNavratuDoPivovaru;
	
	/**
	 * Indikator, zda je cisterna aktualne na ceste nebo ceka v pivovaru.
	 */
	private boolean naCeste;
	
	/**
	 * ArrayList s hospodami, ktere cisterna behem simulace obslouzila.
	 */
	final private List<Integer> hospody;
	
	/**
	 * ArrayList s mnozstvim piva, ktere cisterna zavezla hospodam behem simulace.
	 */
	final private List<Integer> zavezenePivo;
	
	/**
	 * Konstruktor objektu Cisterna - nastavuje se zde ID podle vstupu a mnozstvi piva na 0.
	 * Take se inicalizuji ArrayListy pro navstivene hospody a mnozstvi piva, ktere se do nich dovezlo.
	 * @param id - jedinecny identifikator konkretni cisterny
	 */
	public Cisterna(int id) {
		this.ID = id;
		this.naklad = 0;
		this.setNaCeste(false);
		
		this.hospody = new ArrayList<Integer>();
		this.zavezenePivo = new ArrayList<Integer>();
	}
	
	/**
	 * Metoda, ktera spousti metody pri splneni casovych podminek dorazeni do hospody nebo navratu do pivovaru.
	 * Zaroven o tom podava zpravy na vystup.
	 */
	public void provedAkci() {
		if((this.hodinaDorazeniDoHospody == Simulace.hodina) && (this.denDorazeniDoHospody == Simulace.den)) {
			System.out.println("Cisterna "+this.ID+" dovezla "+this.naklad+" hl piva do hospody "+this.cilovaHospoda+".");
		}
		if((this.hodinaPrecerpaniPiva == Simulace.hodina) && (this.denPrecerpaniPiva == Simulace.den)) {
			odcerpejPivo();
			System.out.println("Cisterna "+this.ID+" cisterna precerpala svuj naklad v hospode "+this.cilovaHospoda+".");
		}
		if((this.hodinaNavratuDoPivovaru == Simulace.hodina) && (this.denNavratuDoPivovaru == Simulace.den)) {
			System.out.println("Cisterna "+this.ID+" se vratila do pivovaru.");
			this.setNaCeste(false);
			
			Pivovar p = Simulace.pivovar;
			p.setPocetCisterenNaCeste(p.getPocetCisterenNaCeste()-1);
		}
	}
	
	/**
	 * Metoda k ziskani id cisterny.
	 * @return - id cisterny
	 */
	public int getID() {
		return this.ID;
	}
	
	/**
	 * Metoda k ziskani aktualni mnozstvi piva vezene cisternou.
	 * @return - mnozstvi piva vezene cisternou
	 */
	public int getNaklad() {
		return this.naklad;
	}
	
	
	/**
	 * Metoda k ziskani indikatoru, jestli je cisterna prave na ceste.
	 * @return - indikace, zda je cisterna na ceste
	 */
	public boolean isNaCeste() {
		return this.naCeste;
	}
	
	/**
	 * Metoda k nastaveni indikatoru, jestli je cisterna na ceste.
	 * @param naCeste - indikator, zda je cisterna na ceste
	 */
	public void setNaCeste(boolean naCeste) {
		this.naCeste = naCeste;
	}
	
	/**
	 * Metoda k nastaveni aktualni cilove hospody.
	 * @param idHospoda - aktualni cilova hospoda
	 */
	public void setCilovaHospoda(int idHospoda) {
		this.cilovaHospoda = idHospoda;
	}
	
	/**
	 * Metoda k ziskani aktualni cilove hospody.
	 * @return - aktualni cilova hospoda.
	 */
	public int getCilovaHospoda() {
		return this.cilovaHospoda;
	}
	
	/**
	 * Metoda k nastaveni dnu, kdy ma cisterna dorazit do aktualni cilove hospody.
	 * @param denDorazeniDoHospody - den, kdy cisterna dorazi do hospody
	 */
	public void setDenDorazeniDoHospody(int denDorazeniDoHospody) {
		this.denDorazeniDoHospody = denDorazeniDoHospody;
	}
	
	/**
	 * Metoda k nastaveni hodiny, kdy ma cisterna dorazit do aktualni cilove hospody.
	 * @param hodinaDorazeniDoHospody - hodina, kdy cisterna dorazi do hospody
	 */
	public void setHodinaDorazeniDoHospody(int hodinaDorazeniDoHospody) {
		this.hodinaDorazeniDoHospody = hodinaDorazeniDoHospody;
	}
	
	/**
	 * Metoda k nastaveni dnu, kdy ma cisterna dorazit zpet do pivovaru.
	 * @param denNavratuDoPivovaru - den, kdy cisterna dorazi zpet do pivovaru
	 */
	public void setDenNavratuDoPivovaru(int denNavratuDoPivovaru) {
		this.denNavratuDoPivovaru = denNavratuDoPivovaru;
	}
	
	/**
	 * Metoda k nastaveni hodiny, kdy ma cisterna dorazit zpet do pivovaru.
	 * @param hodinaNavratuDoPivovaru - hodina, kdy cisterna dorazi zpet do pivovaru
	 */
	public void setHodinaNavratuDoPivovaru(int hodinaNavratuDoPivovaru) {
		this.hodinaNavratuDoPivovaru = hodinaNavratuDoPivovaru;
	}
	
	/**
	 * Metoda k nastaveni dnu, kdy ma cisterna precerpat pivo v cilove hospode.
	 * @param denPrecerpaniPiva - den precerpani piva
	 */
	public void setDenPrecerpaniPiva(int denPrecerpaniPiva) {
		this.denPrecerpaniPiva = denPrecerpaniPiva;
	}
	
	/**
	 * Metoda k nastaveni hodiny, kdy ma cisterna precerpat pivo v cilove hospode.
	 * @param hodinaPrecerpaniPiva - hodina precerpani piva
	 */
	public void setHodinaPrecerpaniPiva(int hodinaPrecerpaniPiva) {
		this.hodinaPrecerpaniPiva = hodinaPrecerpaniPiva;
	}
	
	/**
	 * Metoda k ziskani pole behem simulace obslouzenych hospod.
	 * @return - pole s obslouzenymi hospodami
	 */
	public List<Integer> getHospody() {
		return hospody;
	}

	/**
	 * Metoda pro ziskani mnozstvi behem simulace zavezeneho piva.
	 * @return - pole s mnozstvim zavezeneho pivem
	 */
	public List<Integer> getZavezenePivo() {
		return zavezenePivo;
	}
	
	/**
	 * Metoda pro nacerpani piva v pivovaru.
	 * @param pocet - mnozstvi piva k nacerpani
	 */
	public void nacerpejPivo(int pocet) {
		this.naklad += pocet;
	}
	
	/**
	 * Metoda pro odcerpani piva v cilove hospode.
	 * Cisterna si nejprve urci index hledane hospody v poli tankovych hospod a pote ji preda naklad.
	 * Zaroven si ulozi informace o hospode a zavezenem mnozstvi do zaznamovych ArrayListu.
	 */
	private void odcerpejPivo() {
		int index = this.cilovaHospoda / StaticData.POMER_HOSPOD;
		HospodaTankova h = Simulace.tankoveHospody[index];
		h.nacerpejPivo(this.naklad);
		
		this.hospody.add(this.cilovaHospoda);
		this.zavezenePivo.add(this.naklad);
		h.ulozCisternu(this.ID);
		
		this.naklad = 0;
	}
	
	/**
	 * Metoda pro vytvoreni vypisoveho retezce, pokud je potreba zjistit stav vozidla (mnozstvi vezeneho piva).
	 * Rozlisuje situaci, kdy je cisterna v pivovaru a kdy je na ceste.
	 * @return - retezec s informacemi
	 */
	public String getVypis() {
		if(naCeste) {
			return "Cisterna "+this.ID+" je na ceste a veze "+this.naklad+"hl piva.";
		}
		else {
			return "Cisterna "+this.ID+" je k dispozici v pivovaru.";
		}
	}
}
