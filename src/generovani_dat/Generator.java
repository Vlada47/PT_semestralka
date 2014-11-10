package generovani_dat;

import java.util.ArrayList;
import java.util.Collections;

import objekty_ostatni.Cesta;
import objekty_ostatni.Pozice;
import semestralka.StaticData;

/**
 * T��da objektu obsahuj�c� struktury pro ukl�d�n� generovan�ch dat o hospod�ch/p�ekladi�t�ch a cest�ch mezi nimi.
 * Generov�n� prob�h� v metod�ch objektu.
 * @author Vlada47 & Shag0n
 *
 */
public class Generator {

	public static Pozice[] souradnicePrekladist = new Pozice[StaticData.POCET_PREKLADIST];
	public static Pozice[] souradniceHospod = new Pozice[StaticData.POCET_HOSPOD];
	public static Cesta[] cestyPivovaru = new Cesta[StaticData.POCET_CEST_PIVOVARU];
	public static Cesta[][] cestyPrekladist = new Cesta[StaticData.POCET_PREKLADIST][StaticData.POCET_CEST_PREKLADISTE];
	public static Cesta[][] cestyHospod = new Cesta[StaticData.POCET_HOSPOD][StaticData.POCET_CEST_HOSPODY];
	
	public static void generujSouradniceACesty() {
		generujPozicePrekladist();
		generujPoziceHospod();
		generujCestyPivovaru();
		generujCestyPrekladist();
		generujCestyHospod();
		System.out.println("Vygenerovany souradnice a cesty...");
	}
	
	/**
	 * Metoda pro vygenerov�n� sou�adnic p�ekladi�� (reprezentovan�ch objektem Pozice). P�ekladi�t� jsou rozm�st�na tak, aby se nach�zela v�dy ve st�edu jedn� z osmi obd�ln�kov�ch oblast�.
	 * Objekty se sou�adnicemi jsou pak ulo�eny do pole souradnicePrekladist.
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
	 * Metoda pro vygenerov�n� sou�adnic hospod (reprezentovan�ch objektem Pozice). Hospody jsou rovnom�rn� rozm�st�ny po cel� plo�e do, v�cem�n�, m��kov� struktury.
	 * Objekty se sou�adnicemi jsou pak ulo�eny do pole souradniceHospod.
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
	 * Metoda pro vygenerov�n� cest mezi p�ekladi�ti a nejbli���mi hospodami (reprezentovan�ch objektem Cesta).
	 * Metoda prohled�v� postupn� pro v�echna p�ekladi�t� sou�adnice v�ech hospod a vypo��t�v� vzd�lenosti, kter� ukl�d� do objekt� Cesta.
	 * Tyto objekty jsou pak set��d�ny od nejkrat�� cesty po nejdel�� a je vybr�no prvn�ch 50 z nich, kter� se ulo�� do dvourozm�rn�ho pole cestyPrekladist. 
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
	 * Metoda pro vygenerov�n� cest mezi hospodami (reprezentovan�ch objektem Cesta).
	 * Metoda prohled�v� postupn� pro v�echny hospody sou�adnice v�ech ostatn�ch hospod a vypo��t�v� vzd�lenosti, kter� ukl�d� do objekt� Cesta.
	 * Tyto objekty jsou pak set��d�ny od nejkrat�� cesty po nejdel�� a je vybr�no prvn�ch 15 z nich, kter� se ulo�� do dvourozm�rn�ho pole cestyHospod. 
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