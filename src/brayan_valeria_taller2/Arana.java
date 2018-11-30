package brayan_valeria_taller2;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public abstract class Arana extends Thread {
	protected PApplet app;
	protected int nivel;
	protected PVector pos, vel, ace;
	protected PImage arana;
	protected int fresas;
	protected float max;
	protected boolean vivo, congelado, encafeinado, dientes;
	protected int tam, contadorHielo, contadorCafe, ran, contadorDientes;
	protected Mundo mundo;

	public Arana(PApplet app, Mundo mundo) {
		this.app = app;
		this.mundo = mundo;
		contadorHielo = 0;
		dientes = false;
		vivo = true;
	}

	public abstract void pintar();

	/**
	 * Se establece los estados de cada araña (crecer, perder fresas, velocidad), la
	 * interaccion con la serpiente, con los bonificadores, las fresas
	 */
	public void run() {
		try {
			while (vivo) {
				if (mundo.getLog().isDeathMatch() == true) {
					max *= 1.4;
				}
				if (congelado == false) {
					if (validarPerseguirSerpiente(mundo.getSer()) == true && fresas > mundo.getSer().getN()) {
						perseguirSerpiente(mundo.getSer());
					} else if (validarPerseguirSerpiente(mundo.getSer()) == true && fresas < mundo.getSer().getN()) {
						huirSerpiente(mundo.getSer());
					} else if (validarPerseguirSerpienteB(mundo.getSerB()) == true && fresas > mundo.getSerB().getN()) {
						perseguirSerpienteB(mundo.getSerB());
					} else if (validarPerseguirSerpienteB(mundo.getSerB()) == true && fresas < mundo.getSerB().getN()) {
						huirSerpienteB(mundo.getSerB());
					} else {

						mover(mundo.getRecurso());
					}

				}
				if (congelado == true) {
					contadorHielo++;
				}
				if (contadorHielo == 40) {
					contadorHielo = 0;
					congelado = false;
				}
				if (encafeinado == true) {
					max *= 1.5f;
					tam = 50;
					contadorCafe++;
				} else {
					tam = 40;
					max = ran;
				}
				if (contadorCafe == 100) {
					contadorCafe = 0;
					encafeinado = false;
				}
				if (dientes == true) {
					contadorDientes++;
				}
				if (contadorDientes == 50) {
					contadorDientes = 0;
					dientes = false;
				}
				if (validar(mundo.getRecurso()) == true) {
					mundo.getRecurso().ponerFresa();
					fresas++;
					ace.x = 0;
					ace.y = 0;
				}
				for (int i = 0; i < mundo.getBonificadores().size(); i++) {
					if (validarBonificador(mundo.getBonificadores().get(i)) == true) {
						if (mundo.getBonificadores().get(i) instanceof Hongo) {
							fresas /= 2;
						}
						if (mundo.getBonificadores().get(i) instanceof Hielo) {
							congelado = true;
						}
						if (mundo.getBonificadores().get(i) instanceof Cafe) {
							encafeinado = true;
						}
						if (mundo.getBonificadores().get(i) instanceof Dientes) {
							dientes = true;
						}
						mundo.getBonificadores().remove(i);
					}

				}
				if (validarSerpiente(mundo.getSer()) && fresas > mundo.getSer().getN() && mundo.getSer().getN() > 1) {
					mundo.getSer().quitarCola();
					fresas++;
				}
				if (validarSerpienteB(mundo.getSerB()) && fresas > mundo.getSerB().getN()
						&& mundo.getSerB().getN() > 1) {
					mundo.getSerB().quitarCola();
					fresas++;
				}
				if (validarSerpiente(mundo.getSer()) && fresas > mundo.getSer().getN() && mundo.getSer().getN() > 1
						&& dientes == true) {
					mundo.getSer().quitarColaTodas();
					fresas += mundo.getSer().getN();
				}
				if (validarSerpienteB(mundo.getSerB()) && fresas > mundo.getSerB().getN() && mundo.getSerB().getN() > 1
						&& dientes == true) {
					mundo.getSerB().quitarColaTodas();
					fresas += mundo.getSerB().getN();
				}
				sleep(50);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public abstract void perseguirSerpiente(Serpiente s);

	public abstract void huirSerpiente(Serpiente s);

	public abstract void perseguirSerpienteB(SerpienteB s);

	public abstract void huirSerpienteB(SerpienteB s);

	public void quitarFresa() {
		fresas--;
	}

	public void quitarTodo() {
		fresas -= fresas;
	}

	/**
	 * Validacion de contacto entre araña y fresa
	 * 
	 * @param fresa
	 * @return verdadero si hay "contacto" con un recurso fresa, de lo contrario
	 *         falso
	 */
	public boolean validar(Recurso fresa) {
		if (PApplet.dist(pos.x, pos.y, fresa.getX(), fresa.getY()) < fresa.getTam()) {
			return true;
		} else {
			return false;

		}
	}

	/**
	 * Validacion de contacto entre araña y bonificador
	 * 
	 * @param boni
	 * @return verdadero si hay "contacto" con un bonificador, de lo contrario falso
	 */
	public boolean validarBonificador(Bonificador boni) {
		if (PApplet.dist(pos.x, pos.y, boni.getX(), boni.getY()) < boni.getTam()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Validacion de contacto entre serpiente y araña
	 * 
	 * @param s serpiente
	 * @return verdadero si hay "contacto" con serpiente, de lo contrario falso
	 */
	public boolean validarSerpiente(Serpiente s) {

		if (PApplet.dist(pos.x, pos.y, s.getX().get(s.getX().size() - 1), s.getY().get(s.getY().size() - 1)) < s
				.getTam()) {
			return true;
		}
		return false;

	}

	public boolean validarSerpienteB(SerpienteB s) {

		if (PApplet.dist(pos.x, pos.y, s.getX().get(s.getX().size() - 1), s.getY().get(s.getY().size() - 1)) < s
				.getTam()) {
			return true;
		}
		return false;

	}

	public boolean validarPerseguirSerpienteB(SerpienteB s) {

		if (PApplet.dist(pos.x, pos.y, s.getX().get(s.getX().size() - 1),
				s.getY().get(s.getY().size() - 1)) < s.getTam() * 8) {
			return true;
		}
		return false;

	}

	public boolean validarPerseguirSerpiente(Serpiente s) {

		if (PApplet.dist(pos.x, pos.y, s.getX().get(s.getX().size() - 1),
				s.getY().get(s.getY().size() - 1)) < s.getTam() * 8) {
			return true;
		}
		return false;

	}

	public PVector getPos() {
		return pos;
	}

	public int getFresas() {
		return fresas;
	}

	public abstract void mover(Recurso r);

}
