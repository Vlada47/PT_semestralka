package semestralka;

public class Vozidlo {
	
	private String id;	
	private double rychlost;
	private double vzdalenost;
	private boolean jeVCili;
	private int pocetSudu;
	
	public Vozidlo(String id) {
		this.id = id;
	}
	
	public void ujedHodinovouVzdalenost() {
		this.vzdalenost -= rychlost;
		
		if(vzdalenost <= 0.0) {
			jeVCili = true;
		}
	}
	
	public void setVzdalenost(double kilometry) {
		this.vzdalenost = kilometry;
	}
	
	public double getVzdalenost() {
		return this.vzdalenost;
	}
	
	public void nalozSudy(int pocet) {
		this.pocetSudu += pocet;
	}
	
	public void vylozSudy(int pocet) {
		this.pocetSudu -= pocet;
	}
	
	public int getPocetSudu() {
		return this.pocetSudu;
	}
}
