package objekty_ostatni;

/**
 * Tøída pro vytváøení objektù uchovávajících souøadnice nìjakého místa v oblasti (hospoda / pøekladištì / pivovar).
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
	 * @param x - Souøadnice na ose x.
	 * @param y - Souøadnice na ose y.
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
