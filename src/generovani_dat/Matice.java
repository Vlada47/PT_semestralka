package generovani_dat;

import io.InputOutput;

import java.util.ArrayList;
import java.util.Arrays;

import objekty_ostatni.Cesta;
import semestralka.StaticData;

/**
 * Trida obsahujici matice uchovavajici informace o cestach mezi jednotlivymi uzly (hospody, prekladiste a pivovar) na mape.
 * Zaroven obsahuje metody, ktere maji za ukol tyto matice vytvorit.
 * @author Vladimír & Shag0n
 *
 */
public class Matice {
	
	/**
	 * Pole typu Cesta pro ulozeni cest pivovaru ze souboru a nasledne pouziti pro vytvoreni matice sousednosti.
	 */
	public static Cesta[] cestyPivovaru;
	
	/**
	 * Pole typu Cesta pro ulozeni cest prekladist ze souboru a nasledne pouziti pro vytvoreni matice sousednosti.
	 */
	public static Cesta[][] cestyPrekladist;
	
	/**
	 * Pole typu Cesta pro ulozeni cest hospod ze souboru a nasledne pouziti pro vytvoreni matice sousednosti.
	 */
	public static Cesta[][] cestyHospod;
	
	/**
	 * Pole pro ulozeni matice sousednosti se vsemi uzly, ktere se v simulaci budou vyskytovat.
	 */
	public static double[][] maticeSousednosti = new double[StaticData.POCET_HOSPOD+StaticData.POCET_PREKLADIST+1][StaticData.POCET_HOSPOD+StaticData.POCET_PREKLADIST+1];
	
	/**
	 * Pole pro ulozeni matice, kterou vytvari Floyd-Warshalùv algoritmus.
	 */
	public static int[][] maticeNejkratsichCest = new int[StaticData.POCET_HOSPOD+StaticData.POCET_PREKLADIST+1][StaticData.POCET_HOSPOD+StaticData.POCET_PREKLADIST+1];
	
	/**
	 * Metoda, ktera spousti postupne ostatni metody tridy. Nejprve nacte ze souboru cesty mezi jednotlivymi uzly a pak z nich vypracuje matici sousednosti.
	 * Pozn.: Metoda nespousti metody pro urceni finalni matice cest, ktera je pouzivana zvlastv situaci, kdy je to zapotrebi. Jeji vypocet je casove narocny, z toho duvodu se nepouziva vzdy (tato matice je po vytvoreni ukladana do souboru pro pozdejsi pouziti). 
	 */
	public static void vygenerujMatice() {
		cestyPivovaru = InputOutput.nactiCestyPivovaru();
		cestyPrekladist = InputOutput.nactiCestyPrekladiste();
		cestyHospod = InputOutput.nactiCestyHospod();
		System.out.println("Nacteny cesty ze souboru...");
		
		vytvorMaticiSousednosti();
		System.out.println("Vytvorena matice sousednosti...");
	}
	
	/**
	 * Metoda pro vytvoreni matice sousednosti mezi vsemi uzly na mape.
	 * Nejprve vyplni matici maximalni hodnotou typu Double (reprezentuje nekonecno) a na diagonalu umisti 0.
	 * Pote uklada jednotlive vzdalenost mezi uzly z poli, kde jsou ulozeny. Indexace matice - prvni indexy patri hospodam, po nich nasleduji prekladiste a posledni index matice odpovida pivovaru.
	 */
	private static void vytvorMaticiSousednosti() {	
		for(int i = 0; i < maticeSousednosti.length; i++) {
			Arrays.fill(maticeSousednosti[i], Double.MAX_VALUE);
			maticeSousednosti[i][i] = 0.0;
		}
		
		for(int i = 0; i < cestyHospod.length; i++) {
			for(int j = 0; j < cestyHospod[i].length; j++) {
				int indexFrom = cestyHospod[i][j].getIdFrom();
				int indexTo = cestyHospod[i][j].getIdTo();
				double vzdalenost = cestyHospod[i][j].getVzdalenost();
				
				maticeSousednosti[indexFrom][indexTo] = vzdalenost;
				maticeSousednosti[indexTo][indexFrom] = vzdalenost;
			}
		}
		
		for(int i = 0; i < cestyPrekladist.length; i++) {
			for(int j = 0; j < cestyPrekladist[i].length; j++) {
				int indexFrom = cestyPrekladist[i][j].getIdFrom() + StaticData.POCET_HOSPOD;
				int indexTo = cestyPrekladist[i][j].getIdTo();
				double vzdalenost = cestyPrekladist[i][j].getVzdalenost();
				
				maticeSousednosti[indexFrom][indexTo] = vzdalenost;
				maticeSousednosti[indexTo][indexFrom] = vzdalenost;
			}
		}
		
		for(int i = 0; i < cestyPivovaru.length; i++) {
			int indexFrom = cestyPivovaru[i].getIdFrom() + StaticData.POCET_HOSPOD + StaticData.POCET_PREKLADIST;
			int indexTo = cestyPivovaru[i].getIdTo();
			double vzdalenost = cestyPivovaru[i].getVzdalenost();
			
			maticeSousednosti[indexFrom][indexTo] = vzdalenost;
			maticeSousednosti[indexTo][indexFrom] = vzdalenost;
		}
	}
	
