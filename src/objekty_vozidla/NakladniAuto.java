package objekty_vozidla;

import generovani_dat.Matice;

import java.util.ArrayList;

import objekty_budovy.HospodaSudova;
import objekty_budovy.Prekladiste;
import semestralka.StaticData;
import simulace.Simulace;

public class NakladniAuto {
	
	public static final double RYCHLOST = 70;
	public static final int KAPACITA = 30;
	
	private final int ID;
	private final int startovniPrekladiste;
	private int pocetPlnychSudu;
	private int pocetPrazdnychSudu;
	private ArrayList<Integer> ciloveHospody;
	private ArrayList<Integer> objednavky;
	private ArrayList<Integer> denVyrizeni;
	private ArrayList<Integer> hodinaVyrizeni;
	private ArrayList<Integer> maxDenVyrizeni;
	private ArrayList<Integer> maxHodinaVyrizeni;
	private int denNavratuDoPrekladiste;
	private int hodinaNavratuDoPrekladiste;
	private boolean naCeste;
	
	public NakladniAuto(int ID, int startovniPrekladiste) {
		this.ID = ID;
		this.startovniPrekladiste = startovniPrekladiste;
		this.pocetPrazdnychSudu = 0;
		this.pocetPlnychSudu = 0;
		this.objednavky = new ArrayList<Integer>();
		this.ciloveHospody = new ArrayList<Integer>();
		this.denVyrizeni = new ArrayList<Integer>();
		this.hodinaVyrizeni = new ArrayList<Integer>();
		this.maxDenVyrizeni = new ArrayList<Integer>();
		this.maxHodinaVyrizeni = new ArrayList<Integer>();
		this.setNaCeste(false);
	}
	
	public void provedAkci() {
		if(!(this.naCeste)) {
			kontrolaKapacity();
		}
		if(!(this.naCeste)) {
			kontrolaCasu();
		}
		
		if(this.naCeste) {
			
			if(!(this.denVyrizeni.size() < 1)) {
				while((this.denVyrizeni.get(0) == Simulace.den) && (this.hodinaVyrizeni.get(0) == Simulace.hodina)) {
					System.out.println("Nakladni auto "+this.ID+" z prekladiste "+this.startovniPrekladiste+" dovezlo "+this.objednavky.get(0)+" sudu do hospody "+this.ciloveHospody.get(0)+".");
					vylozPlneSudy();
					nalozPrazdneSudy();
							
					this.ciloveHospody.remove(0);
					this.denVyrizeni.remove(0);
					this.hodinaVyrizeni.remove(0);
					this.maxDenVyrizeni.remove(0);
					this.maxHodinaVyrizeni.remove(0);
						
					if(this.denVyrizeni.size() < 1) {
						break;
					}
				}
			}
			
			if((this.denNavratuDoPrekladiste == Simulace.den) && (this.hodinaNavratuDoPrekladiste == Simulace.hodina)) {
				System.out.println("Nakladni auto "+this.ID+" se vratilo do prekladiste "+this.startovniPrekladiste+".");
				vylozPrazdneSudy();
				setNaCeste(false);
				
				Prekladiste p = Simulace.prekladiste[this.startovniPrekladiste];
				p.setPocetAutNaCeste(p.getPocetAutNaCeste()-1);
			}
		}
	}
	
	public int getID() {
		return this.ID;
	}
	
	public int getPocetPlnychSudu() {
		return this.pocetPlnychSudu;
	}
	
	public int getPocetPrazdnychSudu() {
		return this.pocetPrazdnychSudu;
	}
	
	public boolean isNaCeste() {
		return this.naCeste;
	}

	public void setNaCeste(boolean naCeste) {
		this.naCeste = naCeste;
	}
	
	public int getStartovniPrekladiste() {
		return this.startovniPrekladiste;
	}
	
	public void addCilovaHospoda(int cilovaHospoda) {
		this.ciloveHospody.add(cilovaHospoda);
	}
	
	public int getPosledniCilovaHospoda() {
		if(this.ciloveHospody.size() > 0) {
			return this.ciloveHospody.get(this.ciloveHospody.size()-1);
		}
		else {
			return (StaticData.POCET_HOSPOD + this.startovniPrekladiste);
		}
	}
	
	public void addDenVyrizeniObjednavky(int den) {
		this.denVyrizeni.add(den);
	}
	
	public int getDenVyrizeniPosledniObjednavky() {
		if(this.denVyrizeni.size() > 0) {
			return this.denVyrizeni.get(this.denVyrizeni.size()-1);
		}
		else {
			return Simulace.den;
		}
	}
	
	public void addMaxDenVyrizeniObjednavky(int den) {
		this.maxDenVyrizeni.add(den);
	}
	
	public void addHodinaVyrizeniObjednavky(int hodina) {
		this.hodinaVyrizeni.add(hodina);
	}
	
	public int getHodinaVyrizeniPosledniObjednavky() {
		if(this.hodinaVyrizeni.size() > 0) {
			return this.hodinaVyrizeni.get(this.hodinaVyrizeni.size()-1);
		}
		else {
			return Simulace.hodina;
		}
		
	}
	
