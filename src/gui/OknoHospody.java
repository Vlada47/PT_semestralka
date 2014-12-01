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

public class OknoHospody extends JFrame {

	private static final long serialVersionUID = 1L;

	public OknoHospody() {
		super("Hospoda");

		setSize(100, 180);
		setLocationRelativeTo(null);
		setVisible(true);

		Container cont = new Container();
		cont = getContentPane();
		FlowLayout layout = new FlowLayout();
		cont.setLayout(layout);
		Simulace.timer.stop();
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

				Simulace.timer.start();
				dispose();
			}
		});

		cont.add(vypis);

		setContentPane(cont);
	}

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