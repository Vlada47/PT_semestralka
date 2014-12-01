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
import simulace.Simulace;

public class OknoNakladaku extends JFrame {

	private static final long serialVersionUID = 1L;

	public OknoNakladaku() {
		super("Nakladaky");

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
				new Integer(6), // max
				new Integer(1) // step
		);
		
		SpinnerNumberModel numberModel2 = new SpinnerNumberModel(new Integer(0), // value
				new Integer(0), // min
				new Integer(249), // max
				new Integer(1) // step
		);

		final JSpinner prekladiste = new JSpinner(numberModel1);
		final JSpinner nakladak = new JSpinner(numberModel2);
		
		cont.add(new JLabel("Zadejte ID pøeklaïištì: "));
		cont.add(prekladiste);
		
		cont.add(new JLabel("Zadejte ID náklaïáku: "));
		cont.add(nakladak);

		JButton vypis = new JButton("Vypis");

		vypis.addActionListener(new ActionListener() {

			// @Override
			public void actionPerformed(ActionEvent arg0) {

				int idNakladaku = (Integer) nakladak.getValue();	
				int idPrekladiste = (Integer) prekladiste.getValue();
				
				System.out.println(Simulace.prekladiste[idPrekladiste].nakladniAuta.get(idNakladaku).getVypis());			
				
				Simulace.timer.start();
				dispose();
			}
		});

		cont.add(vypis);

		setContentPane(cont);
	}
}