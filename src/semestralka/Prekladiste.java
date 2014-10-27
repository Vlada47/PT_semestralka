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
	private ArrayList<NakladniAuto> dostupnaAuta;
	private ArrayList<NakladniAuto> autaNaCeste;
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
			
			boolean provedeno = nalozObjednavku(o);
			
			if(provedeno) {
				posliObjednavku(o);
			}
			this.objednavky.remove(0);
		}
	}
	
	private boolean nalozObjednavku(Objednavka o) {
		if(!(this.dostupnaAuta.isEmpty())) {
			NakladniAuto a = this.dostupnaAuta.get(0);
			boolean provedeno = a.nalozPlneSudy(o.getMnozstvi());
			if(provedeno) return true;
			else return false;
		}
		else return false;
	}
	
	private void posliObjednavku(Objednavka o) {
		NakladniAuto a = this.dostupnaAuta.get(0);
		ArrayList<Integer> cesta = Matice.getNejkratsiCesta(this.ID+StaticData.POCET_HOSPOD, o.getIdObjednavajiciho());
		double v = Matice.getDelkaNejkratsiCesty(cesta);
		a.setVzdalenost(v);
		this.autaNaCeste.add(a);
		this.dostupnaAuta.remove(0);
	}
	
	public int getID() {
		return this.ID;
	}

	public Pozice getPosition() {
		return pozice;
	}

}
