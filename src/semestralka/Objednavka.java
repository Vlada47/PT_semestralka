package semestralka;

import java.util.Random;

public class Objednavka {

	static Random rnd = new Random();

	private final int casObednani;
	private final int idObjednavajiciho;
	private final int indexObjednavajiciho;
	private final int mnozstvi;
	private final int idPrekladiste;

	public Objednavka(int casObednani, int idObjednavajiciho, int indexObjednavajiciho, int idPrekladiste, int mnozstvi) {
		super();
		this.casObednani = casObednani;
		this.idObjednavajiciho = idObjednavajiciho;
		this.indexObjednavajiciho = indexObjednavajiciho;
		this.idPrekladiste = idPrekladiste;
		this.mnozstvi = mnozstvi;
	}
	
	public int getCasObednani() {
		return casObednani;
	}

	public int getIdObjednavajiciho() {
		return idObjednavajiciho;
	}
	
	public int getIndexObjednavajiciho() {
		return this.indexObjednavajiciho;
	}

	public int getMnozstvi() {
		return mnozstvi;
	}

	public int idPrekladiste() {
		return idPrekladiste;
	}

	public static void generujRozpisObjednavek() {
		
		for(int i = 0; i < Simulace.tankoveHospody.length; i++) {
			HospodaTankova hospoda = Simulace.tankoveHospody[i];
			
			int generaceCasu = rnd.nextInt(101);
			int generaceMnozstvi = rnd.nextInt(101);
			int mnozstvi;
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
			
			if (generaceMnozstvi < 25)
				mnozstvi = 1;
			else if (generaceMnozstvi < 50)
				mnozstvi = 2;
			else if (generaceMnozstvi < 71)
				mnozstvi = 3;
			else if (generaceMnozstvi < 86)
				mnozstvi = 4;
			else if (generaceMnozstvi < 96)
				mnozstvi = 5;
			else
				mnozstvi = 6;
			
			hospoda.casObjednani = cas;
			hospoda.spotrebuj(mnozstvi);
			hospoda.mnozstviObjednat = mnozstvi;
		}
		
		for(int i = 0; i < Simulace.sudoveHospody.length; i++) {
			HospodaSudova hospoda = Simulace.sudoveHospody[i];
			
			int generaceCasu = rnd.nextInt(101);
			int generaceMnozstvi = rnd.nextInt(101);
			int mnozstvi;
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
			
			if (generaceMnozstvi < 25)
				mnozstvi = 1;
			else if (generaceMnozstvi < 50)
				mnozstvi = 2;
			else if (generaceMnozstvi < 71)
				mnozstvi = 3;
			else if (generaceMnozstvi < 86)
				mnozstvi = 4;
			else if (generaceMnozstvi < 96)
				mnozstvi = 5;
			else
				mnozstvi = 6;
			
			hospoda.casObjednani = cas;
			hospoda.spotrebuj(mnozstvi);
			hospoda.mnozstviObjednat = mnozstvi;
		}
	}

	@Override
	public String toString() {
		return "Objednavka [casObednani=" + casObednani + ", idObjednavajiciho=" + idObjednavajiciho + ", mnozstvi="
				+ mnozstvi + ", idPrekladiste=" + idPrekladiste + "]";
	}

}
