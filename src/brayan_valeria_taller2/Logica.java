package brayan_valeria_taller2;

import de.voidplus.leapmotion.CircleGesture;
import de.voidplus.leapmotion.KeyTapGesture;
import de.voidplus.leapmotion.ScreenTapGesture;
import de.voidplus.leapmotion.SwipeGesture;

import processing.core.PApplet;
import processing.core.PImage;

public class Logica {
	private PApplet app;
	private Mundo mundo;
	private int pantalla;
	private PImage fondo1, fondo2, inicio, iniciarJuego, irInstruc, instrucc, juego, ganar, perder, irInicio;
	private int contador, tiempo;
	private int last;
	private boolean deathMatch;

	public Logica(PApplet app) {
		this.app = app;
		deathMatch = false;
		// carga de imagenes de interfaz
		fondo1 = app.loadImage("data/fondo arena.jpg");
		fondo2 = app.loadImage("data/fondo bosque.png");
		inicio = app.loadImage("data/inicio.jpg");
		iniciarJuego = app.loadImage("data/iniciarjuego.png");
		irInstruc = app.loadImage("data/irInstrucc.png");
		instrucc = app.loadImage("data/instrucc.png");
		juego = app.loadImage("data/juego.png");
		ganar = app.loadImage("data/gano1.png");
		perder = app.loadImage("data/gano2.png");
		irInicio = app.loadImage("data/irInicio.png");
		pantalla = 1;
		last = 0;
		contador = 0;
		tiempo = 90;
	}

	public void pintar() {

		switch (pantalla) {
		case 1:
			app.image(inicio, 0, 0);
			if (app.mouseX > 450 && app.mouseX < 700 && app.mouseY > 400 && app.mouseY < 440) {
				app.image(iniciarJuego, 0, 0);
			}
			if (app.mouseX > 450 && app.mouseX < 720 && app.mouseY > 480 && app.mouseY < 520) {
				app.image(irInstruc, 0, 0);
			}
			break;
		case 2:
			app.image(instrucc, 0, 0);

			break;
		case 3:

			app.image(fondo1, 0, 0);
			mundo.pintar();
			app.image(fondo2, 0, 0);

			app.image(juego, 0, 0);
			app.fill(0);
			app.textSize(30);
			app.text(mundo.getContadorFresaA(), 106, 60);
			app.text(mundo.getContadorFresaB(), 1126, 60);
			contador = app.millis() - last;
			if (app.millis() > last + 1000) {
				last = app.millis();
				tiempo--;
			}
			app.text(tiempo, 972, 60);
			app.text(tiempo, 240, 60);
			if (tiempo < 1 && mundo.getContadorFresaA() == mundo.getContadorFresaB()) {
				tiempo = 20;
				deathMatch = true;
			}
			if (tiempo == 0) {
				if (mundo.ganar() == true) {
					pantalla = 4;
					deathMatch = false;
				}

				if (mundo.ganar() == false) {
					pantalla = 5;
					deathMatch = false;
				}
			}

			break;

		case 4:

			app.image(ganar, 0, 0);
			if (app.mouseX > 564 && app.mouseX < 662 && app.mouseY > 295 && app.mouseY < 335) {
				app.image(irInicio, 0, 0);
			}
			break;

		case 5:

			app.image(perder, 0, 0);
			if (app.mouseX > 450 && app.mouseX < 720 && app.mouseY > 295 && app.mouseY < 335) {
				app.image(irInicio, 0, 0);
			}
			break;
		}

	}

	public void tecla() {
		mundo.serpienteBTecla();
	}

	public void mouse() {
		System.out.println("X: " + app.mouseX + " Y: " + app.mouseY);
		if (pantalla == 1) {
			if (app.mouseX > 450 && app.mouseX < 700 && app.mouseY > 400 && app.mouseY < 440) {
				mundo = new Mundo(app, this);
				mundo.start();
				pantalla = 3;
			}
			if (app.mouseX > 450 && app.mouseX < 720 && app.mouseY > 480 && app.mouseY < 520) {
				pantalla = 2;

			}
		}
		if (pantalla == 2) {
			if (app.mouseX > 1116 && app.mouseX < 1150 && app.mouseY > 6 && app.mouseY < 42) {
				pantalla = 1;

			}
		}

		if (pantalla == 4) {
			if (app.mouseX > 450 && app.mouseX < 720 && app.mouseY > 295 && app.mouseY < 335) {
				pantalla = 1;
				tiempo = 90;
			}
		}

		if (pantalla == 5) {
			if (app.mouseX > 450 && app.mouseX < 720 && app.mouseY > 295 && app.mouseY < 335) {
				pantalla = 1;
				tiempo = 90;
			}
		}

	}

	public boolean isDeathMatch() {
		return deathMatch;
	}

	public int getTiempo() {
		return tiempo;
	}

	public void leapOnSwipeGesture(SwipeGesture g, int state) {
		mundo.leapOnSwipeGesture(g, 1);
	}

	public void leapOnCircleGesture(CircleGesture g, int state) {
		mundo.leapOnCircleGesture(g, state);

	}

	public void leapOnScreenTapGesture(ScreenTapGesture g) {
		mundo.leapOnScreenTapGesture(g);
	}

	public void leapOnKeyTapGesture(KeyTapGesture g) {
		mundo.leapOnKeyTapGesture(g);
	}
}