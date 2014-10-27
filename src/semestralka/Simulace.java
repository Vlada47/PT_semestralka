package semestralka;

import javax.swing.Timer;

public class Simulace {
	
	public static Pivovar pivovar = Pivovar.getInstance();
	public static Prekladiste[] prekladiste = InputOutput.nactiPrekladiste();
	public static Hospoda[] hospody = InputOutput.nactiHospody();
	static int i = 0;
	static Timer timer;

	static public void start() {

		najdiNejblizsiPrekladiste();
		Objednavka.generujRozpisObjednavek();

		timer = new Timer(2000, new MyTimerActionListener());
		timer.start();
	}
	
	private static void najdiNejblizsiPrekladiste() {
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
}
