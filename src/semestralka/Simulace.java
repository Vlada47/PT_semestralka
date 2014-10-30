package semestralka;

import javax.swing.Timer;

public class Simulace {
	
	public static Pivovar pivovar = Pivovar.getInstance();
	public static Prekladiste[] prekladiste = InputOutput.nactiPrekladiste();
	public static HospodaSudova[] sudoveHospody = InputOutput.nactiSudoveHospody();
	public static HospodaTankova[] tankoveHospody = InputOutput.nactiTankoveHospody();
	
	static int i = 0;
	static Timer timer;

	static public void start() {

		Window.textArea.setText(null);
		najdiNejblizsiPrekladiste();
		Objednavka.generujRozpisObjednavek();

		timer = new Timer(StaticData.SIMULACE_MILIS, new MyTimerActionListener());
		timer.start();
	}
	
	private static void najdiNejblizsiPrekladiste() {
		double vzdalenost;
		double nejblizsi;
		Pozice poziceHospody;
		Pozice pozicePrekladiste;
		for (HospodaSudova hospoda : sudoveHospody) {

			poziceHospody = hospoda.getPosition();
			nejblizsi = Double.MAX_VALUE;

			for (int i = 0; i < prekladiste.length; i++) {
				pozicePrekladiste = prekladiste[i].getPosition();

				vzdalenost = Math.sqrt(Math.pow((double) (poziceHospody.x - pozicePrekladiste.x), 2.0)
						+ Math.pow((double) (poziceHospody.y - pozicePrekladiste.y), 2.0));

				if (vzdalenost < nejblizsi) {
					nejblizsi = vzdalenost;
					hospoda.idPrekladiste = i;
				}
			}
		}
	}
}
