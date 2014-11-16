package simulace;

import io.InputOutput;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import objekty_budovy.HospodaSudova;
import objekty_budovy.HospodaTankova;
import objekty_budovy.Prekladiste;
import objekty_vozidla.Cisterna;
import objekty_vozidla.Kamion;
import objekty_vozidla.NakladniAuto;

class MyTimerActionListener implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		
		if (Simulace.hodina == 24) {
			if(Simulace.den == 7) {
				InputOutput.zapisVysledek();
				Simulace.timer.stop();
			}
			Simulace.hodina = 0;
			Simulace.den++;
			Simulace.pivovar.vyprodukujPivo();
		}
		
		System.out.println("");
		System.out.println("Je "+ Simulace.den +". den " + Simulace.hodina + ":00");
		System.out.println("");
		
		if(Simulace.hodina == 16) spotrebujPivo();
		provedObjenavky();
		provedAkciVozidel();
		
		tiskniStav();
		
		Simulace.hodina++;
	}
	
	private void spotrebujPivo() {
		for(HospodaSudova h : Simulace.sudoveHospody) h.spotrebujPivo();
		for(HospodaTankova h : Simulace.tankoveHospody) h.spotrebujPivo();
	}

	private void provedObjenavky() {
		Simulace.pivovar.zpracujObjednavky();
		for(Prekladiste p : Simulace.prekladiste) p.zpracujObjednavky();
		
		if(Simulace.hodina == 16) {
			Simulace.pivovar.rozvozDoPrekladist();
		}
	}
	
	private void provedAkciVozidel() {
		for(Kamion k : Simulace.pivovar.kamionyNaCeste) k.provedAkci();
		for(Cisterna c : Simulace.pivovar.cisternyNaCeste) c.provedAkci();
		for(Prekladiste p : Simulace.prekladiste) for(NakladniAuto a : p.autaNaCeste) a.provedAkci();
	}
	
	private void tiskniStav() {
		System.out.println("");
		System.out.println("Mnozstvi piva v pivovaru: "+Simulace.pivovar.getStavPiva()+" hektolitru.");
		System.out.println("Pocet kamionu na ceste: "+Simulace.pivovar.kamionyNaCeste.size()+".");
		System.out.println("Pocet dostupnych kamionu: "+Simulace.pivovar.dostupneKamiony.size()+".");
		System.out.println("Pocet cisteren na ceste: "+Simulace.pivovar.cisternyNaCeste.size()+".");
		System.out.println("Pocet dostupnych cisteren: "+Simulace.pivovar.dostupneCisterny.size()+".");
		for(Prekladiste p : Simulace.prekladiste) {
			System.out.println("Mnozstvi plnych sudu v prekladisti "+p.getID()+": "+p.getPocetPlnychSudu()+". Mnozstvi prazdnych sudu: "+p.getPocetPrazdnychSudu()+".");
			System.out.println("Pocet nakladnich aut z prekladiste "+p.getID()+" na ceste: "+p.autaNaCeste.size()+".");
			System.out.println("Pocet dostupnych nakladnich aut z prekladiste "+p.getID()+": "+p.dostupnaAuta.size()+".");
		}
		System.out.println("");
	}
}