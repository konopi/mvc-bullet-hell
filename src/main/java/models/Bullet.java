package main.java.models;

import main.java.controllers.Controller;

public class Bullet implements Entity {
	
	/**
	 * The bullet hitbox width.
	 */
	public static final double HITBOX_WIDTH = 0.5;
	/**
	 * The bullet hitbox height.
	 */
	public static final double HITBOX_HEIGHT = 0.5;
	/**
	 * Current horizontal speed.
	 */
	private double horizontalSpeed = 0.0;
	/**
	 * Current vertical speed.
	 */
	private double verticalSpeed = 0.0;
	/**
	 * Determines if the bullet is out of bounds.
	 */
	private boolean outOfBounds = false;
	public boolean scheduledForDisappearance = false;
	
	private Entity source;
	
	public Coordinates hitboxCentre = new Coordinates();
	
	public long id;
	
	public Bullet(Entity source, double hS, double vS) {
		this.source = source;
		if(vS > 0)
			hitboxCentre.set(source.getX(), source.getY() + HITBOX_HEIGHT + 0.01);
		else
			hitboxCentre.set(source.getX(), source.getY() - HITBOX_HEIGHT - 0.01);
		horizontalSpeed = hS;
		verticalSpeed = vS;
		id = Enemy.idCounter++;
	}

	@Override
	public void tick(long interval) {
		if(horizontalSpeed != 0.0 && verticalSpeed != 0.0) {
			horizontalSpeed /= Math.sqrt(2);
			verticalSpeed /= Math.sqrt(2);
		}
		
		hitboxCentre.set(hitboxCentre.getX() + horizontalSpeed*interval, hitboxCentre.getY() + verticalSpeed*interval);
		
		if(hitboxCentre.getX() > 80 - HITBOX_WIDTH/2) {
			outOfBounds = true;
		}
		if(hitboxCentre.getX() < HITBOX_WIDTH/2) {
			outOfBounds = true;
		}
		if(hitboxCentre.getY() > 95 - HITBOX_HEIGHT/2) {
			outOfBounds = true;
		}
		if(hitboxCentre.getY() < HITBOX_HEIGHT/2) {
			outOfBounds = true;
		}
	}
	@Override
	public boolean collidesWith(Entity e) {
		if(e == source) {
			return false;
		}
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
		return 0;
	}
	@Override
	public boolean isOutOfBounds() {
		return outOfBounds;
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
		return "bullet";
	}

	@Override
	public boolean isScheduled() {
		return scheduledForDisappearance;
	}
}
