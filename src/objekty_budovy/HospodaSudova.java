package objekty_budovy;

import io.InputOutput;

import java.util.ArrayList;
import java.util.List;

import objekty_ostatni.Pozice;
import semestralka.StaticData;
import simulace.Simulace;

/**
 * Trida uchovavajici atributy, kontruktor a metody objektu HospodaSudova.
 * @author Vlada47 & Shag0n
 *
 */
public class HospodaSudova {
	
	/**
	 * Objekt typu Pozice uchovavajici souradnice hospody na mape.
	 */
	public final Pozice pozice;
	
	/**
	 * Identifikator dane hospody.
	 */
	private final int ID;
	
	/**
	 * Promenna na uchovani aktualniho postu plnych sudu.
	 */
	private int pocetPlnychSudu;
	
	/**
	 * Promenna na uchovani aktualniho postu prazdnych sudu.
	 */
	private int pocetPrazdnychSudu;
	
	/**
	 * List s spotrebou hospody v jednotlivych dnech, do ktereho je ukladano pri vytvrani objednavek na zacatku simulace.
	 * Hospoda podle nej odebira plne sudy a prislusny zaznam se spotrebou pote odstrani.
	 */
	private List<Integer> denniSpotreba;
	
	/**
	 * Identifikator prekladiste, ktere obsluhuje danou hospodu.
	 */
	private int idPrekladiste;
	
	/**
	 * Pole se zaznamy o dovezenych objednavkach v jednotlivych dnech. Indexove odpovida poli se seznamem obsluhujicich vozidel.
	 */
	private String[] dovezeneObjednavky;
	
	/**
	 * Pole se zaznamy o obsluhujicich vozidlech v jednotlivych dnech. Indexove odpovida poli se seznamem dovezenych objednavek.
	 */
	private String[] vozidla;
	
	/**
	 * Konstruktor objektu HospodaSudova. Z nactenych parametru si ulozi ID a pozici na mape.
	 * Dale iniciuje pocet plnych sudu na 6 a pocet prazdnych sudu na 0 (zacina simulaci s potrebnym poctem sudu pro svoji spotrebu prvni den).
	 * Nakonec vytvori instance ArrayListu a poli pro ukladani hodnot v prubehu simulace.
	 * @param id - jednoznacny identifikator hospody
	 * @param pozice - objekt obsahujici souradnice hospody
	 */
	public HospodaSudova(int id, Pozice pozice) {
		this.ID = id;
		this.pozice = pozice;
		this.pocetPlnychSudu = 6;
		this.pocetPrazdnychSudu = 0;
		this.denniSpotreba = new ArrayList<Integer>();
		this.dovezeneObjednavky = new String[StaticData.POCET_DNU];
		this.vozidla = new String[StaticData.POCET_DNU];
	}
	
	/**
	 * Metoda, ktera resi denni spotrebu piva hospody. Pokud nema dostatecne mnozstvi piva, vypise se chybova hlaska, zapise se log simulace a zastavi se jeji provadeni.
	 * Pokud ma dostatek piva, odebere prislusny pocet plnych sudu, prida stejny pocet prazdnych sudu a odebere danou spotrebu z listu.
	 */
	public void spotrebujPivo(){
		if(this.pocetPlnychSudu < this.denniSpotreba.get(0)) {
			System.err.println("Sudova hospoda "+this.ID+" nema dostatek piva!");
			InputOutput.zapisVysledek();
			Simulace.timer.stop();
			//System.exit(1);
		}
		else {
			this.pocetPlnychSudu -= this.denniSpotreba.get(0);
			this.pocetPrazdnychSudu += this.denniSpotreba.get(0);
			this.denniSpotreba.remove(0);
		}	
	}
	
	/**
	 * Metoda na vraceni ID dane hospody.
	 * @return vraci ID dane hospody
	 */
	public int getID() {
		return this.ID;
	}
	
	/**
	 * Metoda k nastaveni prekladiste, ke kteremu hospoda prislusi.
	 * @param id - identifikator prekladiste
	 */
	public void setIdPrekladiste(int id) {
		this.idPrekladiste = id;
	}
	
	/**
	 * Metoda na vraceni ID prekladiste, ktere spravuje tuto hospodu.
	 * @return vraci ID prekladiste
	 */
	public int getIdPrekladiste() {
		return this.idPrekladiste;
	}
	
	/**
	 * Metoda k vraceni objektu se souradnicemi hospody.
	 * @return vraci objekt se souradnicemi hospody
	 */
	public Pozice getPosition() {
		return pozice;
	}
	
	/**
	 * Vraci pole se zaznamy o dovezenych objednavkach v prubehu simulace.
	 * @return pole typu String
	 */
	public String[] getDovezeneObjednavky() {
		return this.dovezeneObjednavky;
	}
	
	/**
	 * Vraci pole se zaznamy o vozidlech, ktere hospode dovezly v prubehu simulace objednavky.
	 * @return pole typu String
	 */
	public String[] getVozidla() {
		return this.vozidla;
	}
	
	/**
	 * Metoda k pridani denni spotreby.
	 * @param mnozstvi - pivo, ktere hospoda za dany den spotrebuje
	 */
	public void addDenniSpotreba(int mnozstvi) {
		this.denniSpotreba.add(mnozstvi);
	}
	
	/**
	 * Metoda pro pridani plnych sudu pouzivana, kdyz je priveze nakladni auto. Hospoda si zaroven ulozi zaznam o objednavce.
	 * @param mnozstvi - pocet plnych sudu, ktere si hospoda objednala
	 */
	public void pridejPlneSudy(int mnozstvi) {
		this.pocetPlnychSudu += mnozstvi;
		this.dovezeneObjednavky[Simulace.den-1] += mnozstvi+"/";
	}
	
	/**
	 * Metoda pro zaznam nakladniho auta, ktere hospode provezlo objednavku.
	 * @param idAuta - identifikator daneho auta v ramci prekladiste
	 * @param idPrekladiste - identifikator prekladiste, ze ktereho auto pochazi
	 */
	public void ulozNakladniAuto(int idAuta, int idPrekladiste) {
		this.vozidla[Simulace.den-1] += "Auto "+idAuta+" z prekladiste "+idPrekladiste+"/";
	}
	
	/**
	 * Metoda na odebrani prazdnych sudu, pri jejich odberu obsluhujicim autem.
	 * @return vraci mnozstvi prazdnych sudu, ktere hospoda odevzda autu
	 */
	public int odeberPrazdneSudy() {
		int mnozstvi = this.pocetPrazdnychSudu;
		this.pocetPrazdnychSudu = 0;
		return mnozstvi;
	}
	
	/**
	 * Metoda na vypis stavu dane hospody (pocet plnych a prazdnych sudu), pokud je o ni zazadano.
	 * @return vraci String s informacemi o hospode
	 */
	public String getVypis() {
		return "Sudova hospoda "+this.ID+" ma "+this.pocetPlnychSudu+" plnych a "+this.pocetPrazdnychSudu+" prazdnych sudu.";
	}
}
