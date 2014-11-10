package objekty_budovy;

import generovani_dat.Matice;

import java.util.ArrayList;

import objekty_ostatni.Objednavka;
import objekty_ostatni.Pozadavek;
import objekty_ostatni.Pozice;
import objekty_vozidla.Cisterna;
import objekty_vozidla.Kamion;
import semestralka.StaticData;

public class Pivovar extends Budova {
	
	public final int DENNI_PRODUKCE = 7000;

	private Pozice pozice;
	private int stavPiva;
	public ArrayList<Kamion> dostupneKamiony;
	public ArrayList<Kamion> kamionyNaCeste;
	public ArrayList<Cisterna> dostupneCisterny;
	public ArrayList<Cisterna> cisternyNaCeste;
	public ArrayList<Objednavka> objednavkyHospod;
	public ArrayList<Pozadavek> pozadavkyPrekladist;

	private static Pivovar instance;

	private Pivovar() {
		this.stavPiva = this.DENNI_PRODUKCE;
		this.pozice = new Pozice(StaticData.SOURADNICE_PIVOVARU_X,StaticData.SOURADNICE_PIVOVARU_Y);
		
		this.stavPiva = 7000;
		
		this.dostupneKamiony = new ArrayList<Kamion>();
		this.kamionyNaCeste = new ArrayList<Kamion>();
		this.dostupneCisterny = new ArrayList<Cisterna>();
		this.cisternyNaCeste = new ArrayList<Cisterna>();
		
		this.objednavkyHospod = new ArrayList<Objednavka>();
		this.pozadavkyPrekladist = new ArrayList<Pozadavek>();
		
		for(int i = 0; i < StaticData.POCET_KAMIONU; i++) {
			this.dostupneKamiony.add(new Kamion(i));
		}
		
		for(int i = 0; i < StaticData.POCET_CISTEREN; i++) {
			this.dostupneCisterny.add(new Cisterna(i));
		}
	}

	public static Pivovar getInstance() {
		if (instance == null) {
			instance = new Pivovar();
		}
		return instance;
	}
	
	public int getStavPiva() {
		return this.stavPiva;
	}
	
	public void vyprodukujPivo() {
		this.stavPiva += this.DENNI_PRODUKCE;
	}
	
	public void pridejObjednavku(Objednavka o) {
		this.objednavkyHospod.add(o);
	}
	
	public void vyridObjednavku() {
		Objednavka o = this.objednavkyHospod.get(0);
		if(!(this.dostupneCisterny.isEmpty()) && o.getMnozstvi() <= this.stavPiva) {
			if(nalozObjednavku(o)) {
				posliObjednavku(o);
			}
			System.out.println("Objednavka z pivovaru do hospody "+o.getIdObjednavajiciho()+" odeslana.");
			this.objednavkyHospod.remove(0);
		}
	}
	
	private boolean nalozObjednavku(Objednavka o) {
		Cisterna c = this.dostupneCisterny.get(0);
		
		if(c.nacerpejPivo(o.getMnozstvi())) {
			this.stavPiva -= o.getMnozstvi();
			return true;
		}
		else return false;		
	}
	
	private void posliObjednavku(Objednavka o) {
		Cisterna c = this.dostupneCisterny.get(0);
		ArrayList<Integer> cesta = Matice.getNejkratsiCesta(StaticData.POCET_HOSPOD+StaticData.POCET_PREKLADIST, o.getIdObjednavajiciho());
		double v = Matice.getDelkaNejkratsiCesty(cesta);
		c.setVzdalenost(v);
		c.setCil(o.getIdObjednavajiciho(), o.getIndexObjednavajiciho());
		this.cisternyNaCeste.add(c);
		this.dostupneCisterny.remove(0);
	}
	
	public void odvezSudyDoPrekladiste() {
		Pozadavek p = this.pozadavkyPrekladist.get(0);
		if(!(this.dostupneKamiony.isEmpty()) && (p.getPocetSudu() <= (this.stavPiva*2))) {
			if(nalozSudyDoKamionu(p.getPocetSudu())) {
				odesliKamionSeSudy(p.getPrekladiste());
			}
			System.out.println("Kamion se sudy z pivovaru vyjel do prekladiste "+p.getPrekladiste()+".");
			this.pozadavkyPrekladist.remove(0);
		}
	}
	
	private boolean nalozSudyDoKamionu(int pocet) {
		Kamion k = this.dostupneKamiony.get(0);
		if(k.nalozPlneSudy(pocet)) {
			this.stavPiva -= (pocet*0.5);
			return true;
		}
		return false;
	}
	
	private void odesliKamionSeSudy(int prekladiste) {
		Kamion k = this.dostupneKamiony.get(0);
		ArrayList<Integer> cesta = Matice.getNejkratsiCesta(StaticData.POCET_HOSPOD+StaticData.POCET_PREKLADIST, prekladiste+StaticData.POCET_HOSPOD);
		double v = Matice.getDelkaNejkratsiCesty(cesta);
		k.setVzdalenost(v);
		k.setCil(prekladiste);
		this.kamionyNaCeste.add(k);
		this.dostupneKamiony.remove(0);
	}
	
	public void odvezPivoDoHospody(int pocetHektolitru) {
		if(pocetHektolitru <= this.stavPiva) {
			this.stavPiva -= pocetHektolitru;
		}
		else {
			System.out.println("Nedostatek piva v pivovaru!");
			System.exit(1);
		}
	}
}