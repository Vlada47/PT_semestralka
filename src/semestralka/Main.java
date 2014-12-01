package semestralka;

import java.io.File;
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
		Window.zacni.setEnabled(false);

		System.out.println("Spoustim...");

		Generator.generujSouradniceACesty();
		InputOutput.zapisSouradniceACestyDoSouboru();
		Matice.vygenerujMatice();

		File f = new File(InputOutput.MATICE_CEST_SOUBOR);
		if (f.exists() && !f.isDirectory()) {
			Matice.nactiMaticiNejkratsichCestZeSouboru();
		} else {
			System.out.println("Generuji matici nejkratsich cest");
			Matice.vytvorNejkratsiCesty();
			InputOutput.zapisMaticeNejkratsichCest(Matice.maticeNejkratsichCest);
		}

		System.out.println("Pripraveno...");
		Window.zacni.setEnabled(true);
		Window.aktualizace.setEnabled(true);
	}
}
