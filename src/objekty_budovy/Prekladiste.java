package objekty_budovy;

import generovani_dat.Matice;

import java.util.ArrayList;
import java.util.List;

import objekty_ostatni.Objednavka;
import objekty_ostatni.Pozice;
import objekty_vozidla.Cisterna;
import objekty_vozidla.Kamion;
import objekty_vozidla.NakladniAuto;
import semestralka.StaticData;
import simulace.Simulace;

/**
 * Trida uchovavajici atributy a konstruktor a metody pro praci s objektem Prekladiste.
 * @author Vlada47 & Shag0n
 *
 */
public class Prekladiste{
	
	/**
	 * Maximalni kapacita prekladiste pro obsah plnych + prazdnych sudu.
	 */
	public static final int KAPACITA = 2000;
	
	/**
	 * Identifikator prekladiste.
	 */
	private final int ID;
	
	/**
	 * Objekt typu Pozice uchovavajici souradnice prekladiste.
	 */
	private final Pozice pozice;
	
	/**
	 * Aktualni pocet plnych sudu v prekladisti.
	 */
	private int pocetPlnychSudu;
	
	/**
	 * Aktualni pocet prazdnych sudu v prekladisti.
	 */
	private int pocetPrazdnychSudu;
	
	/**
	 * ArrayList uchovavajici nakladni auta, ktera rozvazeji sudy do hospod.
	 */
	public List<NakladniAuto> nakladniAuta;
	
	/**
	 * ArrayList uchovavajici objednavky hospod, ktere prislusi danemu prekladisti.
	 */
	public List<Objednavka> objednavky;
	
	/**
	 * Promenna urcujici, kolik prekladiste odeslalo sudu hospodam a kolik bude pozadovat od pivovaru.
	 */
	private int pocetPozadovanychSudu;
	
	/**
	 * Pocet aut, co jsou aktualne na ceste.
	 */
	private int pocetAutNaCeste;
	
	/**
	 * Kontruktor objektu Prekladiste. Postupne zde dochazi k vytvareni jednotlivych ArrayListu pro prislusne objekty vozidel a objednavek.
	 * Pocet plnych sudu se nastavi na maximalni mez (k dovazeni z pivovaru dochazi zpravidla az nasledujici den) a pocet nakladnich aut na ceste se nastavi a pocet pozadovanych sudu na 0.
	 * @param id - identifikator prekladiste
	 * @param pozice - objekt obsahujici souradnice prekladiste na mape
	 */
	public Prekladiste(int id, Pozice pozice) {
		this.ID = id;
		this.pozice = pozice;
		this.pocetPlnychSudu = KAPACITA;
		this.pocetPrazdnychSudu = 0;
		
		this.nakladniAuta = new ArrayList<NakladniAuto>();
		this.objednavky = new ArrayList<Objednavka>();
		
		for(int i = 0; i < StaticData.POCET_AUT; i++) {
			this.nakladniAuta.add(new NakladniAuto(i));
		}
		
		this.setPocetPozadovanychSudu(0);
		this.setPocetAutNaCeste(0);
	}
	
	/**
	 * Metoda na ziskani identifikatoru prekladiste.
	 * @return - ID prekladiste
	 */
	public int getID() {
		return this.ID;
	}
	
	/**
	 * Metoda pro ziskani pozice prekladiste.
	 * @return - objekt se souradnicemi prekladiste
	 */
	public Pozice getPosition() {
		return pozice;
	}
	
	/**
	 * Metoda pro ziskani aktualniho poctu aut na ceste.
	 * @return - pocet aut, co jsou momentalne na ceste
	 */
	public int getPocetAutNaCeste() {
		return this.pocetAutNaCeste;
	}
	
	/**
	 * Metoda pro nastaveni aktualniho poctu aut na ceste.
	 * @param pocetAutNaCeste - pocet aut na ceste
	 */
	public void setPocetAutNaCeste(int pocetAutNaCeste) {
		this.pocetAutNaCeste = pocetAutNaCeste;
	}
	