	public void addMaxHodinaVyrizeniObjednavky(int hodina) {
		this.maxHodinaVyrizeni.add(hodina);
	}
	
	private void kontrolaKapacity() {
		if(this.pocetPlnychSudu >= KAPACITA && (Simulace.hodina >= StaticData.MIN_HODINA_OBJEDNANI && Simulace.hodina <= StaticData.MAX_HODINA_OBJEDNANI)) {
			nastavDobuNavratuDoPrekladiste();
			setNaCeste(true);
			Prekladiste p = Simulace.prekladiste[this.startovniPrekladiste];
			p.setPocetAutNaCeste(p.getPocetAutNaCeste()+1);
			System.out.println("Nakladni auto "+this.ID+" z prekladiste "+this.startovniPrekladiste+" vyrazilo s objednavkami. ");
		}
	}
	
	private void kontrolaCasu() {
		if(this.maxDenVyrizeni.size() > 0) {
			for(int i = 0; i < this.maxDenVyrizeni.size(); i++) {
				if((this.hodinaVyrizeni.get(i) >= this.maxHodinaVyrizeni.get(i)) && (this.denVyrizeni.get(i) >= this.maxDenVyrizeni.get(i)) || (Simulace.hodina == StaticData.MAX_HODINA_OBJEDNANI)) {
					nastavDobuNavratuDoPrekladiste();
					setNaCeste(true);
					Prekladiste p = Simulace.prekladiste[this.startovniPrekladiste];
					p.setPocetAutNaCeste(p.getPocetAutNaCeste()+1);
					System.out.println("Nakladni auto "+this.ID+" z prekladiste "+this.startovniPrekladiste+" vyrazilo s objednavkami. ");
					break;
				}
				else {
					int den = this.denVyrizeni.get(i);
					int hodina = this.hodinaVyrizeni.get(i) + 1;
					if(hodina >= 24) {
						hodina = 0;
						den++;
					}
					this.denVyrizeni.set(i, den);
					this.hodinaVyrizeni.set(i, hodina);
				}
			}
		}
	}
	
	public void nalozPlneSudy(int pocet) {
		this.pocetPlnychSudu += pocet;
		this.objednavky.add(pocet);
	}
	
	private void nalozPrazdneSudy() {
		HospodaSudova h = Simulace.sudoveHospody[spocitejIndexHospody(this.ciloveHospody.get(0))];
		int pocet = h.odeberPrazdneSudy(KAPACITA - (this.pocetPlnychSudu+this.pocetPrazdnychSudu));
		this.pocetPrazdnychSudu += pocet;
	}
	
	private void vylozPlneSudy() {
		HospodaSudova h = Simulace.sudoveHospody[spocitejIndexHospody(this.ciloveHospody.get(0))];
		h.pridejPlneSudy(this.objednavky.get(0));
		this.pocetPlnychSudu -= this.objednavky.get(0);
		this.objednavky.remove(0);
	}
	
	private void vylozPrazdneSudy() {
		Prekladiste p = Simulace.prekladiste[this.startovniPrekladiste];
		p.prijmiPrazdneSudy(this.pocetPrazdnychSudu);
		this.pocetPrazdnychSudu = 0;
	}
	
	private int spocitejIndexHospody(int id) {
		int index = id - 1;
		
		while(id > StaticData.POMER_HOSPOD) {
			index--;
			id -= StaticData.POMER_HOSPOD;
		}
		
		return index;
	}
	
	private void nastavDobuNavratuDoPrekladiste() {
		ArrayList<Integer> cesta = Matice.getNejkratsiCesta(this.ciloveHospody.get(this.ciloveHospody.size()-1), StaticData.POCET_HOSPOD+this.startovniPrekladiste);
		double vzdalenost = Matice.getDelkaNejkratsiCesty(cesta);
		double dobaCesty = vzdalenost / RYCHLOST;
		
		int denNavratuDoPrekladiste = getDenVyrizeniPosledniObjednavky();
		double hodinaNavratuDoPrekladisteD = getHodinaVyrizeniPosledniObjednavky() + dobaCesty;
		
		while(hodinaNavratuDoPrekladisteD >= 24) {
			hodinaNavratuDoPrekladisteD -= 24;
			denNavratuDoPrekladiste++;
		}
		
		int hodinaNavratuDoPrekladiste = (int)Math.round(hodinaNavratuDoPrekladisteD);
		
		this.denNavratuDoPrekladiste = denNavratuDoPrekladiste;
		this.hodinaNavratuDoPrekladiste = hodinaNavratuDoPrekladiste;
	}
	
	public String getVypis() {
		if(naCeste) {
			return "Nakladni auto "+this.ID+" je na ceste a veze "+this.pocetPlnychSudu+" plnych a "+this.pocetPrazdnychSudu+" prazdnych sudu.";
		}
		else {
			return "Nakladni "+this.ID+" je k dispozici v prekladisti.";
		}
	}
}
