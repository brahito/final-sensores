package brayan_valeria_taller2;

import processing.core.PApplet;
import processing.core.PImage;

public class Hielo extends Bonificador implements Runnable {
	private PImage hielo;
private int dir;
	private boolean vivo;
	private Thread hilo;

	public Hielo(PApplet app) {
		super(app);
		hielo = app.loadImage("hielo.png");
	
		vivo=true;
		dir=5;
	}

	@Override
	public void pintar() {

		app.imageMode(app.CENTER);
		app.image(hielo, x, y, tam, tam);
		app.imageMode(app.CORNER);
	}

	public void mover() {
		y += dir;
			 
	       if(y>= app.height || y<= 0) {
	    	   dir*=-1;
	       }
       
      	}
	
	

	@Override
	public void run() {
         while(vivo) {
        	 try {
        		 mover();
				hilo.sleep(17);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         }

	}
	
	public void start() {
		hilo= new Thread(this);
		hilo.start();
	}
	

}
