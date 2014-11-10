package gui;

import generovani_dat.Matice;
import io.CustomOutputStream;
import io.InputOutput;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.PrintStream;

import javax.swing.*;

import objekty_budovy.HospodaSudova;
import objekty_budovy.Prekladiste;
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
		JButton stav = new JButton("Stav hospod");

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
				Platno.kresli();

				Matice.vytvorNejkratsiCesty();
				System.out.println("Vytvorena matice nejkratsich cest...");

				InputOutput.zapisMaticeNejkratsichCest(Matice.maticeNejkratsichCest);
				System.out.println("Matice nejkratsich cest zapsana do souboru...");

			}
		});

		stav.addActionListener(new ActionListener() {

			// @Override
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText(null);
				for (HospodaSudova hospoda : Simulace.sudoveHospody) {
					if (hospoda.idPrekladiste != 8) {
						System.out.println("Hospoda" + hospoda.ID + " ma " + hospoda.getPocetPlnychSudu()
								+ " plnych a " + hospoda.getPocetPrazdnychSudu() + " prazdnych sudu");
					}
				}
				for (Prekladiste prekladiste : Simulace.prekladiste) {
					System.out.println("Prekladiste" + prekladiste.getID() + " ma " + prekladiste.getPocetPlnychSudu() + " plnych a "
							+ prekladiste.getPocetPrazdnychSudu() + " prazdnych sudu");
				}
			}
		});

		buttonPanel.add(aktualizace);
		buttonPanel.add(mapa);
		buttonPanel.add(zacni);
		buttonPanel.add(stav);
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
