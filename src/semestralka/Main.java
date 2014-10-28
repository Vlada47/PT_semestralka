package semestralka;

public class Main {

	public static void main(String[] args) {

		Window.startOkno();

		System.out.println("Spoustim...");

		Generator.generujSouradniceACesty();
		InputOutput.zapisSouradniceACestyDoSouboru();
		Matice.vygenerujMatice();
		Matice.nactiMaticiNejkratsichCestZeSouboru();
		System.out.println("Pripraveno...");

		// System.out.println(Simulace.objednavky.get(3580));
	}
}
