package semestralka;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * T��da obsahuj�c� statick� metody pro z�pis generovan�ch dat do souboru a
 * p��padn� jejich �ten�.
 * 
 * @author Vlada47 & Shag0n
 *
 */
public class InputOutput {

	public static final String SOURADNICE_PREKLADIST_SOUBOR = "data\\souradnicePrekladist.txt";
	public static final String SOURADNICE_HOSPOD_SOUBOR = "data\\souradniceHospod.txt";
	public static final String CESTY_PIVOVARU_SOUBOR = "data\\cestyPivovaru.txt";
	public static final String CESTY_PREKLADIST_SOUBOR = "data\\cestyPrekladist.txt";
	public static final String CESTY_HOSPOD_SOUBOR = "data\\cestyHospod.txt";
	public static final String MATICE_CEST_SOUBOR = "data\\maticeCest.txt";
	
	public static void zapisSouradniceACestyDoSouboru() {
		InputOutput.zapisSouradnicPrekladiste(Generator.souradnicePrekladist);
		InputOutput.zapisSouradnicHospod(Generator.souradniceHospod);
		InputOutput.zapisCestPivovaru(Generator.cestyPivovaru);
		InputOutput.zapisCestPrekladist(Generator.cestyPrekladist);
		InputOutput.zapisCestHospod(Generator.cestyHospod);
		System.out.println("Souradnice a cesty zapsany do souboru...");
	}

	/**
	 * Metoda pro z�pis sou�adnic p�ekladi�� do souboru.
	 * 
	 * @param prekladiste
	 *            - Pole obsahuj�c� objekty se sou�adnicemi p�ekladi��.
	 */
	private static void zapisSouradnicPrekladiste(Pozice[] prekladiste) {
		try {
			BufferedWriter bwFile = new BufferedWriter(new FileWriter(SOURADNICE_PREKLADIST_SOUBOR));
			for (int i = 0; i < prekladiste.length; i++) {
				bwFile.write(i + " " + prekladiste[i].toString());
				bwFile.newLine();
			}
			bwFile.close();
		} catch (IOException e) {
			System.out.println("I/O error in: " + SOURADNICE_PREKLADIST_SOUBOR + "\n" + e.getMessage());
		}
	}

	/**
	 * Metoda pro z�pis sou�adnic hospod do souboru.
	 * 
	 * @param hospody
	 *            - Pole obsahuj�c� objekty se sou�adnicemi hospod.
	 */
	private static void zapisSouradnicHospod(Pozice[] hospody) {
		try {
			BufferedWriter bwFile = new BufferedWriter(new FileWriter(SOURADNICE_HOSPOD_SOUBOR));
			for (int i = 0; i < hospody.length; i++) {
				bwFile.write(i + " " + hospody[i].toString());
				bwFile.newLine();
			}
			bwFile.close();
		} catch (IOException e) {
			System.out.println("I/O error in: " + SOURADNICE_HOSPOD_SOUBOR + "\n" + e.getMessage());
		}
	}
	
	private static void zapisCestPivovaru(Cesta[] cestyPivovaru) {
		try {
			BufferedWriter bwFile = new BufferedWriter(new FileWriter(CESTY_PIVOVARU_SOUBOR));
			for (int i = 0; i < cestyPivovaru.length; i++) {
			bwFile.write(cestyPivovaru[i].toString());
			bwFile.newLine();
			}
			bwFile.close();
		} catch (IOException e) {
			System.out.println("I/O error in: " + CESTY_PIVOVARU_SOUBOR + "\n" + e.getMessage());
		}
	}
	
