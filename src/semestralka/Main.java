package semestralka;

public class Main {

	public static void main(String[] args) {
		System.out.println("Spoustim...");
		
		Generator.generujSouradniceACesty();
		InputOutput.zapisSouradniceACestyDoSouboru();
		Matice.vygenerujMatice(true);
		Matice.nactiMaticiNejkratsichCestZeSouboru();
	}
}
