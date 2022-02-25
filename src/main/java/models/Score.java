package main.java.models;

import main.java.controllers.Controller;

public class Score implements Entity {
	
	/**
	 * The scale used to calculate score from time.
	 */
	private static final int SCORE_SCALE = 1;
	
	long scoreValue = 0;
	
	@Override
	public void tick(long interval) {
		scoreValue += interval*SCORE_SCALE;
	}
	// overrides because implementation mistakes
	@Override
	public boolean collidesWith(Entity e) {
		return false;
	}

	@Override
	public double getX() {
		return 0.0;
	}

	@Override
	public double getY() {
		return 0.0;
	}

	@Override
	public double getWidth() {
		return 0.0;
	}

	@Override
	public double getHeight() {
		return 0.0;
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
		
	}
	@Override
	public void collidedWith(Entity e) {
		
	}
	@Override
	public String whatAmI() {
		return "useless";
	}
	@Override
	public boolean isScheduled() {
		return false;
	}
}
