package semestralka;

import java.util.ArrayList;

public class Simulace {

	static Hospoda[] hospody = InputOutput.nactiHospody();
	static Prekladiste[] prekladiste = InputOutput.nactiPrekladiste();
	static ArrayList<Objednavka> objednavky = new ArrayList<Objednavka>();

	static private void najdiNejblizsiPrekladiste() {
		double vzdalenost;
		double nejblizsi;
		Pozice poziceHospody;
		Pozice pozicePrekladiste;
		for (Hospoda hospoda : hospody) {

			if (hospoda.ID % 20 != 0) {

				poziceHospody = hospoda.getPosition();
				nejblizsi = 999999;

				for (int i = 0; i < prekladiste.length; i++) {

					pozicePrekladiste = prekladiste[i].getPosition();

					vzdalenost = Math.sqrt(Math.pow((double) (poziceHospody.x - pozicePrekladiste.x), 2.0)
							+ Math.pow((double) (poziceHospody.y - pozicePrekladiste.y), 2.0));

					if (vzdalenost < nejblizsi) {
						nejblizsi = vzdalenost;
						hospoda.idPrekladiste = i;
					}
				}
			} else {
				hospoda.idPrekladiste = 8;
			}
		}
	}

	static public void start() {

		najdiNejblizsiPrekladiste();

		for (int i = 0; i < 24; i++) {

			for (Hospoda hospoda : hospody) {

				if (hospoda.casObjednani == i)
					objednavky.add(new Objednavka(hospoda.casObjednani, hospoda.ID, hospoda.idPrekladiste,
							hospoda.mnozstviObjednat));

			}

			// try {
			// Thread.sleep(3000);
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
		}
	}
}
