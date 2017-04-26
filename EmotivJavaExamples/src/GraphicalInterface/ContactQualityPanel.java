package GraphicalInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ContactQualityPanel extends JPanel {
	private BufferedImage test;
	private Image img;

	private static Color Black = Color.BLACK;
	private static CircleGraphic CMS, DRL, AF3, F7, F3, FC5, T7, P7, O1, O2,
			P8, T8, FC6, F4, F8, AF4;
	/*
	 * private static CircleGraphic DRL; private static CircleGraphic AF3;
	 * private static CircleGraphic F7; private static CircleGraphic F3; private
	 * static CircleGraphic FC5; private static CircleGraphic T7; private static
	 * CircleGraphic P7; private static CircleGraphic O1; private static
	 * CircleGraphic O2; private static CircleGraphic P8; private static
	 * CircleGraphic T8; private static CircleGraphic FC6; private static
	 * CircleGraphic F4; private static CircleGraphic F8; private CircleGraphic
	 * AF4;
	 */
	private static List<CircleGraphic> sensors;

	public ContactQualityPanel() {
		URL url = this.getClass().getResource("/Resources/images/SkullBackground.png");
		try {
			test = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Dimension size = new Dimension(500, 500);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);

		sensors = new ArrayList<CircleGraphic>();
		/* Left side of neuroheadset */
		AF3 = new CircleGraphic(149, 49, Black); sensors.add(AF3);
		F7 = new CircleGraphic(88, 112, Black); sensors.add(F7);
		F3 = new CircleGraphic(192, 95, Black); sensors.add(F3);
		FC5 = new CircleGraphic(135, 151, Black); sensors.add(FC5);
		T7 = new CircleGraphic(35, 228, Black); sensors.add(T7);
		CMS = new CircleGraphic(66, 300, Black); sensors.add(CMS);
		P7 = new CircleGraphic(110, 380, Black); sensors.add(P7);
		O1 = new CircleGraphic(180, 445, Black); sensors.add(O1);
		/* Right side of neuroheadset */
		AF4 = new CircleGraphic(315, 50, Black); sensors.add(AF4);
		F8 = new CircleGraphic(375, 110, Black); sensors.add(F8);
		FC6 = new CircleGraphic(330, 150, Black); sensors.add(FC6);
		T8 = new CircleGraphic(270, 100, Black); sensors.add(T8);
		F4 = new CircleGraphic(425, 225, Black); sensors.add(F4);
		DRL = new CircleGraphic(400, 300, Black); sensors.add(DRL);
		P8 = new CircleGraphic(352, 380, Black); sensors.add(P8);
		O2 = new CircleGraphic(280, 445, Black); sensors.add(O2);
	}

	public ContactQualityPanel(String img) {
		this(new ImageIcon(img).getImage());
	}

	public ContactQualityPanel(Image img) {
		this.img = img;
		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		setLayout(null);
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(test, 0, 0, getWidth(), getHeight(), this);
		for (int i = 0; i < sensors.size(); i++) {
			g2.setColor(sensors.get(i).getColor());
			g2.drawOval(sensors.get(i).getX(), sensors.get(i).getY(), sensors
					.get(i).getDiameter(), sensors.get(i).getDiameter());
			g2.fillOval(sensors.get(i).getX(), sensors.get(i).getY(), sensors
					.get(i).getDiameter(), sensors.get(i).getDiameter());

		}
	}

	public static void updateSensorContactQuality(int index, Color c) {
		sensors.get(index).setColor(c);
	}
}
