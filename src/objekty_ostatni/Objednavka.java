package objekty_ostatni;

/**
 * Trida vytvarejici objekty typu Objednavka, ktere uchovavaji informace potrebne k jejich zpracovani.
 * @author Vlada47 & Shag0n
 *
 */
public class Objednavka {
	
	/**
	 * Den objednani.
	 */
	private int denObednani;
	
	/**
	 * Hodina objednani.
	 */
	private int casObednani;
	
	/**
	 * Identifikator hospody, ktera provadi objednavku.
	 */
	private final int idObjednavajiciho;
	
	/**
	 * Mnozstvi, ktere si hospoda objednava.
	 */
	private final int mnozstvi;
	
	/**
	 * Prekladiste, ktere vyrizuje objednavku.
	 */
	private final int idPrekladiste;
	
	/**
	 * Indikace, jestli je objednavka uz zpracovana.
	 */
	private boolean vyrizena;
	
	/**
	 * Konstruktor objektu Objednavka. Dochazi zde k vlozeni potrebnych atributu. 
	 * @param denObednani - den, kdy ma dojit k obednani
	 * @param casObednani - hodina, kdy ma dojit k obednani
	 * @param idObjednavajiciho - identifikator objednavajici hospody pro jeji urceni v na mape (grafu uzlu)
	 * @param idPrekladiste - identifikator prekladiste, ze ktereho se objednavka vyrizuje (pivovar odpovida prekladisty c 8)
	 * @param mnozstvi - mnozstvi piva v hektolitrech nebo sudech
	 */
	public Objednavka(int denObednani, int casObednani, int idObjednavajiciho, int idPrekladiste, int mnozstvi) {
		super();
		this.denObednani = denObednani;
		this.casObednani = casObednani;
		this.idObjednavajiciho = idObjednavajiciho;
		this.idPrekladiste = idPrekladiste;
		this.mnozstvi = mnozstvi;
		this.vyrizena = false;
	}
	
	/**
	 * Metoda pro nastaveni dnu objednani.
	 * @param den - den kdy probehne objednavka.
	 */
	public void setDenObednani(int den) {
		this.denObednani = den;
	}
	
	/**
	 * Metoda pro ziskani dnu objednani.
	 * @return - den, kdy probehne objednavka
	 */
	public int getDenObednani() {
		return this.denObednani;
	}
	
	/**
	 * Metoda na nastaveni casu objednani.
	 * @param cas - hodina, kdy dojde k obednani
	 */
	public void setCasObednani(int cas) {
		this.casObednani = cas;
	}
	
	/**
	 * Metoda na ziskani casu objednani.
	 * @return - hodina, kdy dojde k obednani
	 */
	public int getCasObednani() {
		return this.casObednani;
	}

	/**
	 * Metoda na ziskani identifikatoru objednavajici hospody.
	 * @return - identifikator hospody
	 */
	public int getIdObjednavajiciho() {
		return idObjednavajiciho;
	}
	
	/**
	 * Metoda na ziskani objednaneho mnozstvi.
	 * @return - objednane mnozstvi
	 */
	public int getMnozstvi() {
		return mnozstvi;
	}
	
	/**
	 * Metoda na ziskani prekladiste, ktere vyrizuje objednavku.
	 * @return - identifikator prekladiste
	 */
	public int getIdPrekladiste() {
		return idPrekladiste;
	}
	
	/**
	 * Metoda na zjisteni, jestli je objednavka uz vyrizena.
	 * @return - indikace vyrizeni objednavky
	 */
	public boolean isVyrizena() {
		return this.vyrizena;
	}
	
	/**
	 * Metoda na nastaveni vyrizeni objednavky.
	 * @param vyrizena - indikace vyrizeni objednavky
	 */
	public void setVyrizena(boolean vyrizena) {
		this.vyrizena = vyrizena;
	}
}
