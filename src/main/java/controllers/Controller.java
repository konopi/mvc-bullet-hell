package main.java.controllers;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import main.java.models.Entity;
import main.java.models.Model;
import main.java.models.Player;
import main.java.views.View;

/**
 * The most important object in the program. <code>Controller</code> implements the <code>Runnable</code> interface,
 * runs in a thread, contains references to every object in the application (directly or indirectly) and organises everything.
 * @author Naichiri
 *
 */
public class Controller implements Runnable {
	
	public Model model;
	public View view;
	
	Player player;
	
	public int score = 0;
	/**
	 * Used for the game loop.
	 */
	private boolean gameRunning = false;
	/**
	 * Used to determine if <code>GameInterface</code> has already been created.
	 */
	private boolean viewInitialised = false;
	
	/**
	 * <code>true</code> if the up arrow key is currently pressed.
	 */
	private boolean upPressed = false;
	/**
	 * <code>true</code> if the left arrow key is currently pressed.
	 */
	private boolean leftPressed = false;
	/**
	 * <code>true</code> if the down arrow key is currently pressed.
	 */
	private boolean downPressed = false;
	/**
	 * <code>true</code> if the right arrow key is currently pressed.
	 */
	private boolean rightPressed = false;
	/**
	 * <code>true</code> if the shift key is currently pressed.
	 */
	private boolean shiftPressed = false;
	/**
	 * <code>true</code> if the fire key is currently pressed.
	 */
	private boolean firePressed = false;
	
	/**
	 * Used to calculate FPS.
	 */
	private long lastFpsTime;
	
	/**
	 * Used to calculate FPS.
	 */
	private int fps = 0;
	/**
	 * Used to display FPS.
	 */
	private int displayFps = 0;
	
	private Thread thread;
	
	/**
	 * The constructor of Controller. Called only once in main method. Starts game thread.
	 * @see main.java.Main
	 * @param m reference to the model
	 * @param v reference to the view
	 */
	public Controller(Model m, View v) {
		model = m;
		view = v;
		
		player = m.player;
		
		view.window.addKeyListener(new InputHandler());
		view.window.requestFocus();
		
		start();
	}
	/**
	 * Used to start game thread execution.
	 */
	public synchronized void start() {
		thread = new Thread(this);
        thread.start();
        gameRunning = true;
	}
	/**
	 * Used to stop game thread execution.
	 */
	public synchronized void stop() {
		try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	/**
	 * Includes main game loop. Calculates fps, does game logic, real main function of the program.
	 */
	@Override
	public void run() {
		
		long lastLoopTime = System.currentTimeMillis();
		long enemySpawnTime = System.currentTimeMillis();
		long timePassed = 0;
		
        while(gameRunning) {
        	
        	long t = 0;
        	long delta = System.currentTimeMillis() - lastLoopTime;
        	lastLoopTime = System.currentTimeMillis();
        	
        	lastFpsTime += delta;
			++fps;
			
			if (lastFpsTime >= 1000) {
				displayFps = fps;
				lastFpsTime = 0;
				fps = 0;
			}
        	
        	if(!viewInitialised) {
        		view.showGameView();
        		view.addEnemySprite(model.addEnemyEntity());
        		view.addEnemySprite(model.addEnemyEntity());
        		viewInitialised = true;
        	}
        	
        	timePassed += System.currentTimeMillis() - enemySpawnTime;
        	if(timePassed > 100000) {
        		view.addEnemySprite(model.addEnemyEntity());
        		view.addEnemySprite(model.addEnemyEntity());
        		timePassed = 0;
        		enemySpawnTime = System.currentTimeMillis();
        	}
        	
        	model.tick(delta);
            view.render();
            
            for (int i = 0; i < model.entities.size(); ++i) {
            	for (int j = i + 1; j < model.entities.size(); ++j) {
            		Entity me = model.entities.get(i);
            		Entity them = model.entities.get(j);
            		
            		if(player.collidesWith(me))
    				{
    					playerCollidedWith(me);
    				}
            		else if(me.whatAmI() == them.whatAmI()) {
            			
            		}
            		
            		else if(me.collidesWith(them)) {
            			if(me.whatAmI() == "enemy")
            				++score;
            			t = me.getId();
            			model.entities.remove(me);
            			view.removeSprite(t);
            			t = them.getId();
            			model.entities.remove(them);
            			view.removeSprite(t);
            		}
            		else if(me.isOutOfBounds()) {
            			t = me.getId();
    					model.entities.remove(me);
    					view.removeSprite(t);
            		}
            		
            	}
			}
            for (int i = 0; i < model.entities.size(); ++i) {
            	if(model.entities.get(i).isScheduled()) {
            		t = model.entities.get(i).getId();
            		model.entities.remove(i);
            		view.removeSprite(t);
            	}
            		
            }
            
            handlePlayerMovement();
            
            if(firePressed) {
            	player.shoot(this);
            }
        }
        view.hideGameView();
        view.dispose();
        stop();
	}
	
	public void playerCollidedWith(Entity e) {
		long t = e.getId();
		model.entities.remove(e);
		view.removeSprite(t);
		
		if(player.lostLastLife())
			gameRunning = false;
	}
	
	/**
	 * Gets displayable FPS count.
	 * @return FPS value to display.
	 */
	public int getFps() {
		return displayFps;
	}
	
	/**
	 * Handles player movement after updating it from the key input.
	 */
	private void handlePlayerMovement() {
		player.setHorizontalSpeed(0.0);
        player.setVerticalSpeed(0.0);
        
        if (leftPressed) {
        	if(shiftPressed) {
        		player.setHorizontalSpeed(-Player.SPEED*Player.FOCUS_MULTIPLIER);
        	} else {
			player.setHorizontalSpeed(-Player.SPEED);
        	}
		} else if ((rightPressed) && (!leftPressed)) {
			if(shiftPressed) {
				player.setHorizontalSpeed(Player.SPEED*Player.FOCUS_MULTIPLIER);
			} else {
			player.setHorizontalSpeed(Player.SPEED);
			}
		}
        if (upPressed) {
        	if(shiftPressed) {
        		player.setVerticalSpeed(-Player.SPEED*Player.FOCUS_MULTIPLIER);
        	} else {
			player.setVerticalSpeed(-Player.SPEED);
        	}
        } else if ((downPressed) && (!upPressed)) {
        	if(shiftPressed) {
				player.setVerticalSpeed(Player.SPEED*Player.FOCUS_MULTIPLIER);
			} else {
			player.setVerticalSpeed(Player.SPEED);
			}
		}
	}
	
	/**
	 * The input handler object. Reads the keys pressed and changes appropriate booleans.
	 * @author Naichiri
	 *
	 */
	private class InputHandler extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				upPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				leftPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				downPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rightPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_Z) {
				firePressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
				shiftPressed = true;
			}
		}
		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				upPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				leftPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				downPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rightPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_Z) {
				firePressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
				shiftPressed = false;
			}
		}
		@Override
		public void keyTyped(KeyEvent e) {
			// if escape then quit
			if(e.getKeyChar() == 27) {
				System.exit(0);
			}
		}
	}
}
