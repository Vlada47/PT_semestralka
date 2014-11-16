package objekty_budovy;

import generovani_dat.Matice;

import java.util.ArrayList;

import objekty_ostatni.Objednavka;
import objekty_ostatni.Pozice;
import objekty_vozidla.Cisterna;
import objekty_vozidla.Kamion;
import objekty_vozidla.NakladniAuto;
import semestralka.StaticData;
import simulace.Simulace;

/**
 * 
 * @author Vlada47 & Shag0n
 *
 */
public class Prekladiste extends Budova{

	public static final int KAPACITA = 2000;

	private final int ID;
	private final Pozice pozice;
	private int pocetPlnychSudu;
	private int pocetPrazdnychSudu;
	public ArrayList<NakladniAuto> dostupnaAuta;
	public ArrayList<NakladniAuto> autaNaCeste;
	public ArrayList<Objednavka> objednavky;

	public Prekladiste(int ID, Pozice pozice) {
		this.ID = ID;
		this.pozice = pozice;
		this.pocetPlnychSudu = KAPACITA;
		this.pocetPrazdnychSudu = 0;
		
		this.dostupnaAuta = new ArrayList<NakladniAuto>();
		this.autaNaCeste = new ArrayList<NakladniAuto>();
		this.objednavky = new ArrayList<Objednavka>();
		
		for(int i = 0; i < StaticData.POCET_AUT; i++) {
			this.dostupnaAuta.add(new NakladniAuto(i));
		}
	}
	
	public int getID() {
		return this.ID;
	}

	public Pozice getPosition() {
		return pozice;
	}
	
	public int getPocetPlnychSudu() {
		return this.pocetPlnychSudu;
	}
	
	public int getPocetPrazdnychSudu() {
		return this.pocetPrazdnychSudu;
	}
	
	public void pridejObjednavku(Objednavka o) {
		this.objednavky.add(o);
	}
	
	/**
	 * Metoda postupne resici jednotlive objednavky sudovych hospod. U kazde objednavky se zkontroluje, zda jeji cas odpovida aktualnimu casu simulace.
	 * Pokud ano, probehne kontrola stavu volnych aut a mnozstvi plnych sudu v prekladisti. V pripade kladneho vysledku se objednavka dale zpracovava metodou pripravObjednavku.
	 * V opacnem pripade se objednavce nastavi novy cas - inkrementuje se o jednu hodinu... v pripade, ze prekroci maximalni povolenou mez (16 hodin),
	 * je jako hodina nastavena minimalni mez (8 hodin) a inkrementuje se den. Metoda pak pokracuje dalsi objednavkou.
	 */
	public void zpracujObjednavky() {
		for(Objednavka o : this.objednavky) {
			if((o.getCasObednani() == Simulace.hodina) && (o.getDenObednani() == Simulace.den)) {
				if(!(this.dostupnaAuta.isEmpty()) && (o.getMnozstvi() <= this.pocetPlnychSudu)) {
					pripravObjednavku(o);
				}
				else {
					int den = Simulace.den;
					int hodina = Simulace.hodina;
					
					if(Simulace.hodina >= StaticData.MAX_HODINA_OBJEDNANI) {
						den++;
						hodina = StaticData.MIN_HODINA_OBJEDNANI;
					}
					else {
						hodina++;
					}	
					zmenDobuObjednani(o, den, hodina);
				}
			}
		}
	}
	
