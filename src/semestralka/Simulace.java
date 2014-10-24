package semestralka;

import java.util.ArrayList;

public class Simulace {

	static Hospoda[] hospody = InputOutput.nactiHospody();
	static Prekladiste[] prekladiste = InputOutput.nactiPrekladiste();
	static ArrayList<Objednavka> objednavky = new ArrayList<Objednavka>();

	
	static public void start(){
		
		for (int i = 0; i < 24; i++) {
			
			for (Hospoda hospoda : hospody) {
				
				if(hospoda.casObjednani==i)objednavky.add(new Objednavka(hospoda.casObjednani,hospoda.ID,hospoda.idPrekladiste,hospoda.mnozstviObjednat));
				
			}
					
//			try {
//				Thread.sleep(3000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}	
	}
	
	
	
	
	
	
	
}
