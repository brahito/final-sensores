package brayan_valeria_taller2;

import java.util.ArrayList;
import java.util.LinkedList;

import de.voidplus.leapmotion.CircleGesture;
import de.voidplus.leapmotion.KeyTapGesture;
import de.voidplus.leapmotion.ScreenTapGesture;
import de.voidplus.leapmotion.SwipeGesture;
import processing.core.PApplet;

public class Mundo extends Thread {
	private PApplet app;

	private ArrayList<Arana> aranas;
	private LinkedList<Bonificador> bonificadores;
	private ArrayList<Escorpion> escorpiones;
	private boolean crear;
	private int contadorFresasA, contadorFresasB, generarBonificador;
	private SerpienteB serpienteB;

	private Serpiente serpiente;
	private Recurso fresa;
	private boolean vivo;
	private Logica log;

	/**
	 * Inicializar mundo
	 * 
	 * @param app - PApplet
	 */

	public Mundo(PApplet app, Logica log) {
		this.app = app;
		this.log = log;
		vivo = true;
		crear = true;
		fresa = new Recurso(app, log);
		fresa.start();
		aranas = new ArrayList<Arana>();
		bonificadores = new LinkedList<Bonificador>();
		escorpiones = new ArrayList<Escorpion>();

		serpiente = new Serpiente(app, this);
		serpienteB = new SerpienteB(app, this);

		for (int i = 0; i < 3; i++) {
			Arana a = new AranaA(app, this);
			aranas.add(a);
			a.start();
		}
		for (int i = 0; i < 3; i++) {
			Arana a = new AranaR(app, this);
			aranas.add(a);
			a.start();
		}

		serpiente.start();
		serpienteB.start();
		contadorFresasA = 0;
		contadorFresasB = 0;

	}

	/**
	 * Pintar los enemigos(arañas), los bonificadores
	 */
	public void pintar() {
		fresa.pintar();
		serpiente.pintar();
		serpienteB.pintar();
		for (int i = 0; i < aranas.size(); i++) {
			aranas.get(i).pintar();
		}
		for (int i = 0; i < escorpiones.size(); i++) {
			escorpiones.get(i).pintar();
		}
		for (int i = 0; i < bonificadores.size(); i++) {
			bonificadores.get(i).pintar();

		}
		if (log.getTiempo() == 50 && crear == true) {
			crear = false;
			for (int i = 0; i < 3; i++) {
				Escorpion e = new Escorpion(app, this);
				escorpiones.add(e);
				e.start();
			}
		}
	}

	public void run() {
		try {
			while (vivo) {
				generarBonificador++;
				if (generarBonificador >= 400) {
					int generar = (int) app.random(1, 5);
					switch (generar) {
					case 1:
						bonificadores.add(new Cafe(app));
						break;
					case 2:
						bonificadores.add(new Hielo(app));

						break;
					case 3:
						bonificadores.add(new Dientes(app));
						break;
					case 4:
						bonificadores.add(new Hongo(app));
						break;
					}
					for (int i = 0; i < bonificadores.size(); i++) {

						if (bonificadores.get(i) instanceof Hielo) {
							Hielo h = (Hielo) bonificadores.get(i);
							System.out.println("empezo");
							h.start();
						}
						if (bonificadores.get(i) instanceof Hongo) {
							Hongo h = (Hongo) bonificadores.get(i);
							System.out.println("empezo");
							h.start();
						}
					}
					generarBonificador = 0;
				}
				sleep(17);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public LinkedList<Bonificador> getBonificadores() {
		return bonificadores;
	}

	public Serpiente getSer() {
		return serpiente;
	}

	public SerpienteB getSerB() {
		return serpienteB;
	}

	public void serpienteBTecla() {
		serpienteB.mover();
	}

	public Recurso getRecurso() {
		return fresa;
	}

	public ArrayList<Arana> getAranas() {
		return aranas;
	}

	public void setRecurso(Recurso fresa) {
		this.fresa = fresa;
	}

	public ArrayList<Escorpion> getEscorpion() {
		return escorpiones;
	}

	public int getContadorFresasA() {
		return contadorFresasA;
	}

	public void setContadorFresasA(int contadorFresasA) {
		this.contadorFresasA = contadorFresasA;
	}

	public int getContadorFresasB() {
		return contadorFresasB;
	}

	public void setContadorFresasB(int contadorFresasB) {
		this.contadorFresasB = contadorFresasB;
	}

	/**
	 * suma el numero de fresas de la serpiente
	 * 
	 * @param a - la cantidad que sumaran las fresas
	 */
	public void masContadorFresasA(int a) {
		contadorFresasA += a;
	}

	public void masContadorFresaB(int a) {
		contadorFresasB += a;
	}

	public int getContadorFresaB() {
		return contadorFresasB;
	}

	public void BajarContadorFresaB(int a) {
		contadorFresasB -= a;
	}

	/**
	 * resta el numero de fresas de la serpiente
	 * 
	 * @param a - la cantidad que restaran las fresas
	 */
	public void BajarContadorFresaA(int a) {
		contadorFresasA -= a;
	}

	public int getContadorFresaA() {
		return contadorFresasA;
	}

	/**
	 * si el numero de fresas de la serpiente es mayor a la de las serpientes, gano,
	 * de lo contrario pierde.
	 * 
	 * @return gano- si gano o no el jugador
	 */
	public boolean ganar() {
		boolean ganoA = false;
		if (contadorFresasA > contadorFresasB) {
			ganoA = true;
		}
		return ganoA;
	}

	public void leapOnSwipeGesture(SwipeGesture g, int state) {
		serpiente.getLeap().leapOnSwipeGesture(g, 1);
	}

	public void leapOnCircleGesture(CircleGesture g, int state) {
		serpiente.getLeap().leapOnCircleGesture(g, state);

	}

	public void leapOnScreenTapGesture(ScreenTapGesture g) {
		serpiente.getLeap().leapOnScreenTapGesture(g);
	}

	public void leapOnKeyTapGesture(KeyTapGesture g) {
		serpiente.getLeap().leapOnKeyTapGesture(g);
	}

	public Logica getLog() {
		return log;
	}

}
