package semestralka;

/**
 * 
 * @author Vlada47 & Shag0n
 *
 */
public class Prekladiste {
	
	private final int MAX_SUDU = 2000;

	Pozice pozice = new Pozice(250,250);
	int pocetPlnychSudu;
	int pocetPrazdnychSudu;

	public Prekladiste(Pozice pozice) {
		this.pozice = pozice;
	}

	public Pozice getPosition(){
		return pozice;
	}
	
}
