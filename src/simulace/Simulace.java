package simulace;

import io.InputOutput;
import generovani_dat.Generator;
import gui.Window;

import javax.swing.Timer;

import objekty_budovy.HospodaSudova;
import objekty_budovy.HospodaTankova;
import objekty_budovy.Pivovar;
import objekty_budovy.Prekladiste;
import objekty_ostatni.Pozice;
import semestralka.StaticData;

public class Simulace {
	
	public static Pivovar pivovar = Pivovar.getInstance();
	public static Prekladiste[] prekladiste = InputOutput.nactiPrekladiste();
	public static HospodaSudova[] sudoveHospody = InputOutput.nactiSudoveHospody();
	public static HospodaTankova[] tankoveHospody = InputOutput.nactiTankoveHospody();
	

	public static int den = 0;
	public static int hodina = 24;
	public static Timer timer;
	public static int[] maxAut = new int[prekladiste.length];
	public static int maxKamionu = 0;
	public static int maxCisteren = 0;

	static public void start() {

		Window.textArea.setText(null);
		najdiNejblizsiPrekladiste();
		Generator.generujObjednavky();

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
					hospoda.setIdPrekladiste(i);
				}
			}
		}
	}
}
