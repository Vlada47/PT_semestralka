package semestralka;

import java.util.ArrayList;

public class Pivovar {
	
	private final int DENNI_PRODUKCE = 7000;
	public final int OBJEM_SUDU = 50;

	private Pozice pozice;
	private int stavPiva;
	private ArrayList<Vozidlo> dostupnaVozidla;
	private ArrayList<Vozidlo> vozidlaNaCeste;

	private static Pivovar instance;

	private Pivovar() {
		this.stavPiva = this.DENNI_PRODUKCE;
		this.pozice = new Pozice(250,250);
		this.dostupnaVozidla = new ArrayList<Vozidlo>();
		this.vozidlaNaCeste = new ArrayList<Vozidlo>();
		
		for(int i = 0; i < 100; i++) {
	//		this.dostupnaVozidla.add(new Vozidlo("vozidlo"+i));
		}
	}

	public static Pivovar getInstance() {
		if (instance == null) {
			instance = new Pivovar();
		}
		return instance;
	}
	
	public Pozice getPozice() {
		return this.pozice;
	}
	
	public int getStavPiva() {
		return this.stavPiva;
	}
	
	public void vyprodukujPivo() {
		this.stavPiva += this.DENNI_PRODUKCE;
	}
	
	public void odvezSudyDoPrekladiste(int pocetSudu) {
		int mnozstvi = pocetSudu * this.OBJEM_SUDU;
		
		if(mnozstvi <= this.stavPiva) {
			this.stavPiva -= mnozstvi;
		}
		else {
			System.out.println("Nedostatek piva v pivovaru!");
		}
	}
	
	public void odvezPivoDoHospody(int pocetHektolitru) {
		if(pocetHektolitru <= this.stavPiva) {
			this.stavPiva -= pocetHektolitru;
		}
		else {
			System.out.println("Nedostatek piva v pivovaru!");
		}
	}
}