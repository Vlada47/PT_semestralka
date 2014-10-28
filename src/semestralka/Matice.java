package semestralka;

import java.util.ArrayList;
import java.util.Arrays;

public class Matice {
	
	public static Cesta[] cestyPivovaru;
	public static Cesta[][] cestyPrekladist;
	public static Cesta[][] cestyHospod;
	
	public static double[][] maticeSousednosti = new double[StaticData.POCET_HOSPOD+StaticData.POCET_PREKLADIST+1][StaticData.POCET_HOSPOD+StaticData.POCET_PREKLADIST+1];
	public static int[][] maticeNejkratsichCest = new int[StaticData.POCET_HOSPOD+StaticData.POCET_PREKLADIST+1][StaticData.POCET_HOSPOD+StaticData.POCET_PREKLADIST+1];
	
	public static void vygenerujMatice() {
		cestyPivovaru = InputOutput.nactiCestyPivovaru();
		cestyPrekladist = InputOutput.nactiCestyPrekladiste();
		cestyHospod = InputOutput.nactiCestyHospod();
		System.out.println("Nacteny cesty ze souboru...");
		
		vytvorMaticiSousednosti();
		System.out.println("Vytvorena matice sousednosti...");
	}
	
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
	
	public static void vytvorNejkratsiCesty() {
		int[][] maticePredchudcu = vytvorMaticiPredchudcu(maticeSousednosti);
		for (int k = 0; k < maticeSousednosti.length; k++) {
			//System.out.println("Matice nejkratsich cest: "+((double)k / maticeSousednosti.length)*100+"%");
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
	
	public static void nactiMaticiNejkratsichCestZeSouboru() {
		maticeNejkratsichCest = InputOutput.nactiMaticiNejkratsichCest();
		System.out.println("Matice nejkratsich cest nactena ze souboru...");	
	}
	
	public static ArrayList<Integer> getNejkratsiCesta(int odkud, int kam) {
		ArrayList<Integer> nejkratsiCesta = new ArrayList<Integer>();
		najdiNejkratsiCestu(maticeNejkratsichCest, odkud, kam, nejkratsiCesta);
		return nejkratsiCesta;
	}
	
	private static void najdiNejkratsiCestu(int[][] maticePredchudcu, int i, int j, ArrayList<Integer> a) {
		if (i == j) a.add(i);
		else if (maticePredchudcu[i][j] == -1) System.out.println("Cesta neexistuje");
		else {
			najdiNejkratsiCestu(maticePredchudcu, i, maticePredchudcu[i][j], a);
			a.add(j);
		}
	}
	
	public static double getDelkaNejkratsiCesty(ArrayList<Integer> nejkratsiCesta) {
		double delka = 0.0;
		
		for(int i = 0; i < nejkratsiCesta.size()-1; i++) {
			delka += maticeSousednosti[nejkratsiCesta.get(i)][nejkratsiCesta.get(i+1)];
		}
		return delka;
	}
}
