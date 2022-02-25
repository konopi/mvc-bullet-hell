package main.java.views;

import java.awt.Color;
import java.awt.Graphics2D;

import main.java.models.Enemy;
/**
 * Draws the enemy sprite, consists of the reference to the enemy model from which it gets the current position.
 * @see main.java.models.Enemy
 * @author Naichiri
 *
 */
public class EnemySprite implements Drawable {
	/**
	 * The width of the hitbox on the screen in pixels, depends on the actual model size.
	 * @see main.java.models.Enemy
	 */
	private static final int HITBOX_SPRITE_WIDTH = View.toPixels(Enemy.HITBOX_HEIGHT);
	/**
	 * The height of the hitbox on the screen in pixels, depends on the actual model size.
	 * @see main.java.models.Enemy
	 */
	private static final int HITBOX_SPRITE_HEIGHT = View.toPixels(Enemy.HITBOX_WIDTH);
	
	private Enemy enemy;
	
	public long id;
	
	public EnemySprite(Enemy e) {
		enemy = e;
		id = enemy.id;
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		
		int hitboxSpriteX = GameInterface.PLAYING_AREA_X
				+ View.toPixels(enemy.hitboxCentre.getX()) - HITBOX_SPRITE_WIDTH/2;
		int hitboxSpriteY = GameInterface.PLAYING_AREA_Y
				+ View.toPixels(enemy.hitboxCentre.getY()) - HITBOX_SPRITE_HEIGHT/2;
		
		g2d.setColor(Color.RED);
		g2d.fillRoundRect(hitboxSpriteX, hitboxSpriteY, HITBOX_SPRITE_WIDTH, HITBOX_SPRITE_HEIGHT,
				HITBOX_SPRITE_WIDTH, HITBOX_SPRITE_HEIGHT);
	}

	@Override
	public long getId() {
		return id;
	}

}
