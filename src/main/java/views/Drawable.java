package main.java.views;

import java.awt.Graphics2D;
/**
 * Small interface created to allow iterating through all of the drawable components.
 * @author Naichiri
 *
 */
public interface Drawable {
	
	/**
	 * The method called in for each loop in <code>View</code>.
	 * @param g2d The <code>Graphics2D</code> defined and initialised in <code>View</code>.
	 * Every object is drawn into that.
	 */
	public void draw(Graphics2D g2d);
	public long getId();
}
