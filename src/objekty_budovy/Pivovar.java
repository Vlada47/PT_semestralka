package objekty_budovy;

import java.util.ArrayList;

import generovani_dat.Matice;
import objekty_ostatni.Objednavka;
import objekty_vozidla.Cisterna;
import objekty_vozidla.Kamion;
import semestralka.StaticData;
import simulace.Simulace;

/**
 * Trida uchovavajici kontruktor a metody pro praci s objektem Pivovar.
 * @author Vlada47 & Shag0n
 *
 */
public class Pivovar extends Budova {
	
	public static final int DENNI_PRODUKCE = 7000;

	private int stavPiva;
	public ArrayList<Kamion> kamiony;
	public ArrayList<Cisterna> cisterny;
	public ArrayList<Objednavka> objednavkyHospod;
	private int pocetKamionuNaCeste;
	private int pocetCisterenNaCeste;

	private static Pivovar instance;
	
	/**
	 * Kontruktor objektu Pivovar. Postupne zde dochazi k vytvareni jednotlivych ArrayListu pro prislusne objekty vozidel a objednavek.
	 */
	private Pivovar() {
		this.stavPiva = 0;
		this.kamiony = new ArrayList<Kamion>();
		this.cisterny = new ArrayList<Cisterna>();
		this.objednavkyHospod = new ArrayList<Objednavka>();
		
		for(int i = 0; i < StaticData.POCET_KAMIONU; i++) {
			this.kamiony.add(new Kamion(i));
		}
		
		for(int i = 0; i < StaticData.POCET_CISTEREN; i++) {
			this.cisterny.add(new Cisterna(i));
		}
		
		this.setPocetKamionuNaCeste(0);
		this.setPocetCisterenNaCeste(0);
	}
	
	/**
	 * Metoda pro vytvoreni instance tridy Pivovar. Objekt by mel byt jedinackem.
	 * @return - instance tridy Pivovar
	 */
	public static Pivovar getInstance() {
		if (instance == null) {
			instance = new Pivovar();
		}
		return instance;
	}
	
	/**
	 * Metoda pro zjisteni mnozstvi piva v pivovaru.
	 * @return - aktualni stav piva
	 */
	public int getStavPiva() {
		return this.stavPiva;
	}
	
	public int getPocetKamionuNaCeste() {
		return this.pocetKamionuNaCeste;
	}

	public void setPocetKamionuNaCeste(int pocetKamionuNaCeste) {
		this.pocetKamionuNaCeste = pocetKamionuNaCeste;
	}

	public int getPocetCisterenNaCeste() {
		return this.pocetCisterenNaCeste;
	}

	public void setPocetCisterenNaCeste(int pocetCisterenNaCeste) {
		this.pocetCisterenNaCeste = pocetCisterenNaCeste;
	}
	
	/**
	 * Metoda, ktera provede denni produkci piva v pivovaru (spousti se jednou za 24 hodin).
	 */
	public void vyprodukujPivo() {
		this.stavPiva += DENNI_PRODUKCE;
	}
	
	/**
	 * Metoda, ktera prida do ArrayListu s objednavkami dalsi objednavku pro zpracovani.
	 * @param o - objekt drzici si informace o objednavce
	 */
	public void pridejObjednavku(Objednavka o) {
		this.objednavkyHospod.add(o);
	}
	
	/**
	 * Metoda inicializujici rozvoz plnych sudu do prekladist. Resi postupne vsechna prekladiste obsazena v poli nalezici aktualni simulaci
	 * volanim metody odvozDoPrekladiste.
	 */
	public void rozvozDoPrekladist() {
		for(Prekladiste p : Simulace.prekladiste) {
			odvozDoPrekladiste(p);
		}
	}
	
