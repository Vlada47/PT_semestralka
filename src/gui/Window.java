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
	private static boolean bezi = true;
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
		final JButton pozastav = new JButton("Pozastavit");
		JButton objednej = new JButton("Objednej");
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
				// Platno.kresli();

				Matice.vytvorNejkratsiCesty();
				System.out.println("Vytvorena matice nejkratsich cest...");

				InputOutput.zapisMaticeNejkratsichCest(Matice.maticeNejkratsichCest);
				System.out.println("Matice nejkratsich cest zapsana do souboru...");

			}
		});

		pozastav.addActionListener(new ActionListener() {

			// @Override
			public void actionPerformed(ActionEvent arg0) {
				if (bezi) {
					Simulace.timer.stop();
					bezi = false;
					pozastav.setText("PokraËovat");
				}

				else {
					Simulace.timer.start();
					bezi = true;
					pozastav.setText("Pozastavit");
				}

			}
		});

		objednej.addActionListener(new ActionListener() {

			// @Override
			public void actionPerformed(ActionEvent arg0) {
				
				new OknoObjednavky();

			}
		});
		
		stavBudov.addActionListener(new ActionListener() {

			// @Override
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText(null);
				for (HospodaSudova hospoda : Simulace.sudoveHospody) {
					System.out.println("Hospoda" + hospoda.getID() + " (sudova) ma " + hospoda.getPocetPlnychSudu()
							+ " plnych a " + hospoda.getPocetPrazdnychSudu() + " prazdnych sudu.");
				}

				for (HospodaTankova hospoda : Simulace.tankoveHospody) {
					System.out.println("Hospoda" + hospoda.getID() + " (tankova) ma " + hospoda.getStavPiva() + " hektolitru piva k dispozici.");
				}

				for (Prekladiste prekladiste : Simulace.prekladiste) {
					System.out.println("Prekladiste" + prekladiste.getID() + " ma " + prekladiste.getPocetPlnychSudu()
							+ " plnych a " + prekladiste.getPocetPrazdnychSudu() + " prazdnych sudu. "
							+ "Pocet aut na ceste je " + prekladiste.getPocetAutNaCeste() + " a pocet dostupnych aut "
							+ (prekladiste.nakladniAuta.size() - prekladiste.getPocetAutNaCeste()) + ".");
				}

				System.out.println("Pivovar ma " + Simulace.pivovar.getStavPiva() + " hektolitru piva k dispozici. "
						+ "Pocet kamionu na ceste je " + Simulace.pivovar.getPocetKamionuNaCeste()
						+ " a pocet dostupnych kamionu " + (Simulace.pivovar.kamiony.size() - Simulace.pivovar.getPocetKamionuNaCeste()) + ". "
						+ "Pocet cisteren na ceste je " + Simulace.pivovar.getPocetCisterenNaCeste()
						+ " a pocet dostupnych cisteren " + (Simulace.pivovar.kamiony.size() - Simulace.pivovar.getPocetCisterenNaCeste()) + ".");
			}
		});
		
		stavVozidel.addActionListener(new ActionListener() {
			// @Override
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText(null);

				for (Prekladiste p : Simulace.prekladiste) {
					for (NakladniAuto a : p.nakladniAuta) {
						if(a.isNaCeste()) {
							System.out.println("Nakladni auto "+a.getID()+" z prekladiste "+a.getStartovniPrekladiste()+" je na ceste a veze "+a.getPocetPlnychSudu()+" plnych sudu a "+a.getPocetPrazdnychSudu()+" prazdnych sudu.");
						}
						else {
							System.out.println("Nakladni auto "+a.getID()+" je k dispozici v prekladisti "+p.getID()+".");
						}
					}
				}

				Pivovar p = Simulace.pivovar;

				for (Kamion k : p.kamiony) {
					if(k.isNaCeste()) {
						System.out.println("Kamion "+k.getID()+" je na ceste a veze "+k.getPocetPlnychSudu()+" plnych sudu a "+k.getPocetPrazdnychSudu()+" prazdnych sudu.");
					}
					else {
						System.out.println("Kamion "+k.getID()+" je k dispozici v pivovaru.");
					}
				}
					
				for (Cisterna c : p.cisterny) {
					if(c.isNaCeste()) {
						System.out.println("Cisterna "+c.getID()+" je na ceste a veze "+c.getNaklad()+" hektolitru piva.");
					}
					else {
						System.out.println("Cisterna "+c.getID()+" je k dispozici v pivovaru.");
					}	
				}
			}
		});

		buttonPanel.add(aktualizace);
		buttonPanel.add(mapa);
		buttonPanel.add(zacni);
		buttonPanel.add(pozastav);
		buttonPanel.add(objednej);
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
