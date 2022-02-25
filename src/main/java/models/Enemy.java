package main.java.models;

import java.util.Random;

import main.java.controllers.Controller;

/**
 * The enemy class. Used for creating multiple objects which are going to fire at player.
 * @author Naichiri
 *
 */
public class Enemy implements Entity {
	/**
	 * The enemy hitbox width.
	 */
	public static final double HITBOX_WIDTH = 3.0;
	/**
	 * The enemy hitbox height.
	 */
	public static final double HITBOX_HEIGHT = 3.0;
	
	public static long idCounter = 1;
	
	/**
	 * Current speed.
	 */
	private double speed = 0.0;
	/**
	 * Last time the object changed directions.
	 */
	private long lastTime = 0;
	/**
	 * Horizontal multiplier for the speed, can take values: {-1, 0, 1}
	 */
	private int horizontal = 0;
	/**
	 * Vertical multiplier for the speed, can take values: {-1, 0, 1}
	 */
	private int vertical = 0;
	
	/**
	 * Used to hold hitbox current position.
	 */
	public Coordinates hitboxCentre = new Coordinates();
	public double fireRate;
	
	public boolean scheduledForDisappearance = false;
	/**
	 * Used to remove the right sprite.
	 */
	public long id;
	
	/**
	 * Default constructor.
	 * Randomises the starting point and movement of the object.
	 */
	public Enemy() {
		Random rnd = new Random();
		hitboxCentre.set(rnd.nextDouble()*78.5, rnd.nextDouble()*38.5);
		speed = 0.01 + rnd.nextDouble()*0.005;
		fireRate = 1.0;
		id = idCounter++;
	}
	/**
	 * @deprecated
	 * Not in use. Default constructor does the job well.
	 */
	public Enemy(double x, double y, double fR) {
		hitboxCentre.set(x, y);
		fireRate = fR;
	}

	@Override
	public void tick(long interval) {
		Random rnd = new Random();
		double t = speed;
		
		lastTime += interval;
		
		if(lastTime > (1000 + rnd.nextInt() % 1000)) {
		horizontal = (rnd.nextInt() % 3);
		vertical = (rnd.nextInt() % 3);
		lastTime = 0;
		}
		// too much speed if both directions at once fix
		if(horizontal != 0 && vertical != 0) {
			speed /= Math.sqrt(2);
		}
		
		hitboxCentre.set(hitboxCentre.getX() + horizontal*speed*interval, hitboxCentre.getY() + vertical*speed*interval);
		
		speed = t;
		
		// border limit control
		if(hitboxCentre.getX() > 80 - HITBOX_WIDTH/2) {
			hitboxCentre.setX(80 - HITBOX_WIDTH/2);
		}
		if(hitboxCentre.getX() < HITBOX_WIDTH/2) {
			hitboxCentre.setX(HITBOX_WIDTH/2);
		}
		if(hitboxCentre.getY() > 95 - HITBOX_HEIGHT/2) {
			hitboxCentre.setY(95 - HITBOX_HEIGHT/2);
		}
		if(hitboxCentre.getY() < HITBOX_HEIGHT/2) {
			hitboxCentre.setY(HITBOX_HEIGHT/2);
		}
	}
	@Override
	public boolean collidesWith(Entity e) {
		if(hitboxCentre.getX() < e.getX() + e.getWidth() && hitboxCentre.getX() > e.getX()) {
			if(hitboxCentre.getY() < e.getY() + e.getHeight() && hitboxCentre.getY() > e.getY()) {
				return true;
			}
		}
		return false;
	}
	@Override
	public double getX() {
		return hitboxCentre.getX() - HITBOX_WIDTH/2;
	}
	@Override
	public double getY() {
		return hitboxCentre.getY() - HITBOX_HEIGHT/2;
	}
	@Override
	public double getWidth() {
		return HITBOX_WIDTH;
	}
	@Override
	public double getHeight() {
		return HITBOX_HEIGHT;
	}
	@Override
	public long getId() {
		return id;
	}
	@Override
	public boolean isOutOfBounds() {
		return false;
	}
	@Override
	public void shoot(Controller c) {
		
	}
	@Override
	public void collidedWith(Entity e) {
		scheduledForDisappearance = true;
		
	}
	@Override
	public String whatAmI() {
		return "enemy";
	}
	@Override
	public boolean isScheduled() {
		return scheduledForDisappearance;
	}
}
