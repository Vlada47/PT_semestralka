package io;

import generovani_dat.Generator;
import gui.Window;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import objekty_budovy.HospodaSudova;
import objekty_budovy.HospodaTankova;
import objekty_budovy.Prekladiste;
import objekty_ostatni.Cesta;
import objekty_ostatni.Pozice;
import semestralka.StaticData;

/**
 * T¯Ìda obsahujÌcÌ statickÈ metody pro z·pis generovan˝ch dat do souboru a
 * jejich p¯ÌpadnÈ ËtenÌ.
 * 
 * @author Vlada47 & Shag0n
 *
 */
public class InputOutput {
	
	/**
	 * Cesta k souboru se souradnicemi prekladist.
	 */
	public static final String SOURADNICE_PREKLADIST_SOUBOR = "data\\souradnicePrekladist.txt";
	
	/**
	 * Cesta k souboru se souradnicemi hospod.
	 */
	public static final String SOURADNICE_HOSPOD_SOUBOR = "data\\souradniceHospod.txt";
	
	/**
	 * Cesta k souboru s cestami pivovaru.
	 */
	public static final String CESTY_PIVOVARU_SOUBOR = "data\\cestyPivovaru.txt";
	
	/**
	 * Cesta k souboru s cestami prekladist.
	 */
	public static final String CESTY_PREKLADIST_SOUBOR = "data\\cestyPrekladist.txt";
	
	/**
	 * Cesta k souboru s cestami hospod.
	 */
	public static final String CESTY_HOSPOD_SOUBOR = "data\\cestyHospod.txt";
	
	/**
	 * Cesta k souboru s matici obsahujici informace o nejkratsich cestach.
	 */
	public static final String MATICE_CEST_SOUBOR = "data\\maticeCest.txt";
	
	/**
	 * Cesta k souboru, do ktereho se vypisuje log programu.
	 */
	public static final String LOG_SOUBOR = "data\\log.txt";
	
	/**
	 * Cesta k souboru, do ktereho se zapisuje statistika kamionu.
	 */
	public static final String STATISTIKA_KAMIONY = "data\\statistika_kamiony.csv";
	
	/**
	 * Cesta k souboru, do ktereho se zapisuje statistika cisteren.
	 */
	public static final String STATISTIKA_CISTERNY = "data\\statistika_cisterny.csv";
	
	/**
	 * Cesta k souboru, do ktereho se zapisuje statistika nakladnich aut.
	 */
	public static final String STATISTIKA_NAKLADNI_AUTA = "data\\statistika_nakladni_auta.csv";
	
	/**
	 * Cesta k souboru, do ktereho se zapisuje tankovych hospod.
	 */
	public static final String STATISTIKA_TANKOVE_HOSPODY = "data\\statistika_tankove_hospody.csv";
	
	/**
	 * Cesta k souboru, do ktereho se zapisuje statistika sudovych hospod.
	 */
	public static final String STATISTIKA_SUDOVE_HOSPODY = "data\\statistika_sudove_hospody.csv";
	
	/**
	 * Metoda postupne spoustejici privatni metody tridy pro zapis souradnic a cest budov.
	 */
	public static void zapisSouradniceACestyDoSouboru() {
		InputOutput.zapisSouradnicPrekladiste(Generator.souradnicePrekladist);
		InputOutput.zapisSouradnicHospod(Generator.souradniceHospod);
		InputOutput.zapisCestPivovaru(Generator.cestyPivovaru);
		InputOutput.zapisCestPrekladist(Generator.cestyPrekladist);
		InputOutput.zapisCestHospod(Generator.cestyHospod);
		System.out.println("Souradnice a cesty zapsany do souboru...");
	}

