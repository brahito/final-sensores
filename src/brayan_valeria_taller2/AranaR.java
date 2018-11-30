package brayan_valeria_taller2;

import processing.core.PApplet;
import processing.core.PVector;

public class AranaR extends Arana {
	public AranaR(PApplet app, Mundo mundo) {
		super(app, mundo);
		arana = app.loadImage("aranaA.png");
		pos = new PVector(app.random(app.width), app.random(app.height));
		vel = new PVector(0, 0);
		ran = (int) app.random(3, 6);
		max = ran;
	}

	@Override
	public void pintar() {
		app.imageMode(PApplet.CENTER);
		
		app.image(arana, pos.x, pos.y, tam, tam);
		app.text(fresas, pos.x + tam / 2, pos.y);
		app.imageMode(PApplet.CORNER);
	}

	@Override
	
	/**
	 * Movimiento de las ara�aR, persigue las fresas
	 */
	public void mover(Recurso r) {
		PVector seguir = new PVector(r.getX(), r.getY());
		PVector dir = PVector.sub(seguir, pos);
		dir.normalize();
		dir.mult((float) 0.5);
		ace = dir;
		vel.add(ace);
		vel.limit(max);
		pos.add(vel);
	}

	@Override
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

	@Override
	public void huirSerpiente(Serpiente s) {
		PVector seguir = new PVector(s.getX().get(s.getX().size() - 1) * -1, s.getY().get(s.getY().size() - 1) * -1);
		PVector dir = PVector.sub(seguir, pos);
		dir.normalize();
		dir.mult((float) 0.5);
		ace = dir;
		vel.add(ace);
		vel.limit(max);
		pos.add(vel);
	}

	@Override
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

	@Override
	public void huirSerpienteB(SerpienteB s) {
		PVector seguir = new PVector(s.getX().get(s.getX().size() - 1) * -1, s.getY().get(s.getY().size() - 1) * -1);
		PVector dir = PVector.sub(seguir, pos);
		dir.normalize();
		dir.mult((float) 0.5);
		ace = dir;
		vel.add(ace);
		vel.limit(max);
		pos.add(vel);
		
	}

}
