package semestralka;

/**
 * Tøída pro uložení údajù o cestì mezi dvìma body (hospodami, pøekladišti...).
 * @author Vlada47 & Shag0n
 *
 */
public class Cesta implements Comparable<Cesta> {
	
	private int idFrom;
	private int idTo;
	private double vzdalenost;
	
	/**
	 * Konstruktor objektu.
	 * @param idFrom - Identifikaèní èíslo hospody/pøekladištì, ze kterého cesta vede.
	 * @param idTo - Identifikaèní èíslo hospody/pøekladištì, do kterého cesta vede.
	 * @param vzdalenost - Vzdálenost v kilometrech mezi dvìma místy.
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
