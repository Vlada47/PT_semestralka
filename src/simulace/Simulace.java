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

/**
 * Trida obsahujici staticke promenne a metody pro praci se simulaci programu.
 * @author Vlada47 & Shag0n
 *
 */
public class Simulace {
	
	/**
	 * Instance tridy Pivovar.
	 */
	public static Pivovar pivovar = Pivovar.getInstance();
	
	/**
	 * Pole obsahujici instance tridy Prekladiste.
	 */
	public static Prekladiste[] prekladiste = InputOutput.nactiPrekladiste();
	
	/**
	 * Pole obsahujici instance tridy HospodaSudova.
	 */
	public static HospodaSudova[] sudoveHospody = InputOutput.nactiSudoveHospody();
	
	/**
	 * Pole obsahujici instance tridy HospodaTankova.
	 */
	public static HospodaTankova[] tankoveHospody = InputOutput.nactiTankoveHospody();
	
	/**
	 * Promenna pro uchovani aktualniho dne simulace. Na pocatku inicializovana na 0.
	 */
	public static int den = 0;
	
	/**
	 * Promenna pro uchovani aktualni hodiny simulace. Na pocatku je inicializovana na 24.
	 */
	public static int hodina = 24;
	
	/**
	 * Promenna pro ulozeni instance tridy Timer, pro beh simulace.
	 */
	public static Timer timer;
	
	/**
	 * Metoda spoustejici simulaci.
	 * Nejprve vyprazdni text areau okna aplikaci (pro vypisovani zprav z prubehu simulace).
	 * Pote spusti metodu pro prirazeni hospod k nejblizsimu prekladisti.
	 * Nasledne vygeneruje objednavky hospod pomoci metody generujObjednavky ze tridy Generator.
	 * Nakonec vytvori instanci tridy Timer a spusti samotnou simulaci.
	 */
	static public void start() {

		Window.textArea.setText(null);
		najdiNejblizsiPrekladiste();
		Generator.generujObjednavky();

		timer = new Timer(StaticData.SIMULACE_MILIS, new MyTimerActionListener());
		timer.start();
	} 
	
	/**
	 * Metoda pro urceni prekladiste ke kazde sudove hospode - toto prekladiste pak bude vyrizovat objednavky dane hospody.
	 * Pro kazdou hospodu provede vypocet, do ktereho prekladiste to ma dana hospoda nejblize, a prekladiste s nejmensi vzdalenosti je pak hospode prirazeno.
	 */
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
