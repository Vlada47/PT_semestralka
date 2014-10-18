package semestralka;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Tøída obsahující statické metody pro zápis generovanıch dat do souboru a pøípadné jejich ètení.
 * @author Vlada47 & Shag0n
 *
 */
public class InputOutput {

	public static final String SOURADNICE_PREKLADIST_SOUBOR = "data\\souradnicePrekladist.txt";
	public static final String SOURADNICE_HOSPOD_SOUBOR = "data\\souradniceHospod.txt";
	public static final String CESTY_PREKLADIST_SOUBOR = "data\\cestyPrekladist.txt";
	public static final String CESTY_HOSPOD_SOUBOR = "data\\cestyHospod.txt";
	
	/**
	 * Metoda pro zápis souøadnic pøekladiš do souboru.
	 * @param prekladiste - Pole obsahující objekty se souøadnicemi pøekladiš.
	 */
	public static void zapisPrekladiste(Pozice[] prekladiste) {
		try {
			BufferedWriter bwFile = new BufferedWriter(new FileWriter(SOURADNICE_PREKLADIST_SOUBOR));
			for (int i = 0; i < prekladiste.length; i++) {
			bwFile.write(i+":"+prekladiste[i].toString());
			bwFile.newLine();
			}
			bwFile.close();
		} catch (Exception e) {
			System.out.println("I/O error in: " + SOURADNICE_PREKLADIST_SOUBOR + "\n" + e.getMessage());
		}
	}
	
	/**
	 * Metoda pro zápis souøadnic hospod do souboru.
	 * @param hospody - Pole obsahující objekty se souøadnicemi hospod.
	 */
	public static void zapisHospody(Pozice[] hospody) {
		try {
			BufferedWriter bwFile = new BufferedWriter(new FileWriter(SOURADNICE_HOSPOD_SOUBOR));
			for (int i = 0; i < hospody.length; i++) {
			bwFile.write(i+":"+hospody[i].toString());
			bwFile.newLine();
			}
			bwFile.close();
		} catch (Exception e) {
			System.out.println("I/O error in: " + SOURADNICE_HOSPOD_SOUBOR + "\n" + e.getMessage());
		}
	}
	
	/**
	 * Metoda zapisující údaje o cestách mezi pøekladišti a hospodami.
	 * @param cestyPrekladist - Pole obsahující objekty s údaji o cestách mezi pøekladišti a hospodami.
	 */
	public static void zapisCestPrekladist(Cesta[][] cestyPrekladist) {
		try {
			BufferedWriter bwFile = new BufferedWriter(new FileWriter(CESTY_PREKLADIST_SOUBOR));
			for (int i = 0; i < cestyPrekladist.length; i++) {
				for (int j = 0; j < cestyPrekladist[i].length; j++) {
					bwFile.write(cestyPrekladist[i][j].toString());
					bwFile.newLine();
				}
			}
			bwFile.close();
		} catch (IOException e) {
			System.out.println("I/O error in: " + CESTY_PREKLADIST_SOUBOR + "\n" + e.getMessage());
		}
	}
	
	/**
	 * Metoda zapisující údaje o cestách mezi hospodami.
	 * @param cestyPrekladist - Pole obsahující objekty s údaji o cestách mezi hospodami.
	 */
	public static void zapisCestHospod(Cesta[][] cestyHospod) {
		try {
			BufferedWriter bwFile = new BufferedWriter(new FileWriter(CESTY_HOSPOD_SOUBOR));
			for (int i = 0; i < cestyHospod.length; i++) {
				for (int j = 0; j < cestyHospod[i].length; j++) {
					bwFile.write(cestyHospod[i][j].toString());
					bwFile.newLine();
				}
			}
			bwFile.close();
		} catch (Exception e) {
			System.out.println("I/O error in: " + CESTY_HOSPOD_SOUBOR + "\n" + e.getMessage());
		}
	}
	
}
