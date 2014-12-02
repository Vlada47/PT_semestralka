package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import objekty_ostatni.Objednavka;
import semestralka.StaticData;
import simulace.Simulace;

/**
 * Trida vytvarejici okno pro vytvoreni objednavky
 * @author Vlada47 & Shag0n
 */
public class OknoObjednavky extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor tridy OknoObjednavky
	 * Vytvori okno 100x180 se dvema Jspinnery a tlacitkem
	 */
	public OknoObjednavky() {
		super("Objednani");

		setSize(100, 180);
		setLocationRelativeTo(null);
		setVisible(true);

		Container cont = new Container();
		cont = getContentPane();
		FlowLayout layout = new FlowLayout();
		cont.setLayout(layout);
		cont.setBackground(Color.white);

		SpinnerNumberModel numberModel1 = new SpinnerNumberModel(new Integer(0), // value
				new Integer(0), // min
				new Integer(4000), // max
				new Integer(1) // step
		);

		SpinnerNumberModel numberModel2 = new SpinnerNumberModel(new Integer(1), // value
				new Integer(1), // min
				new Integer(6), // max
				new Integer(1) // step
		);

		final JSpinner hospoda = new JSpinner(numberModel1);
		final JSpinner mnozstvi = new JSpinner(numberModel2);

		cont.add(new JLabel("Zadejte ID hospody: "));
		cont.add(hospoda);
		cont.add(new JLabel("			Zadejte množství: "));
		cont.add(mnozstvi);

		JButton objednej = new JButton("Objednej");

		objednej.addActionListener(new ActionListener() {

			// @Override
			public void actionPerformed(ActionEvent arg0) {

				int idHospody = (Integer) hospoda.getValue();
				int objednaneMnozstvi = (Integer) mnozstvi.getValue();
				int denObjednavky = Simulace.den;
				int hodinaObjednavky = Simulace.hodina + 1;
				
				if(hodinaObjednavky > StaticData.MAX_HODINA_OBJEDNANI) {
					denObjednavky++;
					hodinaObjednavky = StaticData.MIN_HODINA_OBJEDNANI;
				}
				else if(hodinaObjednavky < StaticData.MIN_HODINA_OBJEDNANI) {
					hodinaObjednavky = StaticData.MIN_HODINA_OBJEDNANI;					
				}

				if (idHospody % 20 == 0) {
					Simulace.pivovar.objednavkyHospod.add(new Objednavka(denObjednavky, hodinaObjednavky, idHospody,
							0, objednaneMnozstvi));
					System.out.println("Pridana objednavka pro tankovou hospodu "+idHospody+" - "+objednaneMnozstvi+" hl. Den objednani - "+denObjednavky+", hodina objednani - "+hodinaObjednavky+".");
				}

				else {

					int praveId = spocitejIndexHospody(idHospody);
					int idPrekladiste = Simulace.sudoveHospody[praveId].getIdPrekladiste();
					Simulace.prekladiste[idPrekladiste].objednavky.add(new Objednavka(denObjednavky,
							hodinaObjednavky, idHospody, idPrekladiste, objednaneMnozstvi));
					System.out.println("Pridana objednavka pro sudovou hospodu "+idHospody+" - "+objednaneMnozstvi+" sudu. Den objednani - "+denObjednavky+", hodina objednani - "+hodinaObjednavky+".");
				}

				dispose();
			}
		});

		cont.add(objednej);

		setContentPane(cont);
	}

	/**
	 * Vypocita id hospody v poli z praveho id hospody
	 * @param id prave id hospody
	 * @return id hospody v poli
	 */
	private int spocitejIndexHospody(int id) {
		int idParam=id;
		int index = id - 1;

		while (idParam > StaticData.POMER_HOSPOD) {
			index--;
			idParam -= StaticData.POMER_HOSPOD;
		}

		return index;
	}
}