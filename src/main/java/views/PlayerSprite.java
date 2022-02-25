package main.java.views;

import java.awt.Color;
import java.awt.Graphics2D;

import main.java.models.Model;
import main.java.models.Player;
/**
 * Draws the player sprite, consists of the reference to the player model from which it gets the current position.
 * @see main.java.models.Player
 * @author Naichiri
 *
 */
public class PlayerSprite implements Drawable {
	/**
	 * The width of the hitbox on the screen in pixels, depends on the actual model size.
	 * @see main.java.models.Player
	 */
	private static final int HITBOX_SPRITE_WIDTH = View.toPixels(Player.HITBOX_HEIGHT);
	/**
	 * The height of the hitbox on the screen in pixels, depends on the actual model size.
	 * @see main.java.models.Player
	 */
	private static final int HITBOX_SPRITE_HEIGHT = View.toPixels(Player.HITBOX_WIDTH);
	
	private Player player;
	
	/**
	 * PlayerSprite constructor.
	 * @param m used to connect player sprite with player model.
	 */
	public PlayerSprite(Model m) {
		player = m.player;
	}
	@Override
	public void draw(Graphics2D g2d) {
		
		int hitboxSpriteX = GameInterface.PLAYING_AREA_X
				+ View.toPixels(player.hitboxCentre.getX()) - HITBOX_SPRITE_WIDTH/2;
		int hitboxSpriteY = GameInterface.PLAYING_AREA_Y
				+ View.toPixels(player.hitboxCentre.getY()) - HITBOX_SPRITE_HEIGHT/2;
		
		g2d.setColor(Color.BLUE);
		g2d.fillRoundRect(hitboxSpriteX, hitboxSpriteY, HITBOX_SPRITE_WIDTH, HITBOX_SPRITE_HEIGHT,
				HITBOX_SPRITE_WIDTH, HITBOX_SPRITE_HEIGHT);
	}
	@Override
	public long getId() {
		return 0;
	}
}
