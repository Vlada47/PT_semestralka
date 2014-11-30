package objekty_ostatni;

/**
 * Tøída pro uložení údajù o cestì mezi dvìma body (hospodami, pøekladišti...).
 * 
 * @author Vlada47 & Shag0n
 *
 */
public class Cesta implements Comparable<Cesta> {
	
	/**
	 * Identifikator zdrojoveho uzlu
	 */
	private final int idFrom;
	
	/**
	 * Identifikator ciloveho uzlu.
	 */
	private final int idTo;
	
	/**
	 * Vzdalenost mezi dvema body.
	 */
	private final double vzdalenost;

	/**
	 * Konstruktor objektu.
	 * @param idFrom - Identifikaèní èíslo hospody/pøekladištì, ze kterého cesta vede.
	 * @param idTo - Identifikaèní èíslo hospody/pøekladištì, do kterého cesta vede.
	 * @param vzdalenost - Vzdálenost v kilometrech mezi dvìma místy.
	 */
	public Cesta(final int idFrom, final int idTo, final double vzdalenost) {
		this.idFrom = idFrom;
		this.idTo = idTo;
		this.vzdalenost = vzdalenost;
	}
	
	/**
	 * Metoda k ziskani id zdroj.
	 * @return - id zdroje
	 */
	public int getIdFrom() {
		return this.idFrom;
	}
	
	/**
	 * Metoda k ziskani id cile.
	 * @return - id cile
	 */
	public int getIdTo() {
		return this.idTo;
	}
	
	/**
	 * Metoda k ziskani vzdalenosti mezi dvema uzly.
	 * @return - vzdalenost
	 */
	public double getVzdalenost() {
		return this.vzdalenost;
	}

	/**
	 * Metoda na vraceni vypisu parametru objektu.
	 */
	public String toString() {
		return this.idFrom + " " + this.idTo + " " + this.vzdalenost;
	}

	/**
	 * Metoda na porovnani delky dvou cest.
	 */
	public int compareTo(Cesta c) {
		
		int vysledek = 0;
		
		if (this.vzdalenost > c.getVzdalenost()) {
			vysledek = 1;
		}
		else if (this.vzdalenost < c.getVzdalenost()){
			vysledek = -1;
		}
		else {
			vysledek = 0;
		}
		
		return vysledek;
	}
}
