package semestralka;

import java.util.Random;

public class Objednavka {

	static Random rnd = new Random();

	private final int casObednani;
	private final int idObjednavajiciho;
	private final int mnozstvi;
	private final int idPrekladiste;

	public int getCasObednani() {
		return casObednani;
	}

	public int getIdObjednavajiciho() {
		return idObjednavajiciho;
	}

	public int getMnozstvi() {
		return mnozstvi;
	}
	
	public int idPrekladiste() {
		return idPrekladiste;
	}

	public Objednavka(int casObednani, int idObjednavajiciho, int idPrekladiste, int mnozstvi) {
		super();
		this.casObednani = casObednani;
		this.idObjednavajiciho = idObjednavajiciho;
		this.idPrekladiste = idPrekladiste;
		this.mnozstvi = mnozstvi;
	}

	public static void generujRozpisObjednavek() {

		for (Hospoda hospoda : Simulace.hospody) {

			int generaceCasu = rnd.nextInt(101);
			int generaceMnozstvi = rnd.nextInt(6)+1;
			int cas;

			if (generaceCasu < 34)
				cas = 10;
			else if (generaceCasu < 53)
				cas = 9;
			else if (generaceCasu < 71)
				cas = 11;
			else if (generaceCasu < 83)
				cas = 8;
			else if (generaceCasu < 93)
				cas = 12;
			else if (generaceCasu < 96)
				cas = 13;
			else if (generaceCasu < 98)
				cas = 14;
			else if (generaceCasu < 100)
				cas = 15;
			else
				cas = 16;
			
			hospoda.casObjednani=cas;
			hospoda.mnozstviObjednat=generaceMnozstvi;
		}
	}

	@Override
	public String toString() {
		return "Objednavka [casObednani=" + casObednani + ", idObjednavajiciho=" + idObjednavajiciho + ", mnozstvi="
				+ mnozstvi + ", idPrekladiste=" + idPrekladiste + "]";
	}

}
