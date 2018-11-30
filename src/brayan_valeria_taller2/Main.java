package brayan_valeria_taller2;

import de.voidplus.leapmotion.CircleGesture;
import de.voidplus.leapmotion.KeyTapGesture;
import de.voidplus.leapmotion.ScreenTapGesture;
import de.voidplus.leapmotion.SwipeGesture;
import processing.core.PApplet;

public class Main extends PApplet {
	private Logica log;

	public static void main(String[] args) {
		PApplet.main("brayan_valeria_taller2.Main");

	}

	public void settings() {

		size(1200, 700);
	}

	public void setup() {
		log = new Logica(this);
	}

	public void draw() {
		background(255);
		log.pintar();
	}

	public void keyPressed() {
		log.tecla();
	}

	public void mousePressed() {
		log.mouse();
	}

	public void leapOnSwipeGesture(SwipeGesture g, int state) {
		log.leapOnSwipeGesture(g, 1);
	}

	public void leapOnCircleGesture(CircleGesture g, int state) {
		log.leapOnCircleGesture(g, state);

	}

	public void leapOnScreenTapGesture(ScreenTapGesture g) {
		log.leapOnScreenTapGesture(g);
	}

	public void leapOnKeyTapGesture(KeyTapGesture g) {
		log.leapOnKeyTapGesture(g);
	}

}
