package semestralka;

public class Main {

	public static void main(String[] args) {
		Generator.generujPozicePrekladist();
		Generator.generujPoziceHospod();
		Generator.generujCestyPrekladist();
		Generator.generujCestyHospod();
		InputOutput.zapisPrekladiste(Generator.souradnicePrekladist);
		InputOutput.zapisHospody(Generator.souradniceHospod);
		InputOutput.zapisCestPrekladist(Generator.cestyPrekladist);
		InputOutput.zapisCestHospod(Generator.cestyHospod);
		//Window.vytvorOkno();
	}

}
