package semestralka;

import io.InputOutput;
import generovani_dat.Generator;
import generovani_dat.Matice;
import gui.Window;

/**
 * Hlavni trida programu.
 * @author Vlada47 & Shag0n
 *
 */
public class Main {
	
	/**
	 * Hlavni metoda programu.
	 * Vytvori GUI a spusti generovani souradnic a cest jednotlivych budov.
	 * Nasledne vytvori matici nezbytnou pro hledani nejkratsich cest v grafu.
	 * @param args - vstupni argumenty programu
	 */
	public static void main(String[] args) {

		Window.startOkno();

		System.out.println("Spoustim...");

		Generator.generujSouradniceACesty();
		InputOutput.zapisSouradniceACestyDoSouboru();
		Matice.vygenerujMatice();
		Matice.nactiMaticiNejkratsichCestZeSouboru();
		System.out.println("Pripraveno...");
	}
}
