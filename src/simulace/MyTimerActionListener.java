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
import semestralka.StaticData;

class MyTimerActionListener implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		
		if (Simulace.hodina == 24) {
			if(Simulace.den == StaticData.POCET_DNU) {
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
		
		
		provedObjednavky();
		provedAkciVozidel();
		if(Simulace.hodina == StaticData.MAX_HODINA_OBJEDNANI) spotrebujPivo();
		
		Simulace.hodina++;
	}
	
	private void spotrebujPivo() {
		for(HospodaSudova h : Simulace.sudoveHospody) {
			h.spotrebujPivo();
		}
		
		for(HospodaTankova h : Simulace.tankoveHospody){
			h.spotrebujPivo();
		}
	}

	private void provedObjednavky() {
		Simulace.pivovar.zpracujObjednavky();
		
		for(Prekladiste p : Simulace.prekladiste) {
			p.zpracujObjednavky();
		}
		
		if(Simulace.hodina == 16) {
			System.out.println("Zahajen rozvoz sudu do prekladist.");
			Simulace.pivovar.rozvozDoPrekladist();
		}
	}
	
	private void provedAkciVozidel() {
		for(Kamion k : Simulace.pivovar.kamiony) {
			if(k.isNaCeste()) {
				k.provedAkci();
			}
		}
		for(Cisterna c : Simulace.pivovar.cisterny) {
			if(c.isNaCeste()) {
				c.provedAkci();
			}
		}
		
		for(Prekladiste p : Simulace.prekladiste) {
			for(NakladniAuto a : p.nakladniAuta) {
				if(a.isNaCeste()) {
					a.provedAkci();
				}
			}
		}
	}
}