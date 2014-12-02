package gui;

import generovani_dat.Matice;
import io.CustomOutputStream;
import io.InputOutput;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.PrintStream;
import javax.swing.*;
import simulace.Simulace;

/**
 * Trida vytvarejici okno applikace
 * @author Vlada47 & Shag0n
 */
public class Window extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JFrame frame;
	/** Indikuje jestli simulace bezi */
	private static boolean bezi = true;
	/** Indikuje zda byla simulace spustena */
	private static boolean spusteno = false;
	/** Vystupni JTextArea */
	public static JTextArea textArea;
	/** Tlacitko pro zacatek simulace */
	public static JButton zacni;
	/** Tlacitko pro pregenerovani matice sousednosti */
	public static JButton aktualizace;

	/**
	 * Metoda, ktera vytvori okno
	 */
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

	/**
	 * Metoda pro inicializaci tlacitek
	 */
	public static void initButtons() {

		JPanel buttonPanel = new JPanel();

		// tlacitka a obsluhy udalosti
		aktualizace = new JButton("Generuj matici");
		zacni = new JButton("Spusù simulaci");
		final JButton objednej = new JButton("Objednej");
		final JButton stavBudov = new JButton("Stav hospod");
		final JButton stavCisteren = new JButton("Stav cisteren");
		final JButton stavNakladaku = new JButton("Stav n·kladnÌch aut");

		zacni.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!spusteno) {
					Simulace.start();
					objednej.setEnabled(true);
					stavBudov.setEnabled(true);
					stavCisteren.setEnabled(true);
					stavNakladaku.setEnabled(true);
					aktualizace.setEnabled(false);
					spusteno=true;
					zacni.setText("Pozastavit");
				}
				else {
					if (bezi) {
						Simulace.timer.stop();
						bezi = false;
						zacni.setText("PokraËovat");
					}
					else {
						Simulace.timer.start();
						bezi = true;
						zacni.setText("Pozastavit");
					}
				}
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

		objednej.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new OknoObjednavky();
			}
		});
		
		stavBudov.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {	
				new OknoHospody();
			}
		});

		stavCisteren.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new OknoCisteren();
			}
		});
		
		stavNakladaku.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new OknoNakladaku();
			}
		});

		buttonPanel.add(aktualizace);
		buttonPanel.add(zacni);
		buttonPanel.add(objednej);
		buttonPanel.add(stavBudov);
		buttonPanel.add(stavCisteren);
		buttonPanel.add(stavNakladaku);
		objednej.setEnabled(false);
		stavBudov.setEnabled(false);
		stavCisteren.setEnabled(false);
		stavNakladaku.setEnabled(false);
		aktualizace.setEnabled(false);
		frame.add(buttonPanel, BorderLayout.PAGE_END);
	}

	
	/**
	 * Metoda pro inicializaci vystupniho textArea
	 */
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
