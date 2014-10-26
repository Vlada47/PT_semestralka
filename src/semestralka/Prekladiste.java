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

	public Prekladiste(int ID, Pozice pozice) {
		this.ID = ID;
		this.pozice = pozice;
		this.pocetPlnychSudu = 2000;
		this.pocetPrazdnychSudu = 0;
		
		this.dostupnaAuta = new ArrayList<NakladniAuto>();
		this.autaNaCeste = new ArrayList<NakladniAuto>();
		
		for(int i = 0; i < StaticData.POCET_AUT; i++) {
			this.dostupnaAuta.add(new NakladniAuto(i));
		}
	}
	
	public int getID() {
		return this.ID;
	}

	public Pozice getPosition() {
		return pozice;
	}

}
