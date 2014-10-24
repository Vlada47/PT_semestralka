package semestralka;

public class Hospoda {

	Pozice pozice;
	int ID;
	int pocetPlnychSudu;
	int pocetPrazdnychSudu;
	int idPrekladiste;
	int casObjednani;
	int mnozstviObjednat;

	public Hospoda(int ID, Pozice pozice) {
		this.ID = ID;
		this.pozice = pozice;
	}

	public Pozice getPosition() {
		return pozice;
	}

	@Override
	public String toString() {
		return "Hospoda [pozice=" + pozice + ", ID=" + ID + "]";
	}

}
