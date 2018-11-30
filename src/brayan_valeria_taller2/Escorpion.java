package brayan_valeria_taller2;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Escorpion extends Thread {
	private PApplet app;
	private PVector pos, vel, ace;
	private PImage escorpion;
	private float max;
	private boolean vivo;
	private int tam, ran;
	private Mundo mundo;

	public Escorpion(PApplet app, Mundo mundo) {
		this.app = app;
		this.mundo = mundo;
		vivo = true;
		escorpion = app.loadImage("malo.png");
		pos = new PVector(app.random(app.width), -50);
		vel = new PVector(0, 0);
		ran = (int) app.random(5, 10);
		max = ran;
		
	}

	public void pintar() {
		app.imageMode(PApplet.CENTER);
		app.image(escorpion, pos.x, pos.y);
		app.imageMode(PApplet.CORNER);
	}

	public void run() {
		try {
			while (vivo) {
				if(mundo.getLog().isDeathMatch()==true) {
					max*=1.5;
					
				}
				if (validarPerseguirSerpiente(mundo.getSer()) == true
						&& mundo.getContadorFresaA() > mundo.getContadorFresaB()) {
					perseguirSerpiente(mundo.getSer());
				} else {
					perseguirSerpienteB(mundo.getSerB());
				}
				if (validarSerpiente(mundo.getSer()) && mundo.getSer().getN() > 1) {
					mundo.getSer().quitarCola();
				}
				if (validarSerpienteB(mundo.getSerB()) && mundo.getSerB().getN() > 1) {
					mundo.getSerB().quitarCola();
				}
				sleep(50);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean validarSerpiente(Serpiente s) {

		if (PApplet.dist(pos.x, pos.y, s.getX().get(s.getX().size() - 1), s.getY().get(s.getY().size() - 1)) < s.getTam()) {
			return true;
		}
		return false;

	}

	public boolean validarSerpienteB(SerpienteB s) {

		if (PApplet.dist(pos.x, pos.y, s.getX().get(s.getX().size() - 1), s.getY().get(s.getY().size() - 1)) < s.getTam()) {
			return true;
		}
		return false;

	}

	public void perseguirSerpiente(Serpiente s) {
		PVector seguir = new PVector(s.getX().get(s.getX().size() - 1), s.getY().get(s.getY().size() - 1));
		PVector dir = PVector.sub(seguir, pos);
		dir.normalize();
		dir.mult((float) 0.5);
		ace = dir;
		vel.add(ace);
		vel.limit(max);
		pos.add(vel);

	}

	public void perseguirSerpienteB(SerpienteB s) {
		PVector seguir = new PVector(s.getX().get(s.getX().size() - 1), s.getY().get(s.getY().size() - 1));
		PVector dir = PVector.sub(seguir, pos);
		dir.normalize();
		dir.mult((float) 0.5);
		ace = dir;
		vel.add(ace);
		vel.limit(max);
		pos.add(vel);

	}

	public boolean validarPerseguirSerpiente(Serpiente s) {

		if (PApplet.dist(pos.x, pos.y, s.getX().get(s.getX().size() - 1),
				s.getY().get(s.getY().size() - 1)) < app.width) {
			return true;
		}
		return false;

	}

	public boolean validarPerseguirSerpienteB(SerpienteB s) {

		if (PApplet.dist(pos.x, pos.y, s.getX().get(s.getX().size() - 1),
				s.getY().get(s.getY().size() - 1)) < app.width) {
			return true;
		}
		return false;

	}

	public PVector getPos() {
		return pos;
	}
}