	/**
	 * Metoda pro z·pis sou¯adnic p¯ekladiöù do souboru.
	 * @param prekladiste - Pole obsahujÌcÌ objekty se sou¯adnicemi p¯ekladiöù.
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
			System.err.println("I/O error in: " + SOURADNICE_PREKLADIST_SOUBOR + "\n" + e.getMessage());
		}
	}

	/**
	 * Metoda pro z·pis sou¯adnic hospod do souboru.
	 * @param hospody - Pole obsahujÌcÌ objekty se sou¯adnicemi hospod.
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
			System.err.println("I/O error in: " + SOURADNICE_HOSPOD_SOUBOR + "\n" + e.getMessage());
		}
	}
	
	/**
	 * Metoda pro zapis cest pivovaru do souboru. Na kazdou dalsi radku se zapisuje vysledek metody toString ze tridy Cesta
	 * @param cestyPivovaru - Pole obsahujici objekty typu Cesta.
	 */
	private static void zapisCestPivovaru(Cesta[] cestyPivovaru) {
		try {
			BufferedWriter bwFile = new BufferedWriter(new FileWriter(CESTY_PIVOVARU_SOUBOR));
			for (int i = 0; i < cestyPivovaru.length; i++) {
				bwFile.write(cestyPivovaru[i].toString());
				bwFile.newLine();
			}
			bwFile.close();
		} catch (IOException e) {
			System.err.println("I/O error in: " + CESTY_PIVOVARU_SOUBOR + "\n" + e.getMessage());
		}
	}

	/**
	 * Metoda zapisujÌcÌ ˙daje o cest·ch mezi p¯ekladiöti a hospodami.
	 * @param cestyPrekladist - Pole obsahujÌcÌ objekty s ˙daji o cest·ch mezi p¯ekladiöti a hospodami.
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
			System.err.println("I/O error in: " + CESTY_PREKLADIST_SOUBOR + "\n" + e.getMessage());
		}
	}

	/**
	 * Metoda zapisujÌcÌ ˙daje o cest·ch mezi hospodami.
	 * @param cestyPrekladist - Pole obsahujÌcÌ objekty s ˙daji o cest·ch mezi hospodami.
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
			System.err.println("I/O error in: " + CESTY_HOSPOD_SOUBOR + "\n" + e.getMessage());
		}
	}
	
	/**
	 * Metoda pro zapsani matice uchovavajici informace o nejkratsich cestach mezi uzly v grafu.
	 * @param maticeNejkratsichCest - dvourozmerne pole uchovavajici danou matici
	 */
	public static void zapisMaticeNejkratsichCest(int[][] maticeNejkratsichCest) {
		try {
			BufferedWriter bwFile = new BufferedWriter(new FileWriter(MATICE_CEST_SOUBOR));
			for (int i = 0; i < maticeNejkratsichCest.length; i++) {
				for (int j = 0; j < maticeNejkratsichCest[i].length; j++) {
					bwFile.write(Integer.toString(maticeNejkratsichCest[i][j]) + " ");
				}
				bwFile.newLine();
			}
			bwFile.close();
		} catch (IOException e) {
			System.err.println("I/O error in: " + MATICE_CEST_SOUBOR + "\n" + e.getMessage());
		}
	}
	
	/**
	 * Metoda pro nacteni matice uchovavajici informace o nejkratsich cestach mezi uzly v grafu ze souboru.
	 * @return vraci dvourozmerne pole, ktere reprezentuje danou matici
	 */
	public static int[][] nactiMaticiNejkratsichCest() {
		String radka;
		int[][] maticeNejkratsichCest = new int[StaticData.POCET_HOSPOD + StaticData.POCET_PREKLADIST + 1][StaticData.POCET_HOSPOD
				+ StaticData.POCET_PREKLADIST + 1];

		try {
			BufferedReader bfr = new BufferedReader(new FileReader(MATICE_CEST_SOUBOR));

			for (int i = 0; i < maticeNejkratsichCest.length; i++) {
				radka = bfr.readLine();
				String[] hodnoty = radka.split(" ");
				for (int j = 0; j < maticeNejkratsichCest[i].length; j++) {
					maticeNejkratsichCest[i][j] = Integer.parseInt(hodnoty[j]);
				}
			}

			bfr.close();
		} catch (IOException e) {
			System.err.println("I/O error in: " + MATICE_CEST_SOUBOR + "\n" + e.getMessage());
		}

		return maticeNejkratsichCest;
	}

