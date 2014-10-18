package semestralka;

import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		Generator.generujPozicePrekladist();
		Generator.generujPoziceHospod();
		Generator.generujCestyPivovaru();
		Generator.generujCestyPrekladist();
		Generator.generujCestyHospod();
		InputOutput.zapisPrekladiste(Generator.souradnicePrekladist);
		InputOutput.zapisHospody(Generator.souradniceHospod);
		InputOutput.zapisCestPivovaru(Generator.cestyPivovaru);
		InputOutput.zapisCestPrekladist(Generator.cestyPrekladist);
		InputOutput.zapisCestHospod(Generator.cestyHospod);

		//Matice mojeMatice = new Matice(Generator.cestyPivovaru, Generator.cestyPrekladist, Generator.cestyHospod);
		//double[][] maticeSousednosti = mojeMatice.getMaticeSousednosti();
	}

}
