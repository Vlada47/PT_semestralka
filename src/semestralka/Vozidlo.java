package semestralka;

public abstract class Vozidlo {
	
	public abstract void ujedHodinovouVzdalenost();
	
	public abstract void setVzdalenost(double kilometry);
	
	public abstract double getVzdalenost();
	
	public abstract void nalozObjednavku(int pocet);
	
	public abstract void vylozObjednavku(int pocet);
	
	public abstract int getStavPiva();
}
