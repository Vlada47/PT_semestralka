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

/**
 * Trida vytvarejici okno pro vypis cisterny
 * @author Vlada47 & Shag0n
 */
public class OknoCisteren extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor tridy OknoCisterny
	 * Vytvori okno 100x180 se dvema Jspinnery a tlacitkem
	 */
	public OknoCisteren() {
		super("Cisterny");

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
				new Integer(199), // max
				new Integer(1) // step
		);

		final JSpinner cisterna = new JSpinner(numberModel1);
		
		cont.add(new JLabel("Zadejte ID cisterny: "));
		cont.add(cisterna);

		JButton vypis = new JButton("Vypis");

		vypis.addActionListener(new ActionListener() {

			// @Override
			public void actionPerformed(ActionEvent arg0) {

				int idCisterny = (Integer) cisterna.getValue();				
				
				System.out.println(Simulace.pivovar.cisterny.get(idCisterny).getVypis());			

				dispose();
			}
		});

		cont.add(vypis);

		setContentPane(cont);
	}
}