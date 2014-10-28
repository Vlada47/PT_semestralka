package semestralka;

public class Hospoda {

	Pozice pozice;
	int ID;
	private int pocetPlnychSudu;
	private int pocetPrazdnychSudu;
	public int idPrekladiste;
	public int casObjednani;
	public int mnozstviObjednat;

	public Hospoda(int ID, Pozice pozice) {
		this.ID = ID;
		this.pozice = pozice;
		this.pocetPlnychSudu = 6;
		this.pocetPrazdnychSudu = 0;
	}
	
	public void odeberPrazdnySud() {
		this.pocetPrazdnychSudu--;
	}
	
	public void pridejPlnySud() {
		this.pocetPlnychSudu++;
		this.mnozstviObjednat--;
	}
	
	public int getPocetPrazdnychSudu() {
		return this.pocetPrazdnychSudu;
	}
	
	public int getPocetPlnychSudu() {
		return this.pocetPlnychSudu;
	}

	public Pozice getPosition() {
		return pozice;
	}

	@Override
	public String toString() {
		return "Hospoda [pozice=" + pozice + ", ID=" + ID + "]";
	}

}
