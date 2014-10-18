package semestralka;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Platno extends JPanel {
	public static final long serialVersionUID = 1L;
	public static final Platno panel = new Platno();
	static BufferedImage platno;

	public static Platno getInstance() {
		return panel;
	}

	public void paint(Graphics g) {
		super.paint(g);

		platno = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2 = platno.createGraphics();

		g2.setColor(Color.BLACK);
		for (Pozice pozice : Generator.souradniceHospod) {

			g2.fillOval(pozice.x * 2, pozice.y * 2, 4, 4);
		}

		g2.setColor(Color.RED);

		g2.fillOval(500, 500, 20, 20);

		g2.setColor(Color.BLUE);
		for (Pozice pozice : Generator.souradnicePrekladist) {

			g2.fillOval(pozice.x * 2, pozice.y * 2, 15, 15);
		}

		g2.setStroke(new BasicStroke((float) 1));
		g2.setColor(Color.GREEN);

		Prekladiste[] polePrekladist = new Prekladiste[8];
		for (int i = 0; i < polePrekladist.length; i++) {
			polePrekladist[i] = new Prekladiste(Generator.souradnicePrekladist[i]);
		}

		Hospoda[] poleHospod = new Hospoda[4000];
		for (int i = 0; i < poleHospod.length; i++) {
			poleHospod[i] = new Hospoda(Generator.souradniceHospod[i]);
		}

		for (int i = 0; i < Generator.cestyPrekladist.length; i++) {
			for (int j = 0; j < Generator.cestyPrekladist[i].length; j++) {

				g2.draw(new Line2D.Double(polePrekladist[Generator.cestyPrekladist[i][j].getIdFrom()].pozice.x * 2,
						polePrekladist[Generator.cestyPrekladist[i][j].getIdFrom()].pozice.y * 2,
						poleHospod[Generator.cestyPrekladist[i][j].getIdTo()].pozice.x * 2,
						poleHospod[Generator.cestyPrekladist[i][j].getIdTo()].pozice.y * 2));
			}
		}

//		for (int i = 0; i < Generator.cestyHospod.length; i++) {
//			for (int j = 0; j < Generator.cestyHospod[i].length; j++) {
//
//				g2.draw(new Line2D.Double(poleHospod[Generator.cestyHospod[i][j].getIdFrom()].pozice.x * 2,
//						poleHospod[Generator.cestyHospod[i][j].getIdFrom()].pozice.y * 2,
//						poleHospod[Generator.cestyHospod[i][j].getIdTo()].pozice.x * 2,
//						poleHospod[Generator.cestyHospod[i][j].getIdTo()].pozice.y * 2));
//			}
//		}

		g.drawImage(platno, 0, 0, null);
	}

}
