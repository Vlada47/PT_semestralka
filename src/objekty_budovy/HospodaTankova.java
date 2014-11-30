package objekty_budovy;

import io.InputOutput;

import java.util.ArrayList;

import objekty_ostatni.Pozice;
import semestralka.StaticData;
import simulace.Simulace;

public class HospodaTankova {
	
	private final Pozice pozice;
	private final int ID;
	private int stavPiva;
	private ArrayList<Integer> denniSpotreba;
	
	private String[] dovezeneObjednavky;
	private String[] vozidla;
	
	public HospodaTankova(int ID, Pozice pozice) {
		this.ID = ID;
		this.pozice = pozice;
		this.stavPiva = 6;
		this.denniSpotreba = new ArrayList<Integer>();
		this.dovezeneObjednavky = new String[StaticData.POCET_DNU];
		this.vozidla = new String[StaticData.POCET_DNU];
	}
	
	public void spotrebujPivo(){
		if(this.stavPiva < this.denniSpotreba.get(0)) {
			System.err.println("Tankova hospoda "+this.ID+" nema dostatek piva!");
			InputOutput.zapisVysledek();
			System.exit(1);
		}
		else {
			this.stavPiva -= this.denniSpotreba.get(0);
			this.denniSpotreba.remove(0);
		}	
	}
	
	public int getID() {
		return this.ID;
	}

	public Pozice getPosition() {
		return pozice;
	}
	
	public String[] getDovezeneObjednavky() {
		return this.dovezeneObjednavky;
	}
	
	public String[] getVozidla() {
		return this.vozidla;
	}
	
	public void addDenniSpotreba(int mnozstvi) {
		this.denniSpotreba.add(mnozstvi);
	}
	
	public void nacerpejPivo(int mnozstvi) {
		this.stavPiva += mnozstvi;
		
		this.dovezeneObjednavky[Simulace.den-1] += mnozstvi+"/";
	}
	
	public void ulozCisternu(int id) {
		this.vozidla[Simulace.den-1] += "Cisterna "+id+"/";
	}
	
	public String getVypis() {
		return "Tankova hospoda "+this.ID+" ma "+this.stavPiva+"hl piva.";
	}
}