	/**
	 * Metoda zapisuj�c� �daje o cest�ch mezi p�ekladi�ti a hospodami.
	 * 
	 * @param cestyPrekladist
	 *            - Pole obsahuj�c� objekty s �daji o cest�ch mezi p�ekladi�ti a
	 *            hospodami.
	 */
	private static void zapisCestPrekladist(Cesta[][] cestyPrekladist) {
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
	 * Metoda zapisuj�c� �daje o cest�ch mezi hospodami.
	 * 
	 * @param cestyPrekladist
	 *            - Pole obsahuj�c� objekty s �daji o cest�ch mezi hospodami.
	 */
	private static void zapisCestHospod(Cesta[][] cestyHospod) {
		try {
			BufferedWriter bwFile = new BufferedWriter(new FileWriter(CESTY_HOSPOD_SOUBOR));
			for (int i = 0; i < cestyHospod.length; i++) {
				for (int j = 0; j < cestyHospod[i].length; j++) {
					bwFile.write(cestyHospod[i][j].toString());
					bwFile.newLine();
				}
			}
			bwFile.close();
		} catch (IOException e) {
			System.out.println("I/O error in: " + CESTY_HOSPOD_SOUBOR + "\n" + e.getMessage());
		}
	}
	
	public static void zapisMaticeNejkratsichCest(int[][] maticeNejkratsichCest) {
		try {
			BufferedWriter bwFile = new BufferedWriter(new FileWriter(MATICE_CEST_SOUBOR));
			for(int i = 0; i < maticeNejkratsichCest.length; i++) {
				for(int j = 0; j <maticeNejkratsichCest[i].length; j++) {
					bwFile.write(Integer.toString(maticeNejkratsichCest[i][j])+" ");
				}
				bwFile.newLine();
			}
			bwFile.close();
		}
		catch(IOException e) {
			System.out.println("I/O error in: " + MATICE_CEST_SOUBOR + "\n" + e.getMessage());
		}
	}
	
	public static int[][] nactiMaticiNejkratsichCest() {
		String radka;
		int[][] maticeNejkratsichCest = new int[StaticData.POCET_HOSPOD+StaticData.POCET_PREKLADIST+1][StaticData.POCET_HOSPOD+StaticData.POCET_PREKLADIST+1];
		
		try{
			BufferedReader bfr = new BufferedReader(new FileReader(MATICE_CEST_SOUBOR));
			
			for(int i = 0; i < maticeNejkratsichCest.length; i++) {
				radka = bfr.readLine();
				String[] hodnoty = radka.split(" ");
				for(int j = 0; j < maticeNejkratsichCest[i].length; j++) {
					maticeNejkratsichCest[i][j] = Integer.parseInt(hodnoty[j]); 
				}
			}
			
			bfr.close();
		}
		catch(IOException e) {
			System.out.println("I/O error in: " + MATICE_CEST_SOUBOR + "\n" + e.getMessage());
		}
		
		return maticeNejkratsichCest;
	}

	/**
	 * Metoda pro na�ten� sou�adnic hospod ze souboru.
	 */

	public static Hospoda[] nactiHospody() {

		String radka;
		Hospoda[] hospody = new Hospoda[StaticData.POCET_HOSPOD];

		try {
			BufferedReader bfr = new BufferedReader(new FileReader(SOURADNICE_HOSPOD_SOUBOR));

			for (int i = 0; i < StaticData.POCET_HOSPOD; i++) {
				radka = bfr.readLine();
				hospody[i] = vytvorHospodu(radka);
			}
			bfr.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return hospody;
	}

	/**
	 * Metoda pro na�ten� sou�adnic p�ekladi�� ze souboru.
	 */
	public static Prekladiste[] nactiPrekladiste() {

		String radka;
		Prekladiste[] prekladiste = new Prekladiste[StaticData.POCET_PREKLADIST];

		try {
			BufferedReader bfr = new BufferedReader(new FileReader(SOURADNICE_PREKLADIST_SOUBOR));

			for (int i = 0; i < StaticData.POCET_PREKLADIST; i++) {
				radka = bfr.readLine();
				prekladiste[i] = vytvorPrekladiste(radka);
			}
			bfr.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return prekladiste;
	}
	
	/**
	 * Metoda pro na�ten� cest pivovaru ze souboru.
	 * @return
	 */
	public static Cesta[] nactiCestyPivovaru() {
		String radka;
		Cesta[] cestyPivovaru = new Cesta[StaticData.POCET_CEST_PIVOVARU];
		
		try{
			BufferedReader bfr = new BufferedReader(new FileReader(CESTY_PIVOVARU_SOUBOR));
			
			for(int i = 0; i < StaticData.POCET_CEST_PIVOVARU; i++) {
				radka = bfr.readLine();
				cestyPivovaru[i] = vytvorCestu(radka);
			}
			
			bfr.close();
		}
		catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		return cestyPivovaru;
	}

	/**
	 * Metoda pro na�ten� cest p�ekladi�� ze souboru.
	 */
	public static Cesta[][] nactiCestyPrekladiste() {

		String radka;
		Cesta[][] cestyPrekladiste = new Cesta[StaticData.POCET_PREKLADIST][StaticData.POCET_CEST_PREKLADISTE];

		try {
			BufferedReader bfr = new BufferedReader(new FileReader(CESTY_PREKLADIST_SOUBOR));

			
			for (int j = 0; j < StaticData.POCET_PREKLADIST; j++) {
				for (int i = 0; i < StaticData.POCET_CEST_PREKLADISTE; i++) {
					radka = bfr.readLine();
					cestyPrekladiste[j][i] = vytvorCestu(radka);
				}
			}
			bfr.close();
				
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return cestyPrekladiste;
	}

	/**
	 * Metoda pro na�ten� cest hospod ze souboru.
	 */
	public static Cesta[][] nactiCestyHospod() {

		String radka;
		Cesta[][] cestyHospod = new Cesta[StaticData.POCET_HOSPOD][StaticData.POCET_CEST_HOSPODY];

		try {
			BufferedReader bfr = new BufferedReader(new FileReader(CESTY_HOSPOD_SOUBOR));

			for (int j = 0; j < StaticData.POCET_HOSPOD; j++) {
				for (int i = 0; i < StaticData.POCET_CEST_HOSPODY; i++) {
					radka = bfr.readLine();
					cestyHospod[j][i] = vytvorCestu(radka);
				}
			}
			bfr.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return cestyHospod;
	}

	/**
	 * Metoda pro vytvo�en� objektu hospody.
	 * 
	 * * @param cvsRadek - String s na�ten�mi hodotami.
	 */
	public static Hospoda vytvorHospodu(String cvsRadek) {
		String[] podRetezce = cvsRadek.split(" ");
		Hospoda hospoda = new Hospoda(Integer.parseInt(podRetezce[0]), new Pozice(Integer.parseInt(podRetezce[1]),
				Integer.parseInt(podRetezce[2])));
		return hospoda;
	}

	/**
	 * Metoda pro vytvo�en� objektu p�ekladi�t�.
	 * 
	 * * @param cvsRadek - String s na�ten�mi hodnotami.
	 */
	public static Prekladiste vytvorPrekladiste(String cvsRadek) {
		String[] podRetezce = cvsRadek.split(" ");
		Prekladiste prekladiste = new Prekladiste(Integer.parseInt(podRetezce[0]), new Pozice(
				Integer.parseInt(podRetezce[1]), Integer.parseInt(podRetezce[2])));
		return prekladiste;
	}

	/**
	 * Metoda pro vytvo�en� objektu cesta.
	 * 
	 * * @param cvsRadek - String s na�ten�mi hodnotami.
	 */
	public static Cesta vytvorCestu(String cvsRadek) {
		String[] podRetezce = cvsRadek.split(" ");
		Cesta cesta = new Cesta(Integer.parseInt(podRetezce[0]), Integer.parseInt(podRetezce[1]),
				Double.parseDouble(podRetezce[2]));
		return cesta;
	}

}