	/**
	 * Metoda k vytvoreni matice nejkratsich cest. Vyuziva Floyd-Warshalova algoritmu k nalezeni nejkratsich cesty mezi vsemi uzly na mape.
	 */
	public static void vytvorNejkratsiCesty() {
		int[][] maticePredchudcu = vytvorMaticiPredchudcu(maticeSousednosti);
		for (int k = 0; k < maticeSousednosti.length; k++) {
	        for (int i = 0; i < maticeSousednosti.length; i++) {
	            for (int j = 0; j < maticeSousednosti.length; j++) {
	                if (maticeSousednosti[i][k] == Double.MAX_VALUE || maticeSousednosti[k][j] == Double.MAX_VALUE) {
	                    continue;                  
	                }
	                
	                if (maticeSousednosti[i][j] > maticeSousednosti[i][k] + maticeSousednosti[k][j]) {
	                	maticeSousednosti[i][j] = maticeSousednosti[i][k] + maticeSousednosti[k][j];
	                    maticePredchudcu[i][j] = maticePredchudcu[k][j];
	                }
	            }
	        }
	    }
	    maticeNejkratsichCest = maticePredchudcu;
	}
	
	/**
	 * Pomocna metoda pro Floyd-Warshaluv algoritmus pro vytvoreni matice predchudcu a jejimu vraceni vyssi metode.
	 * @param maticeSousednosti
	 * @return matice predchudcu 
	 */
	private static int[][] vytvorMaticiPredchudcu(double[][] maticeSousednosti) {
	    int[][] m = new int[maticeSousednosti.length][maticeSousednosti.length];
	    for (int i = 0; i < maticeSousednosti.length; i++) {
	        for (int j = 0; j < maticeSousednosti.length; j++) {
	            if (maticeSousednosti[i][j] != 0.0 && maticeSousednosti[i][j] != Double.MAX_VALUE) {
	                m[i][j] = i;
	            } else {
	                m[i][j] = -1;
	            }
	        }
	    }
	    return m;
	}
	
	/**
	 * Metoda pro nacteni matice nejkratsich cest ze souboru pouzivana v pripade, ze je nejaka ulozena. Vyuziva metody tridy InputOutput.
	 */
	public static void nactiMaticiNejkratsichCestZeSouboru() {
		maticeNejkratsichCest = InputOutput.nactiMaticiNejkratsichCest();
		System.out.println("Matice nejkratsich cest nactena ze souboru...");	
	}
	
	/**
	 * Metoda pro nalezeni nejkratsi cesty mezi dvema libovolnymi uzly na mape z matice nejkratsich cest.
	 * Vyuziva rekurzivni metodu pro prohledavani teto matice a cestu praci jako ArrayList jednotlivych indexu uzlu.
	 * @param odkud - index uzlu, ze ktereho cesta vede
	 * @param kam - index uzlu, do kterehose snazime dostat
	 * @return ArrayList s posloupnosti indexu uzlu, pres ktere cesta vede. 
	 */
	public static ArrayList<Integer> getNejkratsiCesta(int odkud, int kam) {
		ArrayList<Integer> nejkratsiCesta = new ArrayList<Integer>();
		najdiNejkratsiCestu(maticeNejkratsichCest, odkud, kam, nejkratsiCesta);
		return nejkratsiCesta;
	}
	
	/**
	 * Rekurzivni metoda pro vyhledani konkretni cesty v matici nejkratsich cest.
	 * @param maticePredchudcu - metice, ve ktere se cesta hleda
	 * @param i - index pocatecniho uzlu
	 * @param j - index koncoveho uzlu
	 * @param a - ArrayList pro ukladani cesty
	 */
	private static void najdiNejkratsiCestu(int[][] maticePredchudcu, int i, int j, ArrayList<Integer> a) {
		if (i == j) {
			a.add(i);
		}
		else if (maticePredchudcu[i][j] == -1) {
			System.out.println("Cesta z "+i+" do "+j+" neexistuje!");
		}
		else {
			najdiNejkratsiCestu(maticePredchudcu, i, maticePredchudcu[i][j], a);
			a.add(j);
		}
	}
	
	/**
	 * Metoda urcujici pomoci matice sousednosti delku vlozene cesty (nemusi byt specificky nejkratsi, ale v programu se pro to vyuziva).
	 * @param nejkratsiCesta - ArrayList s posloupnosti indexu uzlu dane cesty
	 * @return delka zadane cesty
	 */
	public static double getDelkaNejkratsiCesty(ArrayList<Integer> nejkratsiCesta) {
		double delka = 0.0;
		
		for(int i = 0; i < nejkratsiCesta.size()-1; i++) {
			delka += maticeSousednosti[nejkratsiCesta.get(i)][nejkratsiCesta.get(i+1)];
		}
		return delka;
	}
}
