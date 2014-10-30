package semestralka;

public class HospodaSudova extends Budova {

	Pozice pozice;
	int ID;
	private int pocetPlnychSudu;
	private int pocetPrazdnychSudu;
	public int idPrekladiste;
	public int casObjednani;
	public int mnozstviObjednat;

	public HospodaSudova(int ID, Pozice pozice) {
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
	
	public void spotrebuj(int mnozstvi){
		this.pocetPlnychSudu-=mnozstvi;
		this.pocetPrazdnychSudu+=mnozstvi;	
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
