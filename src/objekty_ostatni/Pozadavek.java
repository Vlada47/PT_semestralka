package objekty_ostatni;

public class Pozadavek {
	
	private final int prekladiste;
	private final int pocetSudu;
	
	public Pozadavek(int prekladiste, int pocetSudu) {
		this.prekladiste = prekladiste;
		this.pocetSudu = pocetSudu;
	}
	
	public int getPrekladiste() {
		return this.prekladiste;
	}
	
	public int getPocetSudu() {
		return this.pocetSudu;
	}
}
