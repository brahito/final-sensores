package brayan_valeria_taller2;

import processing.core.PApplet;
import processing.core.PImage;

public class Recurso extends Thread {
	private PApplet app;
	private float x, y, tam;
	private PImage fresa;
	private boolean vivo;
	private int dir, vel;
	private Logica log;

	public Recurso(PApplet app, Logica log) {
		this.app = app;
		this.log = log;
		fresa = app.loadImage("data/fresa.png");
		tam = 25;
		ponerFresa();
		vivo = true;
		dir = 1;
		vel = 2;
	}

	public void pintar() {
		app.fill(255, 0, 0);
		app.imageMode(app.CENTER);
		app.image(fresa, x, y, tam, tam);
		app.imageMode(app.CORNER);
	}

	public void run() {
		try {
			while (vivo) {
				mover();
				sleep(17);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void mover() {
		if (log.isDeathMatch()) {
			if (dir == 1) {
				x += vel;
			}
			if (dir == -1) {
				y += vel;
			}
			if (x < 0)
				vel *= -1;
			if (x > app.width)
				vel *= -1;
			if (y < 0)
				vel *= -1;
			if (y > app.height)
				vel *= -1;
		}
	}

	/**
	 * al crear una nueva fresa, se pone en un posicion random
	 */
	public void ponerFresa() {
		dir *= -1;
		x = (int) app.random((app.width - tam) - 1100, (app.width - tam) - 100);

		y = (int) app.random((app.height - tam) - 600, (app.height - tam) - 100);
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getTam() {
		return tam;
	}

}
