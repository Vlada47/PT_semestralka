package semestralka;

/**
 * T��da pro vytv��en� objekt� uchov�vaj�c�ch sou�adnice n�jak�ho m�sta v oblasti (hospoda / p�ekladi�t� / pivovar).
 * @author Vlada47 & Shag0n
 *
 */
public class Pozice {

	int x;
	int y;
	
	/**
	 * Kontruktor objektu.
	 * @param x - Sou�adnice na ose x.
	 * @param y - Sou�adnice na ose y.
	 */
	public Pozice(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return ""+x +" "+ y;
	}
}
