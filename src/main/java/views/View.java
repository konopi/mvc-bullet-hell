package main.java.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;

import main.java.controllers.Controller;
import main.java.models.Bullet;
import main.java.models.Enemy;
import main.java.models.Model;

/**
 * Primarily used for defining and constructing a <code>JFrame</code> which then later is used
 * for launching the application in full-screen exclusive mode. Contains draw, conversion
 * and other methods.
 * @author Naichiri
 *
 */
public class View {
	
	/**
	 * Number of buffers used.
	 */
	private static final int NUMBER_OF_BUFFERS = 2;
	/**
	 * The back buffer background colour.
	 */
	private static final Color BACKGROUND_COLOR = Color.BLACK;
	/**
	 * The window width.
	 */
	private static final int WINDOW_WIDTH = 1280;
	/**
	 * The window height.
	 */
	private static final int WINDOW_HEIGHT = 800;
	/**
	 * The scale used to convert game size values to pixels.
	 */
	private static final double SCALE = 8;
	/**
	 * The window title.
	 */
	private static final String WINDOW_TITLE = "thdProject";
	
	public JFrame window = new JFrame(WINDOW_TITLE);
	
	Controller controller;
	Model model;
	
	private Dimension dimension = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
	private GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
	private GraphicsDevice device = environment.getDefaultScreenDevice();
	private GraphicsConfiguration config = device.getDefaultConfiguration();
	
	private BufferStrategy buffer;
	private BufferedImage bufferedImage = config.createCompatibleImage(WINDOW_WIDTH, WINDOW_HEIGHT);
	
	private Graphics graphics = null;
	private Graphics2D g2d = null;
	
	private ArrayList<Drawable> objects = new ArrayList<Drawable>();

	private PlayerSprite playerSprite;
	private GameInterface gameInterface;
	
	/**
	 * The <code>View</code> constructor. Takes a reference to model so it can receive information
	 * straight from it about how to draw its objects.
	 * @param m reference to the model
	 */
	public View(Model m) {
		model = m;
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(dimension);
		window.setUndecorated(true);
		window.setResizable(false);
		window.setIgnoreRepaint(true);

		device.setFullScreenWindow(window);
		device.setDisplayMode(device.getDisplayMode());

		window.createBufferStrategy(NUMBER_OF_BUFFERS);
		buffer = window.getBufferStrategy();
		
		gameInterface = new GameInterface();
		playerSprite = new PlayerSprite(model);
	}
	
	/**
	 * Sets controller after it's been initialised.
	 * @param c reference to the controller
	 */
	public void setController(Controller c) {
		controller = c;
	}
	
	/**
	 * Convertion method, translates model values to drawable pixels.
	 * @param var the variable that is to be translated
	 * @return the corresponding pixel value
	 */
	public static int toPixels(double var) {
		return (int)Math.round(SCALE*var);
	}
	
	/**
	 * The main method of <code>View</code>. Clears the screen and draws the chosen objects. Also draws FPS.
	 * Then tidies up and readies for another loop.
	 */
	public void render() {
		clearBackBuffer();
		
		for(Drawable object : objects) {
			object.draw(g2d);
		}
		
		drawFps();
		drawLives();
		drawScore();
        
        graphics = buffer.getDrawGraphics();
        graphics.drawImage(bufferedImage, 0, 0, null);
        
        if(!buffer.contentsLost())
            buffer.show();
	}
	
	/**
	 * Called if the game ended and the interface can be dismissed.
	 */
	public void dispose() {
		if(graphics != null) 
			graphics.dispose();
		if(g2d != null)
			g2d.dispose();
		
		device.setFullScreenWindow(null);
		window.dispose();
	}
	
	/**
	 * The clear back buffer.
	 */
	private void clearBackBuffer() {
		g2d = bufferedImage.createGraphics();
        g2d.setColor(BACKGROUND_COLOR);
        g2d.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
	}
	/**
	 * Draws FPS to the screen.
	 */
	private void drawFps() {
		g2d.setFont(new Font("Courier New", Font.PLAIN, 14));
        g2d.setColor(Color.GREEN);
        g2d.drawString(String.format("FPS: %s", controller.getFps()), 1220, 790);
	}
	private void drawLives() {
		g2d.setFont(new Font("Open Sans", Font.PLAIN, 20));
		g2d.setColor(Color.RED);
		g2d.drawString(String.format("%s", model.player.getLifeCount()), 1028, 226);
	}
	private void drawScore() {
		g2d.setFont(new Font("Open Sans", Font.PLAIN, 30));
		g2d.setColor(Color.WHITE);
		g2d.drawString(String.format("%s", controller.score), 1020, 100);
	}
	/**
	 * Includes <code>GameInterface</code> and <code>PlayerSprite</code> objects in the list to draw.
	 */
	public void showGameView() {
		objects.add(gameInterface);
		objects.add(playerSprite);
	}
	/**
	 * Removes <code>GameInterface</code> and <code>PlayerSprite</code> objects from the list to draw.
	 */
	public void hideGameView() {
		objects.remove(gameInterface);
		objects.remove(playerSprite);
	}
	/**
	 * Adds an enemy sprite.
	 * @param e the reference is used to get information from the enemy model to sprite and draw it
	 */
	public void addEnemySprite(Enemy e) {
		objects.add(new EnemySprite(e));
	}
	public void addBulletSprite(Bullet b) {
		objects.add(new BulletSprite(b));
	}
	public void removeSprite(long id) {
		if(id == 0)
			return;
		for(int i = 0; i < objects.size(); ++i) {
			Drawable d = objects.get(i);
			if(d.getId() == id) {
				objects.remove(i);
			}
		}
	}
}
