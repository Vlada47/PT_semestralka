package semestralka;

import java.util.ArrayList;

/**
 * 
 * @author Vlada47 & Shag0n
 *
 */
public class Prekladiste extends Budova{

	private final int MAX_SUDU = 2000;

	private int ID;
	private Pozice pozice;
	private int pocetPlnychSudu;
	private int pocetPrazdnychSudu;
	public ArrayList<NakladniAuto> dostupnaAuta;
	public ArrayList<NakladniAuto> autaNaCeste;
	public ArrayList<Objednavka> objednavky;

	public Prekladiste(int ID, Pozice pozice) {
		this.ID = ID;
		this.pozice = pozice;
		this.pocetPlnychSudu = 2000;
		this.pocetPrazdnychSudu = 0;
		
		this.dostupnaAuta = new ArrayList<NakladniAuto>();
		this.autaNaCeste = new ArrayList<NakladniAuto>();
		this.objednavky = new ArrayList<Objednavka>();
		
		for(int i = 0; i < StaticData.POCET_AUT; i++) {
			this.dostupnaAuta.add(new NakladniAuto(i));
		}
	}
	
	public void pridejObjednavku(Objednavka o) {
		this.objednavky.add(o);
	}
	
	public void vyridObjednavku() {
		Objednavka o = this.objednavky.get(0);
		if(!(this.dostupnaAuta.isEmpty()) && (o.getMnozstvi() <= this.pocetPlnychSudu)) {
			if(nalozObjednavku(o)) {
				posliObjednavku(o);
			}
			System.out.println("Objednavka z prekladiste "+this.ID+" do hospody "+o.getIdObjednavajiciho()+" odeslana.");
			this.objednavky.remove(0);
		}
	}
	
	private boolean nalozObjednavku(Objednavka o) {
		NakladniAuto a = this.dostupnaAuta.get(0);
		if(a.nalozPlneSudy(o.getMnozstvi())) {
			this.pocetPlnychSudu -= o.getMnozstvi();
			return true;
		}
		else return false;
	}
	
	private void posliObjednavku(Objednavka o) {
		NakladniAuto a = this.dostupnaAuta.get(0);
		ArrayList<Integer> cesta = Matice.getNejkratsiCesta(this.ID+StaticData.POCET_HOSPOD, o.getIdObjednavajiciho());
		double v = Matice.getDelkaNejkratsiCesty(cesta);
		a.setVzdalenost(v);
		a.setStartCil(o.idPrekladiste(), o.getIdObjednavajiciho());
		this.autaNaCeste.add(a);
		this.dostupnaAuta.remove(0);
	}
	
	public void prijmiPrazdneSudy(int pocet) {
		this.pocetPrazdnychSudu += pocet;
	}
	
	public int getID() {
		return this.ID;
	}

	public Pozice getPosition() {
		return pozice;
	}
	
	public int getPlneSudy() {
		return this.pocetPlnychSudu;
	}
	
	public int getPrazdneSudy() {
		return this.pocetPrazdnychSudu;
	}

}
