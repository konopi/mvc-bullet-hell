package main.java.models;

import main.java.controllers.Controller;

/**
 * A Player object.
 * 
 * A <code>Player</code> object contains the information about the player model and implements
 * <code>Entity</code> interface.
 * 
 * @author Naichiri
 *
 */
public class Player implements Entity {
	/**
	 * The life count the player starts with, not yet implemented.
	 */
	public static final int INITIAL_LIFECOUNT = 2;
	/**
	 * The bomb count the player starts with, not yet implemented.
	 */
	public static final int INITIAL_BOMBCOUNT = 3;
	
	/**
	 * The starting position of the player in game coordinates.
	 */
	public static final Coordinates STARTING_POSITION = new Coordinates(40.0, 90.0);
	
	/**
	 * The hitbox width in the game coordinates.
	 */
	public static final double HITBOX_WIDTH = 1.5;
	/**
	 * The hitbox height in the game coordinates.
	 */
	public static final double HITBOX_HEIGHT = 1.5;
	/**
	 * The default movement speed of the player.
	 */
	public static final double SPEED = 0.05;
	/**
	 * The multiplier which is applied to speed while shift is being pressed down.
	 */
	public static final double FOCUS_MULTIPLIER = 0.45;
	/**
	 * The current position of the player and their hitbox.
	 */
	public Coordinates hitboxCentre;
	/**
	 * The current horizontal speed, above zero moves right, below zero moves left.
	 */
	private double horizontalSpeed = 0.0;
	/**
	 * The current vertical speed, above zero moves down, below zero moves up.
	 */
	private double verticalSpeed = 0.0;
	
	private int lifeCount = INITIAL_LIFECOUNT;
	
	private boolean lostLife = false;
	
	public Player() {
		hitboxCentre = STARTING_POSITION;
	}
	
	/**
	 * 
	 * @param x sets the hitbox centre x from left border of the playing area
	 * @param y sets the hitbox centre y from upper border of the playing area
	 */
	public void setHitboxCentre(double x, double y) {
		hitboxCentre.set(x, y);
	}
	/**
	 * 
	 * @param hS set horizontal speed
	 */
	public void setHorizontalSpeed(double hS) {
		horizontalSpeed = hS;
	}
	/**
	 * 
	 * @param vS set vertical speed
	 */
	public void setVerticalSpeed(double vS) {
		verticalSpeed = vS;
	}
	@Override
	public void tick(long interval) {
		// if player moves in both directions at once, prevents player from moving at ~1.41 faster than the actual speed
		if(horizontalSpeed != 0.0 && verticalSpeed != 0.0) {
			horizontalSpeed /= Math.sqrt(2);
			verticalSpeed /= Math.sqrt(2);
		}
		// sets the new position for the player
		hitboxCentre.set(hitboxCentre.getX() + horizontalSpeed*interval, hitboxCentre.getY() + verticalSpeed*interval);
		if(lostLife) {
			hitboxCentre.set(STARTING_POSITION.getX(), STARTING_POSITION.getY());;
			lostLife = false;
		}
		// some border limit control
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
	
	public void resetPosition() {
		hitboxCentre.set(STARTING_POSITION.getX(), STARTING_POSITION.getY());
	}
	
	public boolean lostLastLife() {
		lostLife = true;
		if(lifeCount <= 0)
			return true;
		--lifeCount;
		return false;
	}
	
	public int getLifeCount() {
		return lifeCount;
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
		return 0;
	}
	@Override
	public boolean isOutOfBounds() {
		return false;
	}
	@Override
	public void shoot(Controller c) {
		c.view.addBulletSprite(c.model.addBulletEntity(this, 0.0, -0.06));
	}

	@Override
	public void collidedWith(Entity e) {
		
	}

	@Override
	public String whatAmI() {
		return "player";
	}
	@Override
	public boolean isScheduled() {
		return false;
	}
}
