package semestralka;

public class Hospoda {

	Pozice pozice;
	int pocetPlnychSudu;
	int pocetPrazdnychSudu;

	public Hospoda(Pozice pozice) {

		this.pozice = pozice;
	}

	public Pozice getPosition(){
		return pozice;
	}
	
}
