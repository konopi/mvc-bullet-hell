package main.java.models;

import java.util.ArrayList;

/**
 * Consists of important game classes, controller modifies it.
 * @author Naichiri
 *
 */
public class Model {
	
	/**
	 * New player object.
	 */
	public Player player = new Player();
	/**
	 * The game entities array list. Doesn't include player since player is handled separately.
	 * Used to update all the entities using <code>tick()</code>.
	 */
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	
	/**
	 * Adds new enemy entity to the entities array list.
	 * @return reference to added enemy to connect it with its sprite.
	 */
	public Enemy addEnemyEntity() {
		Enemy e = new Enemy();
		entities.add(e);
		return e;
	}
	public Bullet addBulletEntity(Entity source, double hS, double vS) {
		Bullet b = new Bullet(source, hS, vS);
		entities.add(b);
		return b;
	}
	
	/**
	 * The main method of the model. Updates all the game model objects according to time passed.
	 * @param interval time value received from <code>Controller</code> after calculating the FPS and the time passed.
	 */
	public void tick(long interval) {
		player.tick(interval);
		
		for(Entity entity : entities) {
			entity.tick(interval);
		}
	}
}
