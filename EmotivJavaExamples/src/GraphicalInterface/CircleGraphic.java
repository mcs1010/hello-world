package GraphicalInterface;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Simple class to hold circle graphics data for the sensor quality display.
 * 
 * @author Stephen Bierly
 *
 */
public class CircleGraphic {
	private final int DIAMETER = 38;
	private int X;
	private int Y;
	private Color c;

	/**
	 * Constructor for a circle graphic
	 * 
	 * @param x The x location of the center of the circle
	 * @param y The y location of the center of the circle
	 * @param c The color that the circle is to be painted
	 */
	CircleGraphic(int x, int y, Color c) {
		X = x;
		Y = y;
		this.c = c;
	}

	/**
	 * Sets the Color of the circle graphic
	 * 
	 * @param c
	 *            The new circle color.
	 */
	public void setColor(Color c) {
		this.c = c;
	}

	/**
	 * @return Returns the current color of the circle graphic
	 */
	public Color getColor() {
		return c;
	}

	/**
	 * @return The x location of the center of the circle graphic
	 */
	public int getX() {
		return X;
	}

	/**
	 * @return The y location of the center of the circle graphic
	 */
	public int getY() {
		return Y;
	}

	/**
	 * @return The diameter of the circle graphic
	 */
	public int getDiameter() {
		return DIAMETER;
	}

}
