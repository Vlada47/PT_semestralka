package semestralka;

public class Main {

	public static void main(String[] args) {
		System.out.println("Spoustim...");
		
		Generator.generujSouradniceACesty();
		InputOutput.zapisSouradniceACestyDoSouboru();
		Matice.vygenerujMatice(true);
		Matice.nactiMaticiNejkratsichCestZeSouboru();

		//new ScrollPane();
		Objednavka.generujRozpisObjednavek();
		
		Simulace.start();
		
		System.out.println(Simulace.objednavky.get(205));
	}
}
