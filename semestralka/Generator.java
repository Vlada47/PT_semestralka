package semestralka;

import java.util.ArrayList;
import java.util.Collections;

/**
 * T��da objektu obsahuj�c� struktury pro ukl�d�n� generovan�ch dat o hospod�ch/p�ekladi�t�ch a cest�ch mezi nimi.
 * Generov�n� prob�h� v metod�ch objektu.
 * @author Vlada47 & Shag0n
 *
 */
public class Generator {

	static Pozice[] souradnicePrekladist = new Pozice[StaticData.POCET_PREKLADIST];
	static Pozice[] souradniceHospod = new Pozice[StaticData.POCET_HOSPOD];
	static Cesta[][] cestyPrekladist = new Cesta[StaticData.POCET_PREKLADIST][StaticData.POCET_CEST_PREKLADISTE];
	static Cesta[][] cestyHospod = new Cesta[StaticData.POCET_HOSPOD][StaticData.POCET_CEST_HOSPODY];
	
	/**
	 * Metoda pro vygenerov�n� sou�adnic p�ekladi�� (reprezentovan�ch objektem Pozice). P�ekladi�t� jsou rozm�st�na tak, aby se nach�zela v�dy ve st�edu jedn� z osmi obd�ln�kov�ch oblast�.
	 * Objekty se sou�adnicemi jsou pak ulo�eny do pole souradnicePrekladist.
	 */
	public static void generujPozicePrekladist() {

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
	public static void generujPoziceHospod() {

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
	 * Metoda pro vygenerov�n� cest mezi p�ekladi�ti a nejbli���mi hospodami (reprezentovan�ch objektem Cesta).
	 * Metoda prohled�v� postupn� pro v�echna p�ekladi�t� sou�adnice v�ech hospod a vypo��t�v� vzd�lenosti, kter� ukl�d� do objekt� Cesta.
	 * Tyto objekty jsou pak set��d�ny od nejkrat�� cesty po nejdel�� a je vybr�no prvn�ch 50 z nich, kter� se ulo�� do dvourozm�rn�ho pole cestyPrekladist. 
	 */
	public static void generujCestyPrekladist() {
		ArrayList<Cesta> cesty = new ArrayList<Cesta>();
		for(int i = 0; i < souradnicePrekladist.length; i++) {
			//System.out.println("Prekladiste"+i);
			for(int j = 0; j < souradniceHospod.length; j++) {
				if(i != j) {
					cesty.add(new Cesta(i, j, Math.sqrt(Math.pow((double)(souradniceHospod[j].getX() - souradnicePrekladist[i].getX()), 2.0) + Math.pow((double)(souradniceHospod[j].getY() - souradnicePrekladist[i].getY()), 2.0))));
				}
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
	public static void generujCestyHospod() {
		ArrayList<Cesta> cesty = new ArrayList<Cesta>();
		for(int i = 0; i < souradniceHospod.length; i++) {
			//System.out.println("Hospoda"+i);
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