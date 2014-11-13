package semestralka;

import io.InputOutput;
import generovani_dat.Generator;
import generovani_dat.Matice;
import gui.Window;

public class Main {

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
