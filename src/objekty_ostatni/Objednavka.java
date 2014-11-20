package objekty_ostatni;

/**
 * Trida vytvarejici objekty typu Objednavka, ktere uchovavaji informace potrebne k jejich zpracovani.
 * @author Vlada47 & Shag0n
 *
 */
public class Objednavka {
	
	private int denObednani; 
	private int casObednani;
	private final int idObjednavajiciho;
	private final int mnozstvi;
	private final int idPrekladiste;
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
	
	public void setDenObednani(int den) {
		this.denObednani = den;
	}
	
	public int getDenObednani() {
		return this.denObednani;
	}
	
	public void setCasObednani(int cas) {
		this.casObednani = cas;
	}
	
	public int getCasObednani() {
		return this.casObednani;
	}

	public int getIdObjednavajiciho() {
		return idObjednavajiciho;
	}

	public int getMnozstvi() {
		return mnozstvi;
	}

	public int getIdPrekladiste() {
		return idPrekladiste;
	}

	public boolean isVyrizena() {
		return this.vyrizena;
	}

	public void setVyrizena(boolean vyrizena) {
		this.vyrizena = vyrizena;
	}
}