	/**
	 * Metoda pro naËtenÌ sou¯adnic sudovych hospod ze souboru. Cte postupne soubor a jako sudovou hospodu vezme vsechny, jejichz ID neni delitelne konstantou POMER_HOSPOD.
	 * Tyto hospody si pak uklada do pole, ktere vraci jako vysledek.
	 * @return vraci pole typu HospodaSudova
	 */
	public static HospodaSudova[] nactiSudoveHospody() {

		String radka;
		HospodaSudova[] hospody = new HospodaSudova[StaticData.POCET_HOSPOD - (StaticData.POCET_HOSPOD / StaticData.POMER_HOSPOD)];

		try {
			BufferedReader bfr = new BufferedReader(new FileReader(SOURADNICE_HOSPOD_SOUBOR));
			
			int index = 0;

			for (int i = 0; i < StaticData.POCET_HOSPOD; i++) {
				radka = bfr.readLine();
				if(i % StaticData.POMER_HOSPOD == 0) continue;
				hospody[index] = vytvorSudovouHospodu(radka);
				index++;
			}
			bfr.close();
		} catch (IOException e) {
			System.err.println("I/O error in: " + SOURADNICE_HOSPOD_SOUBOR + "\n" + e.getMessage());
			System.exit(1);
		}
		return hospody;
	}
	
	/**
	 * Metoda pro naËtenÌ sou¯adnic tankovych hospod ze souboru. Cte postupne soubor a jako tankovou hospodu vezme vsechny, jejichz ID je delitelne konstantou POMER_HOSPOD.
	 * Tyto hospody si pak uklada do pole, ktere vraci jako vysledek.
	 * @return vraci pole typu HospodaTankova
	 */
	public static HospodaTankova[] nactiTankoveHospody() {
		String radka;
		HospodaTankova[] hospody = new HospodaTankova[StaticData.POCET_HOSPOD / StaticData.POMER_HOSPOD];

		try {
			BufferedReader bfr = new BufferedReader(new FileReader(SOURADNICE_HOSPOD_SOUBOR));
			
			int index = 0;

			for (int i = 0; i < StaticData.POCET_HOSPOD; i++) {
				radka = bfr.readLine();
				if(i % StaticData.POMER_HOSPOD != 0) continue;
				hospody[index] = vytvorTankovouHospodu(radka);
				index++;
			}
			bfr.close();
		} catch (IOException e) {
			System.err.println("I/O error in: " + SOURADNICE_HOSPOD_SOUBOR + "\n" + e.getMessage());
			System.exit(1);
		}
		return hospody;
	}

	/**
	 * Metoda pro naËtenÌ sou¯adnic p¯ekladiöù ze souboru.
	 * @return - vraci pole typu Prekladiste
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
			System.err.println("I/O error in: " + SOURADNICE_PREKLADIST_SOUBOR + "\n" + e.getMessage());
			System.exit(1);
		}
		return prekladiste;
	}

	/**
	 * Metoda pro naËtenÌ cest pivovaru ze souboru.
	 * @return - vraci pole typu Cesta
	 */
	public static Cesta[] nactiCestyPivovaru() {
		String radka;
		Cesta[] cestyPivovaru = new Cesta[StaticData.POCET_CEST_PIVOVARU];

		try {
			BufferedReader bfr = new BufferedReader(new FileReader(CESTY_PIVOVARU_SOUBOR));

			for (int i = 0; i < StaticData.POCET_CEST_PIVOVARU; i++) {
				radka = bfr.readLine();
				cestyPivovaru[i] = vytvorCestu(radka);
			}

			bfr.close();
		} catch (IOException e) {
			System.err.println("I/O error in: " + CESTY_PIVOVARU_SOUBOR + "\n" + e.getMessage());
			System.exit(1);
		}

		return cestyPivovaru;
	}

