package semestralka;

import java.util.ArrayList;

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
}
