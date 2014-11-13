package simulace;

import io.InputOutput;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import objekty_budovy.HospodaSudova;
import objekty_budovy.HospodaTankova;
import objekty_budovy.Pivovar;
import objekty_budovy.Prekladiste;
import objekty_ostatni.Objednavka;

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
			Objednavka.generujRozpisObjednavek();
		}
		
		System.out.println("");
		System.out.println("Je "+ Simulace.den +". den " + Simulace.hodina + ":00");
		System.out.println("");
		
		provedObjednavky();
		rozesliObjednavky();
		rozvozObjednavek();
		rozesliPozadavkyDoPrekladist();
		rozvozDoPrekladist();
		tiskniStav();
		
		Simulace.hodina++;
	}

	private void provedObjednavky() {
		
		for(int i = 0; i < Simulace.sudoveHospody.length; i++) {
			HospodaSudova hospoda = Simulace.sudoveHospody[i];
			
			if (hospoda.casObjednani == Simulace.hodina) {
				for (Prekladiste p : Simulace.prekladiste) {
					if (hospoda.idPrekladiste == p.getID()) {
						p.pridejObjednavku(new Objednavka(hospoda.casObjednani, hospoda.ID, i, p.getID(),
								hospoda.mnozstviObjednat));
					}
				}
				System.out.println("Hospoda" + hospoda.ID + " (sudova) si objednala " + hospoda.mnozstviObjednat + " sudu.");
			}
		}
		
		for(int i = 0; i < Simulace.tankoveHospody.length; i++) {
			HospodaTankova hospoda = Simulace.tankoveHospody[i];
			
			if(hospoda.casObjednani == Simulace.hodina) {
				Simulace.pivovar.pridejObjednavku(new Objednavka(hospoda.casObjednani, hospoda.ID, i, 0, hospoda.mnozstviObjednat));
				System.out.println("Hospoda "+hospoda.ID+" (tankova) si objednala "+hospoda.mnozstviObjednat+" hektolitru.");
			}
			
		}
	}

	private void rozesliObjednavky() {
		for (Prekladiste p : Simulace.prekladiste) {
			while (!(p.objednavky.isEmpty())) {
				p.vyridObjednavku();
			}
		}
		
		while(!(Simulace.pivovar.objednavkyHospod.isEmpty())) {
			Simulace.pivovar.vyridObjednavku();
		}
	}

	private void rozvozObjednavek() {
		for (Prekladiste p : Simulace.prekladiste) {
			for (int i = 0; i < p.autaNaCeste.size(); i++) {
				p.autaNaCeste.get(i).vykonejCestu();
			}
		}
		
		for(int i = 0; i < Simulace.pivovar.cisternyNaCeste.size(); i++) {
			Simulace.pivovar.cisternyNaCeste.get(i).vykonejCestu();
		}
	}

	private void rozesliPozadavkyDoPrekladist() {
		Pivovar p = Simulace.pivovar;
		while (!(p.pozadavkyPrekladist.isEmpty())) {
			p.odvezSudyDoPrekladiste();
		}
	}

	private void rozvozDoPrekladist() {
		Pivovar p = Simulace.pivovar;
		for (int i = 0; i < p.kamionyNaCeste.size(); i++) {
			p.kamionyNaCeste.get(i).vykonejCestu();
		}
	}
	
	private void tiskniStav() {
		System.out.println("");
		System.out.println("Mnozstvi piva v pivovaru: "+Simulace.pivovar.getStavPiva()+" hektolitru.");
		System.out.println("Pocet kamionu na ceste: "+Simulace.pivovar.kamionyNaCeste.size()+".");
		System.out.println("Pocet dostupnych kamionu: "+Simulace.pivovar.dostupneKamiony.size()+".");
		for(Prekladiste p : Simulace.prekladiste) {
			System.out.println("Mnozstvi plnych sudu v prekladisti "+p.getID()+": "+p.getPocetPlnychSudu()+". Mnozstvi prazdnych sudu: "+p.getPocetPrazdnychSudu());
		}
		System.out.println("");
	}
}