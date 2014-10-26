package semestralka;

import java.util.ArrayList;

public class Pivovar extends Budova {
	
	public final int DENNI_PRODUKCE = 7000;

	private Pozice pozice;
	private int stavPiva;
	private ArrayList<Kamion> dostupneKamiony;
	private ArrayList<Kamion> kamionyNaCeste;
	private ArrayList<Cisterna> dostupneCisterny;
	private ArrayList<Cisterna> cisternyNaCeste;

	private static Pivovar instance;

	private Pivovar() {
		this.stavPiva = this.DENNI_PRODUKCE;
		this.pozice = new Pozice(StaticData.SOURADNICE_PIVOVARU_X,StaticData.SOURADNICE_PIVOVARU_Y);
		
		this.stavPiva = 7000;
		
		this.dostupneKamiony = new ArrayList<Kamion>();
		this.kamionyNaCeste = new ArrayList<Kamion>();
		this.dostupneCisterny = new ArrayList<Cisterna>();
		this.cisternyNaCeste = new ArrayList<Cisterna>();
		
		for(int i = 0; i < StaticData.POCET_KAMIONU; i++) {
			this.dostupneKamiony.add(new Kamion(i));
		}
		
		for(int i = 0; i < StaticData.POCET_CISTEREN; i++) {
			this.dostupneCisterny.add(new Cisterna(i));
		}
	}

	public static Pivovar getInstance() {
		if (instance == null) {
			instance = new Pivovar();
		}
		return instance;
	}
	
	public int getStavPiva() {
		return this.stavPiva;
	}
	
	public void vyprodukujPivo() {
		this.stavPiva += this.DENNI_PRODUKCE;
	}
	
	public void odvezSudyDoPrekladiste(int pocetSudu) {
		int mnozstvi = pocetSudu * StaticData.OBJEM_SUDU;
		
		if(mnozstvi <= this.stavPiva) {
			this.stavPiva -= mnozstvi;
		}
		else {
			System.out.println("Nedostatek piva v pivovaru!");
			System.exit(1);
		}
	}
	
	public void odvezPivoDoHospody(int pocetHektolitru) {
		if(pocetHektolitru <= this.stavPiva) {
			this.stavPiva -= pocetHektolitru;
		}
		else {
			System.out.println("Nedostatek piva v pivovaru!");
			System.exit(1);
		}
	}
}