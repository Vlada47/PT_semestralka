package semestralka;

//import java.util.Random;

public class Main {

	public static void main(String[] args) {

		Window.startOkno();

		System.out.println("Spoustim...");

		Generator.generujSouradniceACesty();
		InputOutput.zapisSouradniceACestyDoSouboru();
		Matice.vygenerujMatice();
		Matice.nactiMaticiNejkratsichCestZeSouboru();
		System.out.println("Pripraveno...");
		
		/*Random r = new Random();
		double objednavka;
		
		int pocet8 = 0;
		int pocet9 = 0;
		int pocet10 = 0;
		int pocet11 = 0;
		int pocet12 = 0;
		int pocet13 = 0;
		int pocet14 = 0;
		int pocet15 = 0;
		int pocet16 = 0;
		
		for(int i = 0; i < 4000; i++) {
			objednavka = (r.nextGaussian()*1.6+10);
			
			if (objednavka < 8) objednavka = 8;
			if (objednavka > 16) objednavka = 16;
			
			if(((int) Math.round(objednavka)) < 9) pocet8++;
			else if(((int) Math.round(objednavka)) < 10) pocet9++;
			else if(((int) Math.round(objednavka)) < 11) pocet10++;
			else if(((int) Math.round(objednavka)) < 12) pocet11++;
			else if(((int) Math.round(objednavka)) < 13) pocet12++;
			else if(((int) Math.round(objednavka)) < 14) pocet13++;
			else if(((int) Math.round(objednavka)) < 15) pocet14++;
			else if(((int) Math.round(objednavka)) < 16) pocet15++;
			else pocet16++;
		}
		
		int sum = pocet8 + pocet9 + pocet10 + pocet11 + pocet12 + pocet13 + pocet14 + pocet15 + pocet16;
		
		System.out.println("08:00 - "+pocet8);
		System.out.println("09:00 - "+pocet9);
		System.out.println("10:00 - "+pocet10);
		System.out.println("11:00 - "+pocet11);
		System.out.println("12:00 - "+pocet12);
		System.out.println("13:00 - "+pocet13);
		System.out.println("14:00 - "+pocet14);
		System.out.println("15:00 - "+pocet15);
		System.out.println("16:00 - "+pocet16);
		System.out.println(sum);*/
	}
}
