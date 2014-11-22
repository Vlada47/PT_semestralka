package objekty_vozidla;

import objekty_budovy.HospodaSudova;
import objekty_budovy.Prekladiste;
import semestralka.StaticData;
import simulace.Simulace;

public class NakladniAuto {
	
	public static final double RYCHLOST = 70;
	public static final int KAPACITA = 30;
	
	private final int ID;
	private int pocetPlnychSudu;
	private int pocetPrazdnychSudu;
	private int cilovaHospoda;
	private int startovniPrekladiste;
	private int denDorazeniDoHospody;
	private int hodinaDorazeniDoHospody;
	private int denPrelozeniSudu;
	private int hodinaPrelozeniSudu;
	private int denNavratuDoPrekladiste;
	private int hodinaNavratuDoPrekladiste;
	private boolean naCeste;
	
	public NakladniAuto(int ID) {
		this.ID = ID;
		this.pocetPrazdnychSudu = 0;
		this.pocetPlnychSudu = 0;
		this.setNaCeste(false);
	}
	
	public void provedAkci() {
		if((this.hodinaDorazeniDoHospody == Simulace.hodina) && (this.denDorazeniDoHospody == Simulace.den)) {
			System.out.println("Nakladni auto "+this.ID+" z prekladiste "+this.startovniPrekladiste+" dovezlo "+this.pocetPlnychSudu+" sudu do hospody "+this.cilovaHospoda+".");
		}
		if((this.hodinaPrelozeniSudu == Simulace.hodina) && (this.denPrelozeniSudu == Simulace.den)) {
			vylozPlneSudy();
			nalozPrazdneSudy();
		}
		if((this.hodinaNavratuDoPrekladiste == Simulace.hodina) && (this.denNavratuDoPrekladiste == Simulace.den)) {
			System.out.println("Nakladni auto "+this.ID+" se vratilo do prekladiste "+this.startovniPrekladiste+".");
			vylozPrazdneSudy();
			this.setNaCeste(false);
			
			Prekladiste p = Simulace.prekladiste[this.startovniPrekladiste];
			p.setPocetAutNaCeste(p.getPocetAutNaCeste()-1);
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
	
	public void setCilovaHospoda(int cilovaHospoda) {
		this.cilovaHospoda = cilovaHospoda;
	}
	
	public int getCilovaHospoda() {
		return this.cilovaHospoda;
	}
	
	public void setStartovniPrekladiste(int startovniPrekladiste) {
		this.startovniPrekladiste = startovniPrekladiste;
	}
	
	public int getStartovniPrekladiste() {
		return this.startovniPrekladiste;
	}

	public void setDenDorazeniDoHospody(int denDorazeniDoHospody) {
		this.denDorazeniDoHospody = denDorazeniDoHospody;
	}

	public void setHodinaDorazeniDoHospody(int hodinaDorazeniDoHospody) {
		this.hodinaDorazeniDoHospody = hodinaDorazeniDoHospody;
	}

	public void setDenPrelozeniSudu(int denPrelozeniSudu) {
		this.denPrelozeniSudu = denPrelozeniSudu;
	}

	public void setHodinaPrelozeniSudu(int hodinaPrelozeniSudu) {
		this.hodinaPrelozeniSudu = hodinaPrelozeniSudu;
	}

	public void setDenNavratuDoPrekladiste(int denNavratuDoPrekladiste) {
		this.denNavratuDoPrekladiste = denNavratuDoPrekladiste;
	}

	public void setHodinaNavratuDoPrekladiste(int hodinaNavratuDoPrekladiste) {
		this.hodinaNavratuDoPrekladiste = hodinaNavratuDoPrekladiste;
	}
	
	public void nalozPlneSudy(int pocet) {
		this.pocetPlnychSudu += pocet;
	}
	
	private void nalozPrazdneSudy() {
		HospodaSudova h = Simulace.sudoveHospody[spocitejIndexHospody()];
		int pocet = h.odeberPrazdneSudy();
		this.pocetPrazdnychSudu += pocet;
	}
	
	private void vylozPlneSudy() {
		HospodaSudova h = Simulace.sudoveHospody[spocitejIndexHospody()];
		h.pridejPlneSudy(this.pocetPlnychSudu);
		this.pocetPlnychSudu = 0;
	}
	
	private void vylozPrazdneSudy() {
		Prekladiste p = Simulace.prekladiste[this.startovniPrekladiste];
		p.prijmiPrazdneSudy(this.pocetPrazdnychSudu);
		this.pocetPrazdnychSudu = 0;
	}
	
	private int spocitejIndexHospody() {
		int index = this.cilovaHospoda - 1;
		int id = this.cilovaHospoda;
		
		while(id > StaticData.POMER_HOSPOD) {
			index--;
			id -= StaticData.POMER_HOSPOD;
		}
		
		return index;
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
