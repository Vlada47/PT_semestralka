package objekty_ostatni;

/**
 * T��da pro vytv��en� objekt� uchov�vaj�c�ch sou�adnice n�jak�ho m�sta v oblasti (hospoda / p�ekladi�t� / pivovar).
 * @author Vlada47 & Shag0n
 *
 */
public class Pozice {
	
	/**
	 * Souradnice x.
	 */
	public final int x;
	
	/**
	 * Souradnice y.
	 */
	public final int y;
	
	/**
	 * Kontruktor objektu.
	 * @param x - Sou�adnice na ose x.
	 * @param y - Sou�adnice na ose y.
	 */
	public Pozice(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Metoda na vraceni souradnice x.
	 * @return - souradnice x
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Metoda na vraceni souradnice y.
	 * @return - souradnice y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Metoda na vraceni vypisu atributu objektu.
	 */
	public String toString() {
		return x +" "+ y;
	}
}
