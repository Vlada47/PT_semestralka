package semestralka;

/**
 * Tøída pro vytváøení objektù uchovávajících souøadnice nìjakého místa v oblasti (hospoda / pøekladištì / pivovar).
 * @author Vlada47 & Shag0n
 *
 */
public class Pozice {

	int x;
	int y;
	
	/**
	 * Kontruktor objektu.
	 * @param x - Souøadnice na ose x.
	 * @param y - Souøadnice na ose y.
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
