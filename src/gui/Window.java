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

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new ScrollPane();
			}
		});

		zacni.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Simulace.start();
			}
		});

		aktualizace.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				Matice.vytvorNejkratsiCesty();
				System.out.println("Vytvorena matice nejkratsich cest...");

				InputOutput.zapisMaticeNejkratsichCest(Matice.maticeNejkratsichCest);
				System.out.println("Matice nejkratsich cest zapsana do souboru...");

			}
		});

		pozastav.addActionListener(new ActionListener() {

			@Override
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

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				new OknoObjednavky();

			}
		});
		
		stavBudov.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText(null);
				for (HospodaSudova h : Simulace.sudoveHospody) {
					System.out.println(h.getVypis());
				}

				for (HospodaTankova h : Simulace.tankoveHospody) {
					System.out.println(h.getVypis());
				}

				for (Prekladiste p : Simulace.prekladiste) {
					System.out.println(p.getVypis());
				}

				System.out.println(Simulace.pivovar.getVypis());
			}
		});
		
		stavVozidel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText(null);

				for (Prekladiste p : Simulace.prekladiste) {
					for (NakladniAuto a : p.nakladniAuta) {
						System.out.println(a.getVypis());
					}
				}

				Pivovar p = Simulace.pivovar;

				for (Kamion k : p.kamiony) {
					System.out.println(k.getVypis());
				}
					
				for (Cisterna c : p.cisterny) {
					System.out.println(c.getVypis());
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
