package generovani_dat;

import java.util.ArrayList;
import java.util.Collections;

import objekty_ostatni.Cesta;
import objekty_ostatni.Pozice;
import semestralka.StaticData;

/**
 * Trida obsahujici struktury pro ukladani generovanych dat o hospodach/prekladistich a cestach mezi nimi.
 * Generovana probihá v metodach objektu.
 * @author Vlada47 & Shag0n
 *
 */
public class Generator {

	public static Pozice[] souradnicePrekladist = new Pozice[StaticData.POCET_PREKLADIST];
	public static Pozice[] souradniceHospod = new Pozice[StaticData.POCET_HOSPOD];
	public static Cesta[] cestyPivovaru = new Cesta[StaticData.POCET_CEST_PIVOVARU];
	public static Cesta[][] cestyPrekladist = new Cesta[StaticData.POCET_PREKLADIST][StaticData.POCET_CEST_PREKLADISTE];
	public static Cesta[][] cestyHospod = new Cesta[StaticData.POCET_HOSPOD][StaticData.POCET_CEST_HOSPODY];
	
	/**
	 * Metoda postupne provadi generovaci metody pro vytvoreni souradnic a cest jednotlivych budov.
	 */
	public static void generujSouradniceACesty() {
		generujPozicePrekladist();
		generujPoziceHospod();
		generujCestyPivovaru();
		generujCestyPrekladist();
		generujCestyHospod();
		System.out.println("Vygenerovany souradnice a cesty...");
	}
	
	/**
	 * Metoda pro vygenerovani souradnic prekladist (reprezentovanych objektem Pozice). Prekladiste jsou rozmistena tak, aby se nachazela vzdy ve stredu jedne z osmi obdelnikovych oblasti.
	 * Objekty se souradnicemi jsou pak ulozeny do pole souradnicePrekladist.
	 */
	private static void generujPozicePrekladist() {

		int x = StaticData.ROZMEZI_PREKLADISTE_X;
		int y = StaticData.ROZMEZI_PREKLADISTE_Y;
		int pocet = 0;

		for (int j = 0; j < 4; j++) {
			for (int i = 0; i < 2; i++) {
				souradnicePrekladist[pocet++] = new Pozice(x, y);
				x = x + (StaticData.ROZMEZI_PREKLADISTE_X * 2);
			}
			y = y + (StaticData.ROZMEZI_PREKLADISTE_Y * 2);
			x = StaticData.ROZMEZI_PREKLADISTE_X;
		}
	}
	
	/**
	 * Metoda pro vygenerovani souradnic hospod (reprezentovanych objektem Pozice). Hospody jsou rovnomerne rozmisteny po cele plose do, vicemene, mrizkove struktury.
	 * Objekty se souradnicemi jsou pak ulozeny do pole souradniceHospod.
	 */
	private static void generujPoziceHospod() {

		int x = StaticData.ROZMEZI_HOSPODY_X;
		int y = StaticData.ROZMEZI_HOSPODY_Y;

		int pocet = 0;

		for (int j = 0; j < 50; j++) {
			for (int i = 0; i < 80; i++) {
				souradniceHospod[pocet++] = new Pozice(x, y);
				x = x + StaticData.ROZMEZI_HOSPODY_X;
			}
			y = y + (StaticData.ROZMEZI_HOSPODY_Y * 2);
			x = StaticData.ROZMEZI_HOSPODY_X;
		}
	}
	
	/**
	 * Metoda provygenerovani cest z pivovaru do nejblizsich hospod (reprezentovanych objektem Cesta).
	 * Metoda prohleda vsechny souradnice vsech hospod, vypocita vzdalenosti, ktere si ulozi do ArrayListu, ktery seradi a vybere z nej 50 nejkratsich cest.
	 */
	private static void generujCestyPivovaru() {
		ArrayList<Cesta> cesty = new ArrayList<Cesta>();
		for(int i = 0; i < souradniceHospod.length; i++) {
			cesty.add(new Cesta(0, i, Math.sqrt(Math.pow((double)(souradniceHospod[i].getX() - StaticData.SOURADNICE_PIVOVARU_X), 2.0) + Math.pow((double)(souradniceHospod[i].getY() - StaticData.SOURADNICE_PIVOVARU_Y), 2.0))));
		}
		Collections.sort(cesty);
		for(int i = 0; i < StaticData.POCET_CEST_PIVOVARU; i++) {
			cestyPivovaru[i] = cesty.get(i);
		}
		cesty.clear();
	}
	
	
	/**
	 * Metoda pro vygenerovani cest mezi prekladisti a nejblizsimi hospodami (reprezentovanych objektem Cesta).
	 * Metoda prohledava postupne pro vsechna prekladiste souradnice vsech hospod a vypocitava vzdalenosti, které ukládá do objektù Cesta.
	 * Tyto objekty jsou pak setøídìny od nejkratší cesty po nejdelší a je vybráno prvních 50 z nich, které se uloží do dvourozmìrného pole cestyPrekladist. 
	 */
	private static void generujCestyPrekladist() {
		ArrayList<Cesta> cesty = new ArrayList<Cesta>();
		for(int i = 0; i < souradnicePrekladist.length; i++) {
			for(int j = 0; j < souradniceHospod.length; j++) {
				cesty.add(new Cesta(i, j, Math.sqrt(Math.pow((double)(souradniceHospod[j].getX() - souradnicePrekladist[i].getX()), 2.0) + Math.pow((double)(souradniceHospod[j].getY() - souradnicePrekladist[i].getY()), 2.0))));
			}
			Collections.sort(cesty);
			for(int j = 0; j < StaticData.POCET_CEST_PREKLADISTE; j++) {
				cestyPrekladist[i][j] = cesty.get(j);
			}
			cesty.clear();
		}
	}
	
	/**
	 * Metoda pro vygenerování cest mezi hospodami (reprezentovaných objektem Cesta).
	 * Metoda prohledává postupnì pro všechny hospody souøadnice všech ostatních hospod a vypoèítává vzdálenosti, které ukládá do objektù Cesta.
	 * Tyto objekty jsou pak setøídìny od nejkratší cesty po nejdelší a je vybráno prvních 15 z nich, které se uloží do dvourozmìrného pole cestyHospod. 
	 */
	private static void generujCestyHospod() {
		ArrayList<Cesta> cesty = new ArrayList<Cesta>();
		for(int i = 0; i < souradniceHospod.length; i++) {
			for(int j = 0; j < souradniceHospod.length; j++) {
				if(i != j) {
					cesty.add(new Cesta(i, j, Math.sqrt(Math.pow((double)(souradniceHospod[j].getX() - souradniceHospod[i].getX()), 2.0) + Math.pow((double)(souradniceHospod[j].getY() - souradniceHospod[i].getY()), 2.0))));
				}
			}
			
			Collections.sort(cesty);
			for(int j = 0; j < StaticData.POCET_CEST_HOSPODY; j++) {
				cestyHospod[i][j] = cesty.get(j);
			}
			cesty.clear();
		}
	}	
}