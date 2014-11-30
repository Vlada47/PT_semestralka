package objekty_ostatni;

/**
 * T��da pro ulo�en� �daj� o cest� mezi dv�ma body (hospodami, p�ekladi�ti...).
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
	 * @param idFrom - Identifika�n� ��slo hospody/p�ekladi�t�, ze kter�ho cesta vede.
	 * @param idTo - Identifika�n� ��slo hospody/p�ekladi�t�, do kter�ho cesta vede.
	 * @param vzdalenost - Vzd�lenost v kilometrech mezi dv�ma m�sty.
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
