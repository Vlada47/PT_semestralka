package objekty_budovy;

import generovani_dat.Matice;

import java.util.ArrayList;

import objekty_ostatni.Objednavka;
import objekty_ostatni.Pozice;
import objekty_vozidla.Kamion;
import objekty_vozidla.NakladniAuto;
import semestralka.StaticData;
import simulace.Simulace;

/**
 * 
 * @author Vlada47 & Shag0n
 *
 */
public class Prekladiste{

	public static final int KAPACITA = 2000;

	private final int ID;
	private final Pozice pozice;
	private int pocetPlnychSudu;
	private int pocetPrazdnychSudu;
	public ArrayList<NakladniAuto> nakladniAuta;
	public ArrayList<Objednavka> objednavky;
	private int pocetPozadovanychSudu;
	private int pocetAutNaCeste;

	public Prekladiste(int ID, Pozice pozice) {
		this.ID = ID;
		this.pozice = pozice;
		this.pocetPlnychSudu = KAPACITA;
		this.pocetPrazdnychSudu = 0;
		
		this.nakladniAuta = new ArrayList<NakladniAuto>();
		this.objednavky = new ArrayList<Objednavka>();
		
		for(int i = 0; i < StaticData.POCET_AUT; i++) {
			this.nakladniAuta.add(new NakladniAuto(i, this.ID));
		}
		
		this.setPocetPozadovanychSudu(0);
		this.setPocetAutNaCeste(0);
	}
	
	public int getID() {
		return this.ID;
	}

	public Pozice getPosition() {
		return pozice;
	}
	
	public int getPocetAutNaCeste() {
		return this.pocetAutNaCeste;
	}

	public void setPocetAutNaCeste(int pocetAutNaCeste) {
		this.pocetAutNaCeste = pocetAutNaCeste;
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
	
	public int getPocetPozadovanychSudu() {
		return this.pocetPozadovanychSudu;
	}

	public void setPocetPozadovanychSudu(int pocetPozadovanychSudu) {
		this.pocetPozadovanychSudu = pocetPozadovanychSudu;
	}
	
	public void zpracujObjednavky() {
		for(Objednavka o : this.objednavky) {
			if((o.getDenObednani() <= Simulace.den) && (o.getCasObednani() <= Simulace.hodina) && !(o.isVyrizena())) {
				if(((this.nakladniAuta.size()-this.pocetAutNaCeste) > 0) && (o.getMnozstvi() <= this.pocetPlnychSudu)) {
					pripravObjednavku(o);
				}
				else {
					break;
				}
			}
		}
	}
	
	private void pripravObjednavku(Objednavka o) {
		for(NakladniAuto a : this.nakladniAuta) {
			if(!(a.isNaCeste())) {
				if((o.getMnozstvi() + a.getPocetPlnychSudu() <= NakladniAuto.KAPACITA) && (jeStihnutelna(a, o))) {
					this.pocetPlnychSudu -= o.getMnozstvi();
					this.pocetPozadovanychSudu += o.getMnozstvi();
					a.nalozPlneSudy(o.getMnozstvi());
					o.setVyrizena(true);
					return;
				}
			}
		}
	}
	
	private boolean jeStihnutelna(NakladniAuto a, Objednavka o) {
		ArrayList<Integer> cesta = Matice.getNejkratsiCesta(a.getPosledniCilovaHospoda(), o.getIdObjednavajiciho());
		double vzdalenost = Matice.getDelkaNejkratsiCesty(cesta);
		double dobaCesty = vzdalenost / NakladniAuto.RYCHLOST;
		
		//objednavky musi dorazit nejdele stejnou hodinu (jako kdy byla objednana) druheho dne:
		int maxDenVyrizeniObjednavky = o.getDenObednani() + 1;
		int maxHodinaVyrizeniObjednavky = o.getCasObednani();
		
		//vyrizena bude objednavka v case slozenem z casu cesty z posledni hospody a prelozeni sudu k case vyrizeni posledni objednavky
		int denVyrizeniObjednavky = a.getDenVyrizeniPosledniObjednavky();
		double hodinaVyrizeniObjednavkyD = a.getHodinaVyrizeniPosledniObjednavky() + dobaCesty + (o.getMnozstvi()*StaticData.SUD_CAS);
		
		//pokud vypocitany cas prekroci 1 den
		while(hodinaVyrizeniObjednavkyD >= 24) {
			hodinaVyrizeniObjednavkyD -= 24;
			denVyrizeniObjednavky++;
		}
		int hodinaVyrizeniObjednavky = (int)Math.round(hodinaVyrizeniObjednavkyD);
		
		//kontrola, zda se objednavka opravdu stihne dorucit
		if((denVyrizeniObjednavky >= maxDenVyrizeniObjednavky) && (hodinaVyrizeniObjednavky > maxHodinaVyrizeniObjednavky)) {
			return false;
		}
		else {
			a.addCilovaHospoda(o.getIdObjednavajiciho());
			a.addDenVyrizeniObjednavky(denVyrizeniObjednavky);
			a.addHodinaVyrizeniObjednavky(hodinaVyrizeniObjednavky);
			a.addMaxDenVyrizeniObjednavky(maxDenVyrizeniObjednavky);
			a.addMaxHodinaVyrizeniObjednavky(maxHodinaVyrizeniObjednavky);
			return true;
		}
	}
	
	public void prijmiPlneSudy(int pocet) {
		this.pocetPlnychSudu += pocet;
	}
	
	public void prijmiPrazdneSudy(int pocet) {
		this.pocetPrazdnychSudu += pocet;
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
	
	public String getVypis() {
		return "Prekladiste "+this.ID+" ma "+this.pocetPlnychSudu+"plnych a "+this.pocetPrazdnychSudu+" prazdnych sudu.";
	}
}
