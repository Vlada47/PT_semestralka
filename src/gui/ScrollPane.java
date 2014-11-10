package gui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class ScrollPane extends JFrame {

	private static final long serialVersionUID = 1L;

	public ScrollPane() {
		super("Mapa");

		ImageIcon ii = new ImageIcon("image.png");
		JScrollPane jsp = new JScrollPane(new JLabel(ii));
		getContentPane().add(jsp);
		setSize(1000, 1000);
		setLocationRelativeTo(null);
		setVisible(true);
		
	}

}