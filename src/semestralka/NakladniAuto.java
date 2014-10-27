package semestralka;

public class NakladniAuto {
	
	public final double RYCHLOST = 70;
	public final int KAPACITA = 30;
	
	private int ID;
	private int pocetPlnychSudu;
	private int pocetPrazdnychSudu;
	private double vzdalenost;
	
	public NakladniAuto(int ID) {
		this.ID = ID;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public void setVzdalenost(double vzdalenost) {
		this.vzdalenost = vzdalenost;
	}
	
	public double getVzdalenost() {
		return this.vzdalenost;
	}
	
	public boolean nalozPlneSudy(int pocet) {
		if(this.pocetPlnychSudu+this.pocetPrazdnychSudu+pocet <= 30) {
			this.pocetPlnychSudu += pocet;
			return true;
		}
		else {
			return false;
		}
	}
}
