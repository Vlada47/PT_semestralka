package gui;

import generovani_dat.Matice;
import io.CustomOutputStream;
import io.InputOutput;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.PrintStream;

import javax.swing.*;

import objekty_budovy.HospodaSudova;
import objekty_budovy.HospodaTankova;
import objekty_budovy.Pivovar;
import objekty_budovy.Prekladiste;
import objekty_vozidla.Cisterna;
import objekty_vozidla.Kamion;
import objekty_vozidla.NakladniAuto;
import simulace.Simulace;

public class Window extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JFrame frame;
	public static JTextArea textArea;
	
	public static void startOkno() {

		// okno
		frame = new JFrame("Simulator");
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initButtons();
		initOutputWindow();
		ImageIcon logo = new ImageIcon("pils-copy.jpg");
		frame.add(new JLabel(logo), BorderLayout.NORTH);
		frame.setSize(850, 700);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	public static void initButtons() {

		JPanel buttonPanel = new JPanel();

		// tlacitka a obsluhy udalosti
		JButton aktualizace = new JButton("Aktualizuj data");
		JButton mapa = new JButton("Vykresli rozmÌstÏnÌ");
		JButton zacni = new JButton("Spusù simulaci");
		JButton stavBudov = new JButton("Stav budov");
		JButton stavVozidel = new JButton("Stav vozidel");

		mapa.addActionListener(new ActionListener() {

			// @Override
			public void actionPerformed(ActionEvent arg0) {
				new ScrollPane();
			}
		});

		zacni.addActionListener(new ActionListener() {

			// @Override
			public void actionPerformed(ActionEvent arg0) {
				Simulace.start();
			}
		});

		aktualizace.addActionListener(new ActionListener() {

			// @Override
			public void actionPerformed(ActionEvent arg0) {
				//Platno.kresli();

				Matice.vytvorNejkratsiCesty();
				System.out.println("Vytvorena matice nejkratsich cest...");

				InputOutput.zapisMaticeNejkratsichCest(Matice.maticeNejkratsichCest);
				System.out.println("Matice nejkratsich cest zapsana do souboru...");

			}
		});

		stavBudov.addActionListener(new ActionListener() {

			// @Override
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText(null);
				for (HospodaSudova hospoda : Simulace.sudoveHospody) {
					System.out.println("Hospoda" + hospoda.ID + " (sudova) ma " + hospoda.getPocetPlnychSudu()
							+ " plnych a " + hospoda.getPocetPrazdnychSudu() + " prazdnych sudu.");
				}
				
				for (HospodaTankova hospoda : Simulace.tankoveHospody) {
					System.out.println("Hospoda" + hospoda.ID + " (tankova) ma " + hospoda.getStavPiva() + " hektolitru piva k dispozici.");
				}
				
				for (Prekladiste prekladiste : Simulace.prekladiste) {
					System.out.println("Prekladiste" + prekladiste.getID() + " ma " + prekladiste.getPocetPlnychSudu() + " plnych a "
							+ prekladiste.getPocetPrazdnychSudu() + " prazdnych sudu. "
									+ "Pocet aut na ceste je "+prekladiste.autaNaCeste.size()+" a pocet dostupnych aut "+prekladiste.dostupnaAuta.size()+".");
				}
				
				System.out.println("Pivovar ma " + Simulace.pivovar.getStavPiva() + " hektolitru piva k dispozici. "
						+ "Pocet kamionu na ceste je "+Simulace.pivovar.kamionyNaCeste.size()+" a pocet dostupnych kamionu "+Simulace.pivovar.dostupneKamiony.size()+". "
								+ "Pocet cisteren na ceste je "+Simulace.pivovar.cisternyNaCeste.size()+" a pocet dostupnych cisteren "+Simulace.pivovar.dostupneCisterny.size()+".");
			}
		});
		
		stavVozidel.addActionListener(new ActionListener() {
			// @Override
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText(null);
					
				for (Prekladiste p : Simulace.prekladiste) {
					for(NakladniAuto a : p.autaNaCeste) {
						
						String kamJede = "";
						if(a.getVzdalenostTam() <= 0) kamJede = "zpet do prekladiste";
						else kamJede = "do hospody "+a.getCilovaHospoda();
			
						System.out.println("Nakladni auto "+a.getID()+" z prekladiste "+p.getID()+" je momentalne na ceste "+kamJede+" a ma nalozeno "
								+a.getPocetPlnychSudu()+" plnych sudu a "+a.getPocetPrazdnychSudu()+" prazdnych sudu.");
					}
					for(NakladniAuto a : p.dostupnaAuta) {
						System.out.println("Nakladni auto "+a.getID()+" z prekladiste "+p.getID()+" je momentalne v prekladisti a ma nalozeno "
								+a.getPocetPlnychSudu()+" plnych sudu a "+a.getPocetPrazdnychSudu()+" prazdnych sudu.");
					}
				}
				
				Pivovar p = Simulace.pivovar;
				
				for(Kamion k : p.kamionyNaCeste) {
					String kamJede = "";
					if(k.getVzdalenostTam() <= 0) kamJede = "zpet do pivovaru";
					else kamJede = "do prekladiste "+k.getCilovePrekladiste();
					
					System.out.println("Kamion "+k.getID()+" je momentalne na ceste "+kamJede+" a ma nalozeno "
							+k.getPocetPlnychSudu()+" plnych sudu a "+k.getPocetPrazdnychSudu()+" prazdnych sudu.");
				}
				for(Kamion k : p.dostupneKamiony) {
					System.out.println("Kamion "+k.getID()+" je momentalne v pivovaru a ma nalozeno "
							+k.getPocetPlnychSudu()+" plnych sudu a "+k.getPocetPrazdnychSudu()+" prazdnych sudu.");
				}
				for(Cisterna c : p.cisternyNaCeste) {
					String kamJede = "";
					if(c.getVzdalenostTam() <= 0) kamJede = "zpet do pivovaru";
					else kamJede = "do hospody "+c.getCilovaHospoda();
					
					System.out.println("Cisterna "+c.getID()+" je momentalne na ceste "+kamJede+" a ma naklad "+c.getNaklad()+" hektolitru piva.");
				}
				for(Cisterna c : p.dostupneCisterny) {
					System.out.println("Cisterna "+c.getID()+" je momentalne v pivovaru a ma naklad "+c.getNaklad()+" hektolitru piva.");
				}
			}
		});

		buttonPanel.add(aktualizace);
		buttonPanel.add(mapa);
		buttonPanel.add(zacni);
		buttonPanel.add(stavBudov);
		buttonPanel.add(stavVozidel);
		frame.add(buttonPanel, BorderLayout.PAGE_END);
	}

	public static void initOutputWindow() {
			
		textArea = new JTextArea(15, 40);
		textArea.setEditable(false);
		PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));

		// re-assigns standard output stream and error output stream
		System.setOut(printStream);
		System.setErr(printStream);

		frame.add(new JScrollPane(textArea), BorderLayout.CENTER);
	}

}
