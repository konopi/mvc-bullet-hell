package main.java;

import main.java.controllers.Controller;
import main.java.models.Model;
import main.java.views.View;

/**
 * Shoot'em up Project in Java
 * @author Naichiri
 */
public class Main {
	
	public static void main(String[] args) {
		
		Model model = new Model();
		View view = new View(model);
		
		Controller controller = new Controller(model, view);
		
		view.setController(controller);
	}
}
