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

public class OknoObjednavky extends JFrame {

	private static final long serialVersionUID = 1L;

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
              
		SpinnerNumberModel numberModel1 = new SpinnerNumberModel(
                new Integer(0), // value
                new Integer(0), // min
                new Integer(4000), // max
                new Integer(1) // step
                );
		   
		SpinnerNumberModel numberModel2 = new SpinnerNumberModel(
                new Integer(1), // value
                new Integer(1), // min
                new Integer(6), // max
                new Integer(1) // step
                );
		   
		
		JSpinner hospoda = new JSpinner(numberModel1);
		JSpinner mnozstvi = new JSpinner(numberModel2);
				
		cont.add( new JLabel("Zadejte ID hospody: "));
		cont.add(hospoda);
		cont.add( new JLabel("			Zadejte množství: "));
		cont.add(mnozstvi);
		
		JButton objednej = new JButton("Objednej");
	
		objednej.addActionListener(new ActionListener() {

			// @Override
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});

		cont.add(objednej);
	
		setContentPane(cont);
	}

}