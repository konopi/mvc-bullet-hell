package main.java.views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * Draws all the static elements in the game.
 * @author Naichiri
 *
 */
public class GameInterface implements Drawable {
	
	/**
	 * Determines left border of the playing area.
	 */
	public static final int PLAYING_AREA_X = 128;
	/**
	 * Determines upper border of the playing area.
	 */
	public static final int PLAYING_AREA_Y = 20;
	/**
	 * Determines width of the playing area.
	 */
	public static final int PLAYING_AREA_WIDTH = 640;
	/**
	 * Determines height of the playing area.
	 */
	public static final int PLAYING_AREA_HEIGHT = 760;

	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(new Color(139, 0, 0));
		g2d.drawRect(PLAYING_AREA_X-1, PLAYING_AREA_Y-1, PLAYING_AREA_WIDTH+1, PLAYING_AREA_HEIGHT+1);
		g2d.setColor(new Color(180, 180, 180));
		g2d.fillRect(PLAYING_AREA_X, PLAYING_AREA_Y, PLAYING_AREA_WIDTH, PLAYING_AREA_HEIGHT);
		
		g2d.setFont(new Font("Open Sans", Font.BOLD, 30));
		
		g2d.setColor(Color.WHITE);
		g2d.drawString("SCORE", 980, 50);
		
		g2d.setFont(new Font("Open Sans", Font.BOLD, 20));
		
		g2d.setColor(Color.RED);
		g2d.drawString("Lives", 1008, 200);
		
//		g2d.setColor(Color.GREEN);
//		g2d.drawString("Bombs", 1000, 270);
		
	}

	@Override
	public long getId() {
		return 0;
	}
	
}
