package generovani_dat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import objekty_budovy.HospodaSudova;
import objekty_budovy.HospodaTankova;
import objekty_ostatni.Cesta;
import objekty_ostatni.Objednavka;
import objekty_ostatni.Pozice;
import semestralka.StaticData;
import simulace.Simulace;

/**
 * Trida obsahujici struktury pro ukladani generovanych dat o hospodach/prekladistich a cestach mezi nimi.
 * Generovana probihá v metodach objektu.
 * @author Vlada47 & Shag0n
 *
 */
public class Generator {
	
	/**
	 * Pole typu Pozice uchovavajici souradnice prekladist pro dalsi pouziti.
	 */
	public static Pozice[] souradnicePrekladist = new Pozice[StaticData.POCET_PREKLADIST];
	
	/**
	 * Pole typu Pozice uchovavajici souradnice hospod pro dalsi pouziti.
	 */
	public static Pozice[] souradniceHospod = new Pozice[StaticData.POCET_HOSPOD];
	
	/**
	 * Pole typu Cesta uchovavajici cesty vedouci z pivovaru pro dalsi pouziti.
	 */
	public static Cesta[] cestyPivovaru = new Cesta[StaticData.POCET_CEST_PIVOVARU];
	
	/**
	 * Pole typu Cesta uchovavajici cesty vedouci z jednotlivych prekladist pro dalsi pouziti.
	 */
	public static Cesta[][] cestyPrekladist = new Cesta[StaticData.POCET_PREKLADIST][StaticData.POCET_CEST_PREKLADISTE];
	
	/**
	 * Pole typu Cesta uchovavajici cesty vedouci z jednotlivych hospod pro dalsi pouziti.
	 */
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
	
	/**
	 * Metoda, ktera postupne generuje objednavky jednotlivych hospod pro celou dobu simulace a pripradi je pivovaru nebo prislusnemu prekladisti
	 * Mnozstvi, ktere ma hospoda objednat se vypocte pomoci metody vyberMnozstvi. Tato hodnota se rovnez pouzije pro urceni denni spotreby hospody.
	 * Cas, kdy dojde k objednani je zase vypocitan metodou vyberCas.
	 */
	public static void generujObjednavky() {
		for(HospodaTankova h : Simulace.tankoveHospody) {
			for(int i = 1; i <= StaticData.POCET_DNU; i++) {
				int mnozstvi = vyberMnozstvi();
				h.addDenniSpotreba(mnozstvi);
				Objednavka o = new Objednavka(i, vyberCas(), h.getID(), 8, mnozstvi);
				Simulace.pivovar.pridejObjednavku(o);
			}
		}
		
		for(HospodaSudova h : Simulace.sudoveHospody) {
			for(int i = 1; i <= StaticData.POCET_DNU; i++) {
				int mnozstvi = vyberMnozstvi();
				h.addDenniSpotreba(mnozstvi);
				Objednavka o = new Objednavka(i, vyberCas(), h.getID(), h.getIdPrekladiste(), mnozstvi);
				Simulace.prekladiste[h.getIdPrekladiste()].pridejObjednavku(o);
			}
		}
	}
	
	/**
	 * Metoda na vybrani mnozstvi, ktere si dana hospoda objedna v rozsahu 1 az 6 sudu / hl.
	 * Nejprve je pomoci tridy Random vybrano pseudonahodne cislo a podle nej rozrazeno do jednotlivych kategorii (podle pravdepodobnosti uvedene v zadani). 
	 * @return vraci mnostvi, ktere si ma hospoda objednat
	 */
	private static int vyberMnozstvi() {
		Random r = new Random();
		int generaceMnozstvi = r.nextInt(101);
		int mnozstvi;
		
		if (generaceMnozstvi < 25) {
			mnozstvi = 1;
		}	
		else if (generaceMnozstvi < 50) {
			mnozstvi = 2;
		}
		else if (generaceMnozstvi < 71) {
			mnozstvi = 3;
		}
		else if (generaceMnozstvi < 86) {
			mnozstvi = 4;
		}
		else if (generaceMnozstvi < 96) {
			mnozstvi = 5;
		}
		else {
			mnozstvi = 6;
		}
			
		
		return mnozstvi;
	}
	
	/**
	 * Metoda na urceni casu, ve kterem dojde k objednavce. Pomoci tridy Random a jeji metody nextGaussian je vybrana hodnota casu dle Gaussovy rozdeleni pravdepodobnosti.
	 * Vzhledem k vlastnostem Gaussovy krivky je vysledek, pokud presahuje stanovene meze, na tyto meze snizen / zvysen.
	 * Vrchol krivky odpovida hodine, kdy ma chodit nejvice objednavek. 
	 * @return vraci cas, ve kterem dojde k objednavce
	 */
	private static int vyberCas() {
		Random r = new Random();
		double cas = r.nextGaussian()*StaticData.GAUSS_ROZSAH + StaticData.HODINA_OBJEDNAVEK;
		if(cas < StaticData.MIN_HODINA_OBJEDNANI) {
			cas = StaticData.MIN_HODINA_OBJEDNANI;
		}
		if(cas > StaticData.MAX_HODINA_OBJEDNANI) {
			cas = StaticData.MAX_HODINA_OBJEDNANI;
		}
		
		return (int) Math.round(cas);
	}
}