	/**
	 * Metoda resici jednu konkretni objednavku "schvalenou" predchazejici metodou. Nejprve se urci, jak dlouho se objednavka poveze,
	 * a pote se rozhodne, zda je stihnutelna do maximalni casove meze (16 hodin). Pokud ne, odlozi se na dalsi den na minimalni cas (8 hodin).
	 * Pokud ano, odebere se pozadovany pocet plnych sudu z prekladiste a preda se volnemu autu, ktere se pak resi metodou vysliAutoDoHospody. Objednavka se zaroven odstrani z ArrayListu.
	 * @param o - objekt drzici onformace o objednavce
	 */
	private void pripravObjednavku(Objednavka o) {
		ArrayList<Integer> cesta = Matice.getNejkratsiCesta(StaticData.POCET_HOSPOD+this.ID, o.getIdObjednavajiciho());
		double vzdalenost = Matice.getDelkaNejkratsiCesty(cesta);
		int dobaCesty = (int) Math.round(vzdalenost / Cisterna.RYCHLOST);
		
		if(((Simulace.hodina + dobaCesty) > StaticData.MAX_HODINA_OBJEDNANI) && (dobaCesty <= (StaticData.MAX_HODINA_OBJEDNANI - StaticData.MIN_HODINA_OBJEDNANI))) {
			zmenDobuObjednani(o, Simulace.den+1, StaticData.MIN_HODINA_OBJEDNANI);
		}
		else {
			this.pocetPlnychSudu -= o.getMnozstvi();
			NakladniAuto a = this.dostupnaAuta.get(0);
			
			a.nalozPlneSudy(o.getMnozstvi());
			a.setCilovaHospoda(o.getIdObjednavajiciho());
			this.objednavky.remove(o);
			
			vysliAutoDoHospody(a, dobaCesty);
		}
	}
	
	/**
	 * Metoda resici nastaveni casu dojezdu auta do hospody a jeho navratu do prekladiste (obdoba metody pro kamion).
	 * Volne auto se pak vlozi do ArrayListu vyuzivanych aut a je odstraneno z ArrayListu volnych aut.
	 * @param a - auto, do ktere se casy ukladaji
	 * @param dobaCesty - doba cesty v hodinach
	 */
	private void vysliAutoDoHospody(NakladniAuto a, int dobaCesty) {
		
		int denDorazeniDoHospody = Simulace.den;
		int hodinaDorazeniDoHospody = Simulace.hodina + dobaCesty;
		
		int denNavratuDoPrekladiste = denDorazeniDoHospody;
		int hodinaNavratuDoPrekladiste = hodinaDorazeniDoHospody + dobaCesty;
		while(hodinaNavratuDoPrekladiste > 23) {
			hodinaNavratuDoPrekladiste -= 24;
			denNavratuDoPrekladiste++;
		}
		
		a.setDenDorazeniDoHospody(denDorazeniDoHospody);
		a.setHodinaDorazeniDoHospody(hodinaDorazeniDoHospody);
		a.setDenNavratuDoPrekladiste(denNavratuDoPrekladiste);
		a.setHodinaNavratuDoPrekladiste(hodinaNavratuDoPrekladiste);
		
		this.dostupnaAuta.remove(a);
		this.autaNaCeste.add(a);
	}
	
	
	/**
	 * Metoda pro zmenu hodiny a dne dane objednavky.
	 * @param o - objekt objednavky
	 * @param denObjednani - den objednani, ktery se ma k dane objednavce ulozit
	 * @param hodinaObjednani - hodina objednani, ktera se ma k dane objednave ulozit
	 */
	private void zmenDobuObjednani(Objednavka o, int denObjednani, int hodinaObjednani) {
		o.setDenObednani(denObjednani);
		o.setCasObednani(hodinaObjednani);
	}
	
	
	public void prijmiPlneSudy(int pocet) {
		this.pocetPlnychSudu += pocet;
		
		if((this.pocetPlnychSudu + this.pocetPrazdnychSudu) > KAPACITA) {
			System.err.println("Prekrocena kapacita prekladiste "+this.ID+"!");
		}
	}
	
	public void prijmiPrazdneSudy(int pocet) {
		this.pocetPrazdnychSudu += pocet;
		
		if((this.pocetPlnychSudu + this.pocetPrazdnychSudu) > KAPACITA) {
			System.err.println("Prekrocena kapacita prekladiste "+this.ID+"!");
		}
	}
	
	public int odevzdejPrazdneSudy() {
		if(this.pocetPrazdnychSudu >= Kamion.KAPACITA) {
			this.pocetPrazdnychSudu -= Kamion.KAPACITA;
			return Kamion.KAPACITA;
		}
		else {
			int pocet = this.pocetPrazdnychSudu;
			this.pocetPrazdnychSudu = 0;
			return pocet;
		}
	}
}
