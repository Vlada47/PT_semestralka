package semestralka;

/**
 * T��da pro ulo�en� �daj� o cest� mezi dv�ma body (hospodami, p�ekladi�ti...).
 * @author Vlada47 & Shag0n
 *
 */
public class Cesta implements Comparable<Cesta> {
	
	private int idFrom;
	private int idTo;
	private double vzdalenost;
	
	/**
	 * Konstruktor objektu.
	 * @param idFrom - Identifika�n� ��slo hospody/p�ekladi�t�, ze kter�ho cesta vede.
	 * @param idTo - Identifika�n� ��slo hospody/p�ekladi�t�, do kter�ho cesta vede.
	 * @param vzdalenost - Vzd�lenost v kilometrech mezi dv�ma m�sty.
	 */
	public Cesta(int idFrom, int idTo, double vzdalenost) {
		this.idFrom = idFrom;
		this.idTo = idTo;
		this.vzdalenost = vzdalenost;
	}
	
	public int getIdFrom() {
		return this.idFrom;
	}
	
	public int getIdTo() {
		return this.idTo;
	}
	
	public double getVzdalenost() {
		return this.vzdalenost;
	}
	
	@Override
	public String toString() {
		return (""+this.idFrom+"-"+this.idTo+":"+this.vzdalenost);
	}
	
	@Override
	public int compareTo(Cesta c) {
		if(this.vzdalenost > c.getVzdalenost()) return 1;
		else if(this.vzdalenost < c.getVzdalenost()) return -1;
		else return 0;
	}
}
