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
import semestralka.StaticData;
import simulace.Simulace;

/**
 * Trida vytvarejici okno pro vypis cisterny
 * @author Vlada47 & Shag0n
 */
public class OknoHospody extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor tridy OknoHospody
	 * Vytvori okno 100x180 sJspinnerem a tlacitkem
	 */
	public OknoHospody() {
		super("Hospoda");

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

		final JSpinner hospoda = new JSpinner(numberModel1);
		
		cont.add(new JLabel("Zadejte ID hospody: "));
		cont.add(hospoda);

		JButton vypis = new JButton("Vypis");

		vypis.addActionListener(new ActionListener() {

			// @Override
			public void actionPerformed(ActionEvent arg0) {

				int idHospody = (Integer) hospoda.getValue();
				
				if (idHospody % 20 == 0) {
				System.out.println(Simulace.tankoveHospody[(idHospody/20)].getVypis());			
				}

				else {

					int praveId = spocitejIndexHospody(idHospody);
					System.out.println(Simulace.sudoveHospody[(praveId)].getVypis());	
				}

				dispose();
			}
		});

		cont.add(vypis);

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