package objekty_budovy;

import io.InputOutput;

import java.util.ArrayList;

import objekty_ostatni.Pozice;

public class HospodaTankova extends Budova {
	
	private final Pozice pozice;
	private final int ID;
	private int stavPiva;
	private ArrayList<Integer> denniSpotreba;
	
	public HospodaTankova(int ID, Pozice pozice) {
		this.ID = ID;
		this.pozice = pozice;
		this.stavPiva = 6;
		this.denniSpotreba = new ArrayList<Integer>();
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
	
	public int getStavPiva() {
		return this.stavPiva;
	}

	public Pozice getPosition() {
		return pozice;
	}
	
	public void addDenniSpotreba(int mnozstvi) {
		this.denniSpotreba.add(mnozstvi);
	}
	
	public void nacerpejPivo(int mnozstvi) {
		this.stavPiva += mnozstvi;
	}
}