	/**
	 * Metoda pro naËtenÌ cest p¯ekladiöù ze souboru.
	 * @return - vraci pole typu Cesta
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
			System.err.println("I/O error in: " + CESTY_PREKLADIST_SOUBOR + "\n" + e.getMessage());
			System.exit(1);
		}
		return cestyPrekladiste;
	}

	/**
	 * Metoda pro naËtenÌ cest hospod ze souboru.
	 * @return - vraci pole typu Cesta
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
			System.err.println("I/O error in: " + CESTY_HOSPOD_SOUBOR + "\n" + e.getMessage());
			System.exit(1);
		}
		return cestyHospod;
	}

	/**
	 * Metoda pro vytvo¯enÌ objektu sudove hospody. Postupne rozparsuje radek nacteny ze souboru a hodnoty z nej prectene pouzije jako vstupni parametry konstruktoru.
	 * @param cvsRadek - String s naËten˝mi hodotami.
	 */
	public static HospodaSudova vytvorSudovouHospodu(String cvsRadek) {
		String[] podRetezce = cvsRadek.split(" ");
		HospodaSudova hospoda = new HospodaSudova(Integer.parseInt(podRetezce[0]), new Pozice(Integer.parseInt(podRetezce[1]),
				Integer.parseInt(podRetezce[2])));
		return hospoda;
	}
	
	/**
	 * Metoda pro vytvo¯enÌ objektu tankove hospody. Postupne rozparsuje radek nacteny ze souboru a hodnoty z nej prectene pouzije jako vstupni parametry konstruktoru.
	 * @param cvsRadek - String s naËten˝mi hodotami.
	 */
	public static HospodaTankova vytvorTankovouHospodu(String cvsRadek) {
		String[] podRetezce = cvsRadek.split(" ");
		HospodaTankova hospoda = new HospodaTankova(Integer.parseInt(podRetezce[0]), new Pozice(Integer.parseInt(podRetezce[1]),
				Integer.parseInt(podRetezce[2])));
		return hospoda;
	}

	/**
	 * Metoda pro vytvo¯enÌ objektu p¯ekladiötÏ. Postupne rozparsuje radek nacteny ze souboru a hodnoty z nej prectene pouzije jako vstupni parametry konstruktoru.
	 * @param cvsRadek - String s naËten˝mi hodnotami.
	 */
	public static Prekladiste vytvorPrekladiste(String cvsRadek) {
		String[] podRetezce = cvsRadek.split(" ");
		Prekladiste prekladiste = new Prekladiste(Integer.parseInt(podRetezce[0]), new Pozice(
				Integer.parseInt(podRetezce[1]), Integer.parseInt(podRetezce[2])));
		return prekladiste;
	}

	/**
	 * Metoda pro vytvo¯enÌ objektu cesta. Postupne rozparsuje radek nacteny ze souboru a hodnoty z nej prectene pouzije jako vstupni parametry konstruktoru.
	 * @param cvsRadek - String s naËten˝mi hodnotami.
	 */
	public static Cesta vytvorCestu(String cvsRadek) {
		String[] podRetezce = cvsRadek.split(" ");
		Cesta cesta = new Cesta(Integer.parseInt(podRetezce[0]), Integer.parseInt(podRetezce[1]),
				Double.parseDouble(podRetezce[2]));
		return cesta;
	}
	
	/**
	 * Metoda pro zapis obsahu textArea pouzivane v GUI programu.
	 */
	public static void zapisVysledek() {
		
		try {
			BufferedWriter bwFile = new BufferedWriter(new FileWriter(LOG_SOUBOR));

			bwFile.write(Window.textArea.getText());

			bwFile.close();
		} catch (IOException e) {
			System.err.println("I/O error in: " + LOG_SOUBOR + "\n" + e.getMessage());
		}
	}
	
	/**
	 * Metoda pro zapsani jednoho radku do souboru se statistikou. Prijme vzdy retezec, ktery prida na konec daneho souboru.
	 * @param soubor - Cesta k souboru, do ktereho bude metoda pridavat novy obsah.
	 * @param retezec - Novy obsah, ktery bude pridan.
	 */
	public static void zapisStatistiku(String soubor, String retezec) {
		try {
			BufferedWriter bwFile = new BufferedWriter(new FileWriter(soubor, true));
			
			bwFile.append(retezec);
			
			bwFile.close();
		}
		catch (IOException e) {
			System.err.println("I/O error in: " + soubor + "\n" + e.getMessage());
		}
	}
}
