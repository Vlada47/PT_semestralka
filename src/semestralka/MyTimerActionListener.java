package semestralka;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MyTimerActionListener implements ActionListener {

	public void actionPerformed(ActionEvent e) {

		System.out.println("Je " + Simulace.i + ":00");
		
		provedObjednavky();
		rozesliObjednavky();
		rozvozObjednavek();
		//rozesliPozadavkyDoPrekladist();
		//rozvozDoPrekladist();
	
		Simulace.i++;
		if (Simulace.i == 24) {
			Simulace.timer.stop();
		}
	}
	
	private void provedObjednavky() {
		for (Hospoda hospoda : Simulace.hospody) {

			if (hospoda.casObjednani == Simulace.i) {
				for(Prekladiste p : Simulace.prekladiste) {
					if(hospoda.idPrekladiste == p.getID()) {
						p.pridejObjednavku(new Objednavka(hospoda.casObjednani, hospoda.ID, p.getID(),
								hospoda.mnozstviObjednat));
					}
				}
				System.out.println("Hospoda" + hospoda.ID + " si objednala " + hospoda.mnozstviObjednat + " litru");
			}
		}
	}
	
	private void rozesliObjednavky() {
		for(Prekladiste p : Simulace.prekladiste) {
			while(!(p.objednavky.isEmpty())) {
				p.vyridObjednavku();
			}
		}
	}
	
	private void rozvozObjednavek() {
		for(Prekladiste p : Simulace.prekladiste) {
			for(int i = 0; i < p.autaNaCeste.size(); i++) {
				p.autaNaCeste.get(i).vykonejCestu();
			}
		}
	}
	
	private void rozesliPozadavkyDoPrekladist() {
		Pivovar p = Simulace.pivovar;
		while(!(p.pozadavkyPrekladist.isEmpty())) {
			p.odvezSudyDoPrekladiste();
		}
	}
	
	private void rozvozDoPrekladist() {
		Pivovar p = Simulace.pivovar;
		
		for(int i = 0; i < p.kamionyNaCeste.size(); i++) {
			p.kamionyNaCeste.get(i).vykonejCestu();
		}
	}
}