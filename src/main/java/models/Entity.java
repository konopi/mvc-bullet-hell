package main.java.models;

import main.java.controllers.Controller;

/**
 * An interface created to allow iterating through all of the updatable entities.
 * Probably some of this stuff should be swapped with an actual class because of similarities in implementation
 * between the different entities. Only the <code>tick()</code> method should stay.
 * @author Naichiri
 *
 */
public interface Entity {
	/**
	 * The method called for each entity that changes with time. Most classes implementing
	 * <code>Entity</code> interface use <code>interval</code> to move the objects accordingly.
	 * @param interval time passed since the last time the <code>tick()</code> was called
	 */
	public void tick(long interval);
	public boolean collidesWith(Entity e);
	public double getX();
	public double getY();
	public double getWidth();
	public double getHeight();
	public long getId();
	public boolean isOutOfBounds();
	public void shoot(Controller c);
	public void collidedWith(Entity e);
	public String whatAmI();
	public boolean isScheduled();
}
