package GraphicalInterface;
import java.awt.Canvas;
import java.awt.Color;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that extends Canvas to display the circle graphics to show signal quality
 * of the headset sensors.
 * @author Stephen Bierly
 *
 */

@SuppressWarnings("serial")
public class ContactQuality extends Canvas {

	private static Color Black = Color.BLACK;
	
	private static CircleGraphic CMS;
	private static CircleGraphic DRL;
	private static CircleGraphic AF3;
	private static CircleGraphic F7;
	private static CircleGraphic F3;
	private static CircleGraphic FC5;
	private static CircleGraphic T7;
	private static CircleGraphic P7;
	private static CircleGraphic O1;
	private static CircleGraphic O2;
	private static CircleGraphic P8;
	private static CircleGraphic T8;
	private static CircleGraphic FC6;
	private static CircleGraphic F4;
	private static CircleGraphic F8;
	private  CircleGraphic AF4;
	private static  List<CircleGraphic> sensors;

	/**
	 * ContactQuality Constructor sets up all the circles related to the display of
	 * sensor contact quality
	 * @wbp.parser.entryPoint
	 */
	public ContactQuality(){
		setBackground(Color.WHITE);
		setLocation(new Point(50, 0));
		setBounds(new Rectangle(20, 143, 532, 467));
		sensors = new ArrayList<CircleGraphic>();
		CMS = new CircleGraphic(5, 245 ,Black );
		DRL = new CircleGraphic(325, 245,Black);
		AF3 = new CircleGraphic(55,50,Black);
		F3= new CircleGraphic(90, 90,Black);
		F7 = new CircleGraphic(15, 110,Black);
		FC5 =new CircleGraphic(40, 150,Black);		
		T7 = new CircleGraphic(0, 195,Black);
		P7 = new CircleGraphic(30, 315,Black );
		O1 = new CircleGraphic(90, 380,Black);
		O2 = new CircleGraphic( 240, 380,Black);
		P8 = new CircleGraphic(300, 315,Black);
		T8 = new CircleGraphic(330, 195,Black);
		FC6 =new CircleGraphic(280, 150,Black);
		F4= new CircleGraphic(240, 90,Black );
		F8 = new CircleGraphic(315, 110,Black );
		AF4 = new CircleGraphic(285,50,Black);
		sensors.add(CMS);
		sensors.add(DRL);
		sensors.add(AF3);
		sensors.add(F7);
		sensors.add(F3);
		sensors.add(FC5);
		sensors.add(T7);
		sensors.add(P7);
		sensors.add(O1);
		sensors.add(O2);
		sensors.add(P8);
		sensors.add(T8);
		sensors.add(FC6);
		sensors.add(F4);
		sensors.add(F8);
		sensors.add(AF4);
	}
	/**
	 * repaints the graphic circles when called
	 */
	public void paint(Graphics g){
		
		for(int i = 0; i<sensors.size();i++){
			g.setColor(sensors.get(i).getColor());
			g.drawOval(sensors.get(i).getX(), sensors.get(i).getY(), sensors.get(i).getDiameter(),sensors.get(i).getDiameter());
			g.fillOval(sensors.get(i).getX(), sensors.get(i).getY(), sensors.get(i).getDiameter(), sensors.get(i).getDiameter());
			
		}
	}
	/**
	 * update the contact quality of a sensor
	 * @param index The sensor index
	 * @param c The new color of the sensor quality
	 */
	public static  void changeContactQuality(int index, Color c){
		sensors.get(index).setColor(c);
	}
}