	/**
	 * Metoda resici odvoz sudu do jednoho konkretniho prekladiste. Odesle staticky nastaveny pocet kamionu z ArrayListu dostupneKamiony,
	 * kterym pak nastavi naklad na jejich maximalni kapacitu (a odebere prislusne mnozstvi piva z pivovaru) a zavola metodu odesliKamionDoPrekladiste.
	 * Po jejim vykonani odstrani prislusny kamion z listu dostupneKamiony a priradi jej do listu kamionyNaCeste.
	 * @param p - objekt prekladiste, do ktereho se kamiony maji poslat
	 */
	private void odvozDoPrekladiste(Prekladiste p) {
		
		int pocetKamionuKVyslani = p.getPocetPozadovanychSudu() / Kamion.KAPACITA;
		p.setPocetPozadovanychSudu(p.getPocetPozadovanychSudu() - (pocetKamionuKVyslani * Kamion.KAPACITA));

		for (int i = 0; i < pocetKamionuKVyslani; i++) {
			if(((this.kamiony.size()-this.pocetKamionuNaCeste) > 0) && ((Kamion.KAPACITA/2) <= (this.stavPiva))) {
				this.stavPiva -= Kamion.KAPACITA/2;
				Kamion k = this.kamiony.get(0);
				for(int j = 0; j < this.kamiony.size(); j++) {
					k = this.kamiony.get(j);
					if(!(k.isNaCeste())) break;
				}
				k.nalozPlneSudy(Kamion.KAPACITA);
				
				odesliKamionDoPrekladiste(k, p);
			}
			else {
				System.err.println("Nelze odeslat kamion se sudy do prekladiste "+p.getID()+" (nedostatek piva nebo dostupnych kamionu)!");
				System.exit(1);
			}
		}
		
		if(p.getPocetPozadovanychSudu() > 0) {
			if(((this.kamiony.size()-this.pocetKamionuNaCeste) > 0) && ((Kamion.KAPACITA/2) <= (this.stavPiva))) {
				this.stavPiva -= Kamion.KAPACITA/2;
				Kamion k = this.kamiony.get(0);
				for(int j = 0; j < this.kamiony.size(); j++) {
					k = this.kamiony.get(j);
					if(!(k.isNaCeste())) break;
				}
				k.nalozPlneSudy(p.getPocetPozadovanychSudu());
				p.setPocetPozadovanychSudu(0);
				
				odesliKamionDoPrekladiste(k, p);
			}
			else {
				System.err.println("Nelze odeslat kamion se sudy do prekladiste "+p.getID()+" (nedostatek piva nebo dostupnych kamionu)!");
				System.exit(1);
			}
		}	
	}
	
	/**
	 * Metoda, ktera pro konkretni kamion zjisti nejvyhodnejsi cestu, vypocita jeji vzdalenost a na zaklade prumerne rychlosti kamionu mu urci, kdy dorazi do cile.
	 * Cil (prekladiste) se rovnez uklada k prislusnemu kamionu.
	 * @param k - objekt kamionu, kteremu se urcuje cesta a jeji cil
	 * @param p - objekt prekladiste, ktere predstavuje cil pro kamion
	 */
	private void odesliKamionDoPrekladiste(Kamion k, Prekladiste p) {
		ArrayList<Integer> cesta = Matice.getNejkratsiCesta(StaticData.POCET_HOSPOD+StaticData.POCET_PREKLADIST, StaticData.POCET_HOSPOD+p.getID());
		double vzdalenost = Matice.getDelkaNejkratsiCesty(cesta);
		double dobaCesty = vzdalenost / Kamion.RYCHLOST;
		
		int denDorazeniDoPrekladiste = Simulace.den;
		double hodinaDorazeniDoPrekladisteD = Simulace.hodina + dobaCesty;
		while(hodinaDorazeniDoPrekladisteD > 23) {
			hodinaDorazeniDoPrekladisteD -= 24;
			denDorazeniDoPrekladiste++;
		}
		int hodinaDorazeniDoPrekladiste = (int)Math.round(hodinaDorazeniDoPrekladisteD);
		
		int denPrelozeniSudu = denDorazeniDoPrekladiste;
		double hodinaPrelozeniSuduD = hodinaDorazeniDoPrekladisteD + (k.getPocetPlnychSudu()*StaticData.SUD_CAS);
		while(hodinaPrelozeniSuduD > 23) {
			hodinaPrelozeniSuduD -= 24;
			denPrelozeniSudu++;
		}
		int hodinaPrelozeniSudu = (int)Math.round(hodinaPrelozeniSuduD);
		
		int denNavratuDoPivovaru = denPrelozeniSudu;
		double hodinaNavratuDoPivovaruD = hodinaPrelozeniSuduD + dobaCesty;
		while(hodinaNavratuDoPivovaruD > 23) {
			hodinaNavratuDoPivovaruD -= 24;
			denNavratuDoPivovaru++;
		}
		int hodinaNavratuDoPivovaru = (int)Math.round(hodinaNavratuDoPivovaruD);
		
		k.setCilovePrekladiste(p.getID());
		k.setDenDorazeniDoPrekladiste(denDorazeniDoPrekladiste);
		k.setHodinaDorazeniDoPrekladiste(hodinaDorazeniDoPrekladiste);
		k.setDenPrelozeniSudu(denPrelozeniSudu);
		k.setHodinaPrelozeniSudu(hodinaPrelozeniSudu);
		k.setDenNavratuDoPivovaru(denNavratuDoPivovaru);
		k.setHodinaNavratuDoPivovaru(hodinaNavratuDoPivovaru);
		
		k.setNaCeste(true);
		this.pocetKamionuNaCeste++;
		System.out.println("Kamion "+k.getID()+" vyrazil do prekladiste "+k.getCilovePrekladiste()+".");
	}
	
