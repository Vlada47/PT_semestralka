package semestralka;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MyTimerActionListener implements ActionListener {

	public void actionPerformed(ActionEvent e) {

		System.out.println("Je " + Simulace.i + ":00");

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
		Simulace.i++;
		if (Simulace.i == 24) {
			Simulace.timer.stop();
		}
	}
}