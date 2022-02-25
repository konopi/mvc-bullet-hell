package main.java.models;
/**
 * Simple class used to group two coordinates together.
 * @author Naichiri
 *
 */
public class Coordinates {
	
	private double x;
	private double y;
	
	/**
	 * Default constructor.
	 */
	public Coordinates() {
		this.x = 0.0;
		this.y = 0.0;
	}
	/**
	 * Sets x and y accordingly with parameters.
	 * @param x the width parameter
	 * @param y the height parameter
	 */
	public Coordinates(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	/**
	 * Used to set both values at once.
	 * @param x the width parameter
	 * @param y the height parameter
	 */
	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}
}
