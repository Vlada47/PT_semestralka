package semestralka;

import java.util.ArrayList;

public class NakladniAuto {
	
	public final double RYCHLOST = 70;
	public final int KAPACITA = 30;
	
	private int ID;
	private int pocetPlnychSudu;
	private int pocetPrazdnychSudu;
	private ArrayList<Integer> prazdnychSuduKNalozeni;
	private ArrayList<Integer> plnychSuduKVylozeni;
	private double vzdalenost;
	private ArrayList<ArrayList<Integer>> cesty;
	private int indexVykonavaneCesty = 0;
	
	public NakladniAuto(int ID) {
		this.ID = ID;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public void addCesta(int odkud, int kam, int prazdneSudy, int plneSudy) {
		ArrayList<Integer> cesta = Matice.getNejkratsiCesta(odkud, kam);
		this.cesty.add(cesta);
		this.prazdnychSuduKNalozeni.add(prazdneSudy);
		this.plnychSuduKVylozeni.add(plneSudy);
	}
	
	public void nalozPrazdneSudy(int pocet) {
		this.vzdalenost += (pocet * StaticData.HODIN_NA_SUD) * this.RYCHLOST;
		this.pocetPrazdnychSudu += pocet;
	}
	
	public void vylozPrazdneSudy(int pocet) {
		this.vzdalenost += (pocet * StaticData.HODIN_NA_SUD) * this.RYCHLOST;
		this.pocetPrazdnychSudu -= pocet;
	}
	
	public void nalozPlneSudy(int pocet) {
		this.vzdalenost += (pocet * StaticData.HODIN_NA_SUD) * this.RYCHLOST;
		this.pocetPlnychSudu += pocet;
	}
	
	public void vylozPlneSudy(int pocet) {
		this.vzdalenost += (pocet * StaticData.HODIN_NA_SUD) * this.RYCHLOST;
		this.pocetPlnychSudu -= pocet;
	}
}