	/**
	 * Metoda pro ziskani aktualniho poctu plnych sudu.
	 * @return - aktualni pocet plnych sudu
	 */
	public int getPocetPlnychSudu() {
		return this.pocetPlnychSudu;
	}
	
	/**
	 * Metoda pro ziskani aktualniho poctu prazdnych sudu.
	 * @return - aktualni pocet prazdnych sudu
	 */
	public int getPocetPrazdnychSudu() {
		return this.pocetPrazdnychSudu;
	}
	
	/**
	 * Metoda pro pridani objednavky do listu objednavek.
	 * @param o - objekt s informacemi o objednavce
	 */
	public void pridejObjednavku(Objednavka o) {
		this.objednavky.add(o);
	}
	
	/**
	 * Metoda pro ziskani aktualniho poctu pozadovanych sudu.
	 * @return - aktualni pocet pozadovanych sudu
	 */
	public int getPocetPozadovanychSudu() {
		return this.pocetPozadovanychSudu;
	}
	
	/**
	 * Metoda pro nastaveni aktualniho poctu pozadovanych sudu.
	 * @param pocetPozadovanychSudu - aktualni pocet pozadovanych sudu
	 */
	public void setPocetPozadovanychSudu(int pocetPozadovanychSudu) {
		this.pocetPozadovanychSudu = pocetPozadovanychSudu;
	}
	
	/**
	 * Metoda postupne resici jednotlive objednavky sudovych hospod. U kazde objednavky se zkontroluje, zda jeji cas odpovida aktualnimu casu simulace.
	 * Pokud ano, probehne kontrola stavu volnych aut a mnozstvi plnych sudu v prekladisti. V pripade kladneho vysledku se objednavka dale zpracovava metodou pripravObjednavku.
	 * V opacnem pripade se objednavce nastavi novy cas - inkrementuje se o jednu hodinu... v pripade, ze prekroci maximalni povolenou mez (16 hodin),
	 * je jako hodina nastavena minimalni mez (8 hodin) a inkrementuje se den. Metoda pak pokracuje dalsi objednavkou.
	 */
	public void zpracujObjednavky() {
		for(Objednavka o : this.objednavky) {
			if((o.getCasObednani() == Simulace.hodina) && (o.getDenObednani() == Simulace.den) && !(o.isVyrizena())) {
				if(((this.nakladniAuta.size()-this.pocetAutNaCeste) > 0) && (o.getMnozstvi() <= this.pocetPlnychSudu)) {
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
		List<Integer> cesta = Matice.getNejkratsiCesta(StaticData.POCET_HOSPOD+this.ID, o.getIdObjednavajiciho());
		double vzdalenost = Matice.getDelkaNejkratsiCesty(cesta);
		double dobaCesty = vzdalenost / Cisterna.RYCHLOST;
		
		if(((Simulace.hodina + dobaCesty) > StaticData.MAX_HODINA_OBJEDNANI) && (dobaCesty <= (StaticData.MAX_HODINA_OBJEDNANI - StaticData.MIN_HODINA_OBJEDNANI))) {
			zmenDobuObjednani(o, Simulace.den+1, StaticData.MIN_HODINA_OBJEDNANI);
		}
		else {
			this.pocetPlnychSudu -= o.getMnozstvi();
			this.pocetPozadovanychSudu += o.getMnozstvi();
			NakladniAuto a = this.nakladniAuta.get(0);
			for(int i = 0; i < nakladniAuta.size(); i++) {
				a = this.nakladniAuta.get(i);
				if(!(a.isNaCeste())) {
					break;
				}
			}
			a.nalozPlneSudy(o.getMnozstvi());
			a.setStartovniPrekladiste(this.ID);
			a.setCilovaHospoda(o.getIdObjednavajiciho());
			o.setVyrizena(true);
			
			vysliAutoDoHospody(a, dobaCesty);
		}
	}
	
	/**
	 * Metoda resici nastaveni casu dojezdu auta do hospody a jeho navratu do prekladiste (obdoba metody pro kamion).
	 * Volne auto se pak vlozi do ArrayListu vyuzivanych aut a je odstraneno z ArrayListu volnych aut.
	 * @param a - auto, do ktere se casy ukladaji
	 * @param dobaCesty - doba cesty v hodinach
	 */
	private void vysliAutoDoHospody(NakladniAuto a, double dobaCesty) {
		
		int denDorazeniDoHospody = Simulace.den;
		double hodinaDorazeniDoHospodyD = Simulace.hodina + dobaCesty;
		int hodinaDorazeniDoHospody = (int)Math.round(hodinaDorazeniDoHospodyD);
		
		int denPrelozeniSudu = denDorazeniDoHospody;
		double hodinaPrelozeniSuduD = hodinaDorazeniDoHospodyD + (a.getPocetPlnychSudu()*StaticData.SUD_CAS);
		int hodinaPrelozeniSudu = (int)Math.round(hodinaPrelozeniSuduD);
		
		
		int denNavratuDoPrekladiste = denDorazeniDoHospody;
		double hodinaNavratuDoPrekladisteD = hodinaPrelozeniSuduD + dobaCesty;
		
		while(hodinaNavratuDoPrekladisteD > 23) {
			hodinaNavratuDoPrekladisteD -= 24;
			denNavratuDoPrekladiste++;
		}
		int hodinaNavratuDoPrekladiste = (int)Math.round(hodinaNavratuDoPrekladisteD);
		
		a.setDenDorazeniDoHospody(denDorazeniDoHospody);
		a.setHodinaDorazeniDoHospody(hodinaDorazeniDoHospody);
		a.setDenPrelozeniSudu(denPrelozeniSudu);
		a.setHodinaPrelozeniSudu(hodinaPrelozeniSudu);
		a.setDenNavratuDoPrekladiste(denNavratuDoPrekladiste);
		a.setHodinaNavratuDoPrekladiste(hodinaNavratuDoPrekladiste);
		
		a.setNaCeste(true);
		this.pocetAutNaCeste++;
		System.out.println("Nakladni auto "+a.getID()+" vyrazilo z prekladiste "+a.getStartovniPrekladiste()+" do hospody "+a.getCilovaHospoda()+".");
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
	
	/**
	 * Metoda pro prijeti plnych sudu od kamionu.
	 * @param pocet - naklad, ktery kamion preda prekladisti
	 */
	public void prijmiPlneSudy(int pocet) {
		this.pocetPlnychSudu += pocet;
	}
	
	/**
	 * Metoda pro prijeti prazdnych sudu od nakladniho auta.
	 * @param pocet - prazdne sudy nakladniho auta
	 */
	public void prijmiPrazdneSudy(int pocet) {
		this.pocetPrazdnychSudu += pocet;
	}
	
	/**
	 * Metoda k odevzdani prazdnych sudu obsluhujicimu kamionu. Dochazi zde ke kontrole, jestli prekladiste ma alespon tolik sudu, kolik vyplni jeden kamion.
	 * Pokud ano, odevzda presne pocet sudu odpovidajici kapacite kamionu, pokud ne, odevzda, co mu zbyva.
	 * @return - pocet odevzdanych prazdnych sudu
	 */
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
	
	/**
	 * Metoda pro ziskani retezce s informacemi o stavu prekladiste (pocet plnych a prazdnych sudu).
	 * @return - retezec s informacemi
	 */
	public String getVypis() {
		return "Prekladiste "+this.ID+" ma "+this.pocetPlnychSudu+"plnych a "+this.pocetPrazdnychSudu+" prazdnych sudu.";
	}
}
