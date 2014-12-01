package objekty_budovy;

import io.InputOutput;

import java.util.ArrayList;
import java.util.List;

import objekty_ostatni.Pozice;
import semestralka.StaticData;
import simulace.Simulace;

/**
 * Trida uchovavajici atributy, kontruktor a metody objektu HospodaTankova.
 * @author Vlada47 & Shag0n
 *
 */
public class HospodaTankova {
	
	/**
	 * Objekt typu Pozice uchovavajici souradnice hospody na mape.
	 */
	private final Pozice pozice;
	
	/**
	 * Identifikator dane hospody.
	 */
	private final int ID;
	
	/**
	 * Aktualni mnozstvi piva, ktere ma hospoda k dispozici.
	 */
	private int stavPiva;
	
	/**
	 * List s spotrebou hospody v jednotlivych dnech, do ktereho je ukladano pri vytvrani objednavek na zacatku simulace.
	 * Hospoda podle nej odebira mnozstvi dostupneho piva a prislusny zaznam se spotrebou pote odstrani.
	 */
	private List<Integer> denniSpotreba;
	
	/**
	 * Pole se zaznamy o dovezenych objednavkach v jednotlivych dnech. Indexove odpovida poli se seznamem obsluhujicich vozidel.
	 */
	private String[] dovezeneObjednavky;
	
	/**
	 * Pole se zaznamy o obsluhujicich vozidlech v jednotlivych dnech. Indexove odpovida poli se seznamem dovezenych objednavek.
	 */
	private String[] vozidla;
	
	/**
	 * Konstruktor objektu HospodaTankova. Z nactenych parametru si ulozi ID a pozici na mape.
	 * Dale iniciuje stav piva na 6 hl (zacina simulaci s potrebnym mnozstvim piva pro svoji spotrebu prvni den).
	 * Nakonec vytvori instance ArrayListu a poli pro ukladani hodnot v prubehu simulace.
	 * @param id - jednoznacny identifikator hospody
	 * @param pozice - objekt obsahujici souradnice hospody
	 */
	public HospodaTankova(int id, Pozice pozice) {
		this.ID = id;
		this.pozice = pozice;
		this.stavPiva = 6;
		this.denniSpotreba = new ArrayList<Integer>();
		this.dovezeneObjednavky = new String[StaticData.POCET_DNU];
		this.vozidla = new String[StaticData.POCET_DNU];
	}
	
	/**
	 * Metoda, ktera resi denni spotrebu piva hospody. Pokud nema dostatecne mnozstvi piva, vypise se chybova hlaska, zapise se log simulace a zastavi se jeji provadeni.
	 * Pokud ma dostatek piva, odebere prislusne mnozstvi piva a danou spotrebu z listu.
	 */
	public void spotrebujPivo(){
		if(this.stavPiva < this.denniSpotreba.get(0)) {
			System.err.println("Tankova hospoda "+this.ID+" nema dostatek piva!");
			InputOutput.zapisVysledek();
			Simulace.timer.stop();
			//System.exit(1);
		}
		else {
			this.stavPiva -= this.denniSpotreba.get(0);
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
	 * Metoda pro pridani aktualniho mnozstvi piva pri obsluze cisternou. Hospoda si zaroven ulozi zaznam o objednavce.
	 * @param mnozstvi - pocet hl piva, ktere je hospode pridano
	 */
	public void nacerpejPivo(int mnozstvi) {
		this.stavPiva += mnozstvi;
		this.dovezeneObjednavky[Simulace.den-1] += mnozstvi+"/";
	}
	
	/**
	 * Metoda pro ulozeni zaznamu o cisterne, ktera hospode prave privezla objednavku.
	 * @param id - identifikator cisterny
	 */
	public void ulozCisternu(int id) {
		this.vozidla[Simulace.den-1] += "Cisterna "+id+"/";
	}
	
	/**
	 * Metoda na vypis stavu dane hospody (mnozstvi piva), pokud je o ni zazadano.
	 * @return vraci String s informacemi o hospode
	 */
	public String getVypis() {
		return "Tankova hospoda "+this.ID+" ma "+this.stavPiva+"hl piva.";
	}
}
