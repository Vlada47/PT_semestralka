package simulace;

import io.InputOutput;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import objekty_budovy.HospodaSudova;
import objekty_budovy.HospodaTankova;
import objekty_budovy.Prekladiste;
import objekty_vozidla.Cisterna;
import objekty_vozidla.Kamion;
import objekty_vozidla.NakladniAuto;
import semestralka.StaticData;

/**
 * Trida implementujici ActionListener a obsahujici metody na praci se simulaci.
 * @author Vlada47 & Shag0n
 *
 */
class MyTimerActionListener implements ActionListener {
	
	/**
	 * Implementace metody actionPerformed z tridy ActionListener.
	 * Nejprve provede casove kontroly, zda neubehl cely den, pripadne cela simulace, na jejimz koncivypise log, statistiky a ukonci timer simulace.
	 * Na konci dne se take provadi produkce piva v pivovaru.
	 * Dale vypise na vystup, jaky je den a hodina a zacne provadet jednotlive akce pro prubeh simulace - provedeni objednavek, provedeni akce nad vozidly a spotrebu piva v hospodach.
	 */
	public void actionPerformed(ActionEvent e) {
		
		if (Simulace.hodina == 24) {
			if(Simulace.den == StaticData.POCET_DNU) {
				InputOutput.zapisVysledek();
				zapisStatistikyHospod();
				zapisStatistikyVozidel();
				Simulace.timer.stop();
			}
			Simulace.hodina = 0;
			Simulace.den++;
			Simulace.pivovar.vyprodukujPivo();
		}
		
		System.out.println("");
		System.out.println("Je "+ Simulace.den +". den " + Simulace.hodina + ":00");
		System.out.println("");
		
		
		provedObjednavky();
		provedAkciVozidel();
		if(Simulace.hodina == StaticData.MAX_HODINA_OBJEDNANI) spotrebujPivo();
		
		Simulace.hodina++;
	}
	
	/**
	 * Metoda vyvolavajici denni spotrebu piva na jednotlivych hospodach.
	 */
	private void spotrebujPivo() {
		for(HospodaSudova h : Simulace.sudoveHospody) {
			h.spotrebujPivo();
		}
		
		for(HospodaTankova h : Simulace.tankoveHospody){
			h.spotrebujPivo();
		}
	}
	
	/**
	 * Metoda vyvolavajici zpracovani objednavek v pivovaru a jednotlivych prekladistich.
	 * Pokud aktualni hodina simulace odpovida 16. hodine (tou dobou jiz prekladiste maji zjisteno, kolik sudu za den spotrebovala), vyvola metodu pro rozvoz sudu z pivovaru do prekladist.
	 */
	private void provedObjednavky() {
		Simulace.pivovar.zpracujObjednavky();
		
		for(Prekladiste p : Simulace.prekladiste) {
			p.zpracujObjednavky();
		}
		
		if(Simulace.hodina == 16) {
			System.out.println("Zahajen rozvoz sudu do prekladist.");
			Simulace.pivovar.rozvozDoPrekladist();
		}
	}
	
	/**
	 * Metoda postupne volajici na jednotlivymi vozidly, ktere jsou oznacene jako "na ceste", jejich hlavni metodu - provedAkci.
	 */
	private void provedAkciVozidel() {
		for(Kamion k : Simulace.pivovar.kamiony) {
			if(k.isNaCeste()) {
				k.provedAkci();
			}
		}
		for(Cisterna c : Simulace.pivovar.cisterny) {
			if(c.isNaCeste()) {
				c.provedAkci();
			}
		}
		
		for(Prekladiste p : Simulace.prekladiste) {
			for(NakladniAuto a : p.nakladniAuta) {
				if(a.isNaCeste()) {
					a.provedAkci();
				}
			}
		}
	}
	
