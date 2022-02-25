package main.java.views;

import java.awt.Color;
import java.awt.Graphics2D;

import main.java.models.Bullet;

public class BulletSprite implements Drawable {
	/**
	 * The width of the hitbox on the screen in pixels, depends on the actual model size.
	 * @see main.java.models.Bullet
	 */
	private static final int HITBOX_SPRITE_WIDTH = View.toPixels(Bullet.HITBOX_HEIGHT);
	/**
	 * The height of the hitbox on the screen in pixels, depends on the actual model size.
	 * @see main.java.models.Bullet
	 */
	private static final int HITBOX_SPRITE_HEIGHT = View.toPixels(Bullet.HITBOX_WIDTH);
	
	private Bullet bullet;
	
	public long id;
	
	public BulletSprite(Bullet b) {
		bullet = b;
		id = bullet.id;
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		int hitboxSpriteX = GameInterface.PLAYING_AREA_X
				+ View.toPixels(bullet.hitboxCentre.getX()) - HITBOX_SPRITE_WIDTH/2;
		int hitboxSpriteY = GameInterface.PLAYING_AREA_Y
				+ View.toPixels(bullet.hitboxCentre.getY()) - HITBOX_SPRITE_HEIGHT/2;
		
		g2d.setColor(Color.BLACK);
		g2d.fillRoundRect(hitboxSpriteX, hitboxSpriteY, HITBOX_SPRITE_WIDTH, HITBOX_SPRITE_HEIGHT,
				HITBOX_SPRITE_WIDTH, HITBOX_SPRITE_HEIGHT);
	}

	@Override
	public long getId() {
		return id;
	}

}
