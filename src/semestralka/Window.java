package semestralka;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Window extends JFrame {

	static Window frame = new Window();
	private static final long serialVersionUID = 1L;
	
	public static void vytvorOkno() {
		frame.setTitle("Mapa");
		frame.setSize(1000, 1000);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.add(new Platno(), BorderLayout.CENTER);
		frame.setLocationRelativeTo(null); // vycentruje okno
		frame.setVisible(true);
	}
	
	
}