	/**
	 * Metoda postupne resici jednotlive objednavky tankovych hospod. U kazde objednavky se zkontroluje, zda jeji cas odpovida aktualnimu casu simulace.
	 * Pokud ano, probehne kontrola stavu volnych cisteren a mnozstvi piva v pivovaru. V pripade kladneho vysledku se objednavka dale zpracovava metodou pripravObjednavku.
	 * V opacnem pripade se objednavce nastavi novy cas - inkrementuje se o jednu hodinu... v pripade, ze prekroci maximalni povolenou mez (16 hodin),
	 * je jako hodina nastavena minimalni mez (8 hodin) a inkrementuje se den. Metoda pak pokracuje dalsi objednavkou.
	 */
	public void zpracujObjednavky() {	
		for(Objednavka o : this.objednavkyHospod) {
			if((o.getCasObednani() == Simulace.hodina) && (o.getDenObednani() == Simulace.den) && !(o.isVyrizena())) {
				if(((this.cisterny.size()-this.pocetCisterenNaCeste) > 0) && (o.getMnozstvi() <= this.stavPiva)) {
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
	 * Pokud ano, odcerpa se pozadovany pocet piva z pivovaru a preda se volne cisterne, ktera se pak resi metodou vysliCisternuDoHospody. Objednavka se zaroven odstrani z ArrayListu.
	 * @param o - objekt drzici onformace o objednavce
	 */
	private void pripravObjednavku(Objednavka o) {
		ArrayList<Integer> cesta = Matice.getNejkratsiCesta(StaticData.POCET_HOSPOD+StaticData.POCET_PREKLADIST, o.getIdObjednavajiciho());
		double vzdalenost = Matice.getDelkaNejkratsiCesty(cesta);
		double dobaCesty = vzdalenost / Cisterna.RYCHLOST;
		
		if(((Simulace.hodina + dobaCesty) > StaticData.MAX_HODINA_OBJEDNANI) && (dobaCesty <= (StaticData.MAX_HODINA_OBJEDNANI - StaticData.MIN_HODINA_OBJEDNANI))) {
			zmenDobuObjednani(o, Simulace.den+1, StaticData.MIN_HODINA_OBJEDNANI);
		}
		else {
			this.stavPiva -= o.getMnozstvi();
			Cisterna c = this.cisterny.get(0);
			for(int i = 0; i < this.cisterny.size(); i++) {
				c = this.cisterny.get(i);
				if(!(c.isNaCeste())) break;
			}
			c.nacerpejPivo(o.getMnozstvi());
			c.setCilovaHospoda(o.getIdObjednavajiciho());
			o.setVyrizena(true);
			
			vysliCisternuDoHospody(c, dobaCesty);
		}
	}
	
	/**
	 * Metoda resici nastaveni casu dojezdu cisterny do hospody a jejiho navratu do pivovaru (obdoba metody pro kamion).
	 * Volna cisterna se pak vlozi do ArrayListu vyuzivanych cisteren a je odstranena z ArrayListu volnych cisteren.
	 * @param c - cisterna, do ktere se casy ukladaji
	 * @param dobaCesty - doba cesty v hodinach
	 */
	private void vysliCisternuDoHospody(Cisterna c, double dobaCesty) {
		
		int denDorazeniDoHospody = Simulace.den;
		double hodinaDorazeniDoHospodyD = Simulace.hodina + dobaCesty;
		int hodinaDorazeniDoHospody = (int)Math.round(hodinaDorazeniDoHospodyD);
		
		int denPrecerpaniPiva = denDorazeniDoHospody;
		double hodinaPrecerpaniPivaD = hodinaDorazeniDoHospodyD + (c.getNaklad()*StaticData.HEKTOLITR_CAS);
		int hodinaPrecerpaniPiva = (int)Math.round(hodinaPrecerpaniPivaD);
		
		int denNavratuDoPivovaru = denDorazeniDoHospody;
		double hodinaNavratuDoPivovaruD = hodinaPrecerpaniPivaD + dobaCesty;
		
		while(hodinaNavratuDoPivovaruD > 23) {
			hodinaNavratuDoPivovaruD -= 24;
			denNavratuDoPivovaru++;
		}
		int hodinaNavratuDoPivovaru = (int)Math.round(hodinaNavratuDoPivovaruD);
		
		c.setDenDorazeniDoHospody(denDorazeniDoHospody);
		c.setHodinaDorazeniDoHospody(hodinaDorazeniDoHospody);
		c.setDenPrecerpaniPiva(denPrecerpaniPiva);
		c.setHodinaPrecerpaniPiva(hodinaPrecerpaniPiva);
		c.setDenNavratuDoPivovaru(denNavratuDoPivovaru);
		c.setHodinaNavratuDoPivovaru(hodinaNavratuDoPivovaru);
		
		c.setNaCeste(true);
		this.pocetCisterenNaCeste++;
		System.out.println("Cisterna "+c.getID()+" vyrazila do hospody "+c.getCilovaHospoda()+".");
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
}