package semestralka;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.TextLayout;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Platno extends JPanel {
	public static final long serialVersionUID = 1L;
	public static final Platno panel = new Platno();
	static BufferedImage platno;

	public static Platno getInstance() {
		return panel;
	}

	public static void kresli() {
		platno = new BufferedImage(5000, 5000, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2 = platno.createGraphics();

		g2.setColor(Color.RED);
		g2.fillOval(2500, 2500, 20, 20);

		g2.setColor(Color.BLUE);
		for (Pozice pozice : Generator.souradnicePrekladist) {
			g2.fillOval(pozice.x * 10, pozice.y * 10, 15, 15);
		}

		g2.setStroke(new BasicStroke((float) 1));
		g2.setColor(Color.GREEN);

		Prekladiste[] polePrekladist = InputOutput.nactiPrekladiste();
		Hospoda[] poleHospod = InputOutput.nactiHospody();

		for (int i = 0; i < Generator.cestyPrekladist.length; i++) {
			for (int j = 0; j < Generator.cestyPrekladist[i].length; j++) {

				g2.draw(new Line2D.Double(polePrekladist[Generator.cestyPrekladist[i][j].getIdFrom()].getPosition().x * 10,
						polePrekladist[Generator.cestyPrekladist[i][j].getIdFrom()].getPosition().y * 10,
						poleHospod[Generator.cestyPrekladist[i][j].getIdTo()].pozice.x * 10,
						poleHospod[Generator.cestyPrekladist[i][j].getIdTo()].pozice.y * 10));
			}
		}

		for (int i = 0; i < Generator.cestyHospod.length; i++) {
			for (int j = 0; j < Generator.cestyHospod[i].length; j++) {

				g2.draw(new Line2D.Double(poleHospod[Generator.cestyHospod[i][j].getIdFrom()].pozice.x * 10,
						poleHospod[Generator.cestyHospod[i][j].getIdFrom()].pozice.y * 10,
						poleHospod[Generator.cestyHospod[i][j].getIdTo()].pozice.x * 10,
						poleHospod[Generator.cestyHospod[i][j].getIdTo()].pozice.y * 10));
			}
		}

		g2.setColor(Color.BLACK);
		int pocet = 0;
		for (Pozice pozice : Generator.souradniceHospod) {

			g2.fillOval(pozice.x * 10, pozice.y * 10, 4, 4);
			napis(pozice.x * 10, pozice.y * 10, "" + pocet, g2);
			pocet++;
		}

		g2.drawImage(platno, 0, 0, null);
		File outputfile = new File("image.png");

		try {
			ImageIO.write(platno, "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void napis(int x, int y, String str, Graphics2D g2) {

		Font fnt = new Font("Arial", Font.ITALIC, 15);
		g2.setFont(fnt);
		TextLayout layout = new TextLayout(str, g2.getFont(), g2.getFontRenderContext());
		layout.draw(g2, (float) x, (float) y);

	}

}
