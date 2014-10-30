package semestralka;

public class HospodaTankova extends Budova {
	
	Pozice pozice;
	int ID;
	private int stavPiva;
	public int casObjednani;
	public int mnozstviObjednat;
	
	public HospodaTankova(int ID, Pozice pozice) {
		this.ID = ID;
		this.pozice = pozice;
		this.stavPiva = 6;
	}
	
	public void pridejHektolitr() {
		this.stavPiva++;
		this.mnozstviObjednat--;
	}
	
	public void spotrebuj(int mnozstvi){
		this.stavPiva-=mnozstvi;	
	}
	
	public int getStavPiva() {
		return this.stavPiva;
	}

	public Pozice getPosition() {
		return pozice;
	}

	@Override
	public String toString() {
		return "Hospoda [pozice=" + pozice + ", ID=" + ID + "]";
	}

}
