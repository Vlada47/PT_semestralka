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

		Matice mojeMatice = new Matice(InputOutput.nactiCestyPivovaru(), InputOutput.nactiCestyPrekladiste(), InputOutput.nactiCestyHospod());
		double[][] maticeSousednosti = mojeMatice.getMaticeSousednosti();
		
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		
		for(int i = 0; i < 4000; i++) {
			
			int citac = 0;
			
			
			for(int j = 0; j < 4000; j++) {
				if(maticeSousednosti[i][j] > 0.0) citac++;
			}
			
			if(citac > 15) System.out.println("Hospoda "+i+" ma "+citac+" sousedu.");
			if(citac > max) max = citac;
			if(citac < min) min = citac;
		}
		
		System.out.println(max);
		System.out.println(min);
	}

}
