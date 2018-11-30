package brayan_valeria_taller2;

import processing.core.PApplet;
import processing.core.PImage;

public class Hongo extends Bonificador implements Runnable {
	private PImage hongo;

	private int dir;
	private boolean vivo;
	private Thread hilo;

	public Hongo(PApplet app) {
		super(app);
		hongo = app.loadImage("hongo.png");
		dir=5;
		vivo=true;
	
		
		
		
	}

	@Override
	public void pintar() {

		app.imageMode(app.CENTER);
		app.image(hongo, x, y, tam, tam);
		app.imageMode(app.CORNER);
	}

	public void mover() {
		
	      x+= dir;
		 
	       if(x>= app.width || x<= 0) {
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
