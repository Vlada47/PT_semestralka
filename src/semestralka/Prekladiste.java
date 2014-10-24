package semestralka;

/**
 * 
 * @author Vlada47 & Shag0n
 *
 */
public class Prekladiste extends Budova{

	private final int MAX_SUDU = 2000;

	int ID;
	Pozice pozice = new Pozice(250, 250);
	int pocetPlnychSudu;
	int pocetPrazdnychSudu;

	public Prekladiste(int ID, Pozice pozice) {
		this.ID = ID;
		this.pozice = pozice;
	}

	public Pozice getPosition() {
		return pozice;
	}

}