	/**
	 * Metoda pro zapsani statistika pouzivana ke konci simulace. Postupne si nacita z dat ulozenych v jednotlivych vozidlech udaje, ktere zpracuje do retezce.
	 * Tento retezec se pak posle metode ze tridy InputOutput na jeho zapsani jako jednoho radku.
	 */
	private void zapisStatistikyVozidel() {
		InputOutput.zapisStatistiku(InputOutput.STATISTIKA_KAMIONY, "ID vozidla;Cil cesty;Pocet zavezenych plnych sudu;Pocet odvezenych prazdnych sudu;\n");
		for(Kamion k : Simulace.pivovar.kamiony) {
			ArrayList<Integer> prekladiste = k.getPrekladiste();
			ArrayList<Integer> zavezenePlneSudy = k.getZavezenePlneSudy();
			ArrayList<Integer> odvezenePrazdneSudy = k.getOdvezenePrazdneSudy();
			
			for(int i = 0; i < prekladiste.size(); i++) {
				String ret = ""+k.getID()+";Prekladiste c. "+prekladiste.get(i)+";"+zavezenePlneSudy.get(i)+";"+odvezenePrazdneSudy.get(i)+";\n";
				InputOutput.zapisStatistiku(InputOutput.STATISTIKA_KAMIONY, ret);
			}
			InputOutput.zapisStatistiku(InputOutput.STATISTIKA_KAMIONY, "\n");
		}
		
		InputOutput.zapisStatistiku(InputOutput.STATISTIKA_CISTERNY, "ID vozidla;Cil cesty;Pocet zavezenych hektolitru piva;\n");
		for(Cisterna c : Simulace.pivovar.cisterny) {
			ArrayList<Integer> hospody = c.getHospody();
			ArrayList<Integer> zavezenePivo = c.getZavezenePivo();
			
			for(int i = 0; i < hospody.size(); i++) {
				String ret = ""+c.getID()+";Hospoda c. "+hospody.get(i)+";"+zavezenePivo.get(i)+";\n";
				InputOutput.zapisStatistiku(InputOutput.STATISTIKA_CISTERNY, ret);
			}
			InputOutput.zapisStatistiku(InputOutput.STATISTIKA_CISTERNY, "\n");
		}
		
		InputOutput.zapisStatistiku(InputOutput.STATISTIKA_NAKLADNI_AUTA, "ID vozidla a prislusneho prekladiste;Cil cesty;Pocet zavezenych plnych sudu;Pocet odvezenych prazdnych sudu;\n");
		for(Prekladiste p : Simulace.prekladiste) {
			for(NakladniAuto a : p.nakladniAuta) {
				ArrayList<Integer> hospody = a.getHospody();
				ArrayList<Integer> zavezenePlneSudy = a.getZavezenePlneSudy();
				ArrayList<Integer> odvezenePrazdneSudy = a.getOdvezenePrazdneSudy();
				
				for(int i = 0; i < hospody.size(); i++) {
					String ret = "Auto "+a.getID()+" z prekladiste c. "+a.getStartovniPrekladiste()+";Hospoda c. "+hospody.get(i)+";"+zavezenePlneSudy.get(i)+";"+odvezenePrazdneSudy.get(i)+";\n";
					InputOutput.zapisStatistiku(InputOutput.STATISTIKA_NAKLADNI_AUTA, ret);
				}
				InputOutput.zapisStatistiku(InputOutput.STATISTIKA_NAKLADNI_AUTA, "\n");
			}
		}
	}
	
	private void zapisStatistikyHospod() {
		InputOutput.zapisStatistiku(InputOutput.STATISTIKA_TANKOVE_HOSPODY, "ID hospody;Den 1;Den 2;Den 3;Den 4;Den 5;Den 6;Den 7;\n");
		for(HospodaTankova h : Simulace.tankoveHospody) {
			String[] dovezeneObjednavky = h.getDovezeneObjednavky();
			String[] vozidla = h.getVozidla();
			String ret = ""+h.getID()+";";
			
			for(int i = 0; i < dovezeneObjednavky.length; i++) {
				ret += dovezeneObjednavky[i]+" od "+vozidla[i]+";";
			}
			
			ret += "\n";
			
			InputOutput.zapisStatistiku(InputOutput.STATISTIKA_TANKOVE_HOSPODY, ret);
		}
		
		InputOutput.zapisStatistiku(InputOutput.STATISTIKA_SUDOVE_HOSPODY, "ID hospody;Den 1;Den 2;Den 3;Den 4;Den 5;Den 6;Den 7;\n");
		for(HospodaSudova h : Simulace.sudoveHospody) {
			String[] dovezeneObjednavky = h.getDovezeneObjednavky();
			String[] vozidla = h.getVozidla();
			String ret = ""+h.getID()+";";
			
			for(int i = 0; i < dovezeneObjednavky.length; i++) {
				ret += dovezeneObjednavky[i]+" od "+vozidla[i]+";";
			}
			
			ret += "\n";
			
			InputOutput.zapisStatistiku(InputOutput.STATISTIKA_SUDOVE_HOSPODY, ret);
		}
	}
}