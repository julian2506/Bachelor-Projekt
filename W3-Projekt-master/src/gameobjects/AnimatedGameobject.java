package gameobjects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import controller.ObjectController;
import playground.Playground;

import collider.Collider;
import collider.RectCollider;
import collider.CircleCollider;


public class AnimatedGameobject extends GameObject{
	
	private BufferedImage[] imageArray;
	double showtime[];
	long zeit;
	boolean loop = false;
	boolean rueckwaerts = false;
	boolean vorwaerts = false;
	
	int loopFrame = 0;
	int backFrame = 0;
	int forFrame = 0;

	public AnimatedGameobject(String id, Playground pg, ObjectController o, 
			double x, double y, double vx, double vy, double sizeX, double sizeY, 
			double scale, BufferedImage[] imageArray, 
		    double[] showtime, long zeit, String abspielmodus, LinkedList<Collider> col) {
		super(id, pg, o, x, y, vx, vy, col); // Konstruktor-Aufruf GameObject
		
		this.imageArray = imageArray;
		this.showtime = showtime;
		this.zeit = zeit;
		this.backFrame = (imageArray.length - 1);
			
		if(abspielmodus.equals("loop")) this.loop = true;
		if(abspielmodus.equals("rueckwaerts")) this.rueckwaerts = true;
		if(abspielmodus.equals("vorwaerts")) this.vorwaerts = true;
			
		for (int i = 0; i < imageArray.length; i++ ) { 	
			// Radius festlegen
			if (imageArray[i].getHeight() == imageArray[i].getWidth()) {
				setRadius(imageArray[i].getWidth() * scale);
			}
			else { // Hoehe und Breite festlegen
				setWidthAndHeight(imageArray[i].getWidth() * scale, imageArray[i].getHeight() * scale);
			}
		}
	}

	public void draw(Graphics2D g) {
	    //System.out.println("drawing"+this.getId());
		
		// Zeitmessung und Differenz der beiden messen
		long i2 = System.nanoTime();
		
		long elapsedTime = i2 - zeit;
		
		// Differenz von Millisekunden in Sekunden umwandeln
		double seconds = (double)elapsedTime / 1000000000.0;
		
			if(loop) {
				if (loopFrame >= imageArray.length) loopFrame = 0;
				
				if (seconds >= showtime[loopFrame]) {
					g.drawImage(imageArray[loopFrame], (int) Math.round(x - rx), 
							(int) Math.round(y - ry), (int) rx * 2,
		    	  			(int) ry * 2, null);
					loopFrame++;
					zeit = System.nanoTime();
					//System.out.println(showtime[index]);
				} else {
					g.drawImage(imageArray[loopFrame], (int) Math.round(x - rx), 
							(int) Math.round(y - ry), (int) rx * 2,
		    	  			(int) ry * 2, null);
				}
			}
			
			if (vorwaerts) {
				if (seconds >= showtime[forFrame] && forFrame < imageArray.length-1) {
					g.drawImage(imageArray[forFrame], (int) Math.round(x - rx), 
							(int) Math.round(y - ry), (int) rx * 2,
		    	  			(int) ry * 2, null);
					forFrame++;
					zeit = System.nanoTime();
				} else {
					g.drawImage(imageArray[forFrame], (int) Math.round(x - rx), 
							(int) Math.round(y - ry), (int) rx * 2,
		    	  			(int) ry * 2, null);
				}
			} 
			
			if(rueckwaerts) {
				if (seconds >= showtime[backFrame] && backFrame > 1) {
					g.drawImage(imageArray[backFrame], (int) Math.round(x - rx), 
							(int) Math.round(y - ry), (int) rx * 2,
		    	  			(int) ry * 2, null);
					backFrame--;
					zeit = System.nanoTime();
				} else {
					g.drawImage(imageArray[backFrame], (int) Math.round(x - rx), 
							(int) Math.round(y - ry), (int) rx * 2,
		    	  			(int) ry * 2, null);
				}
			}

	} 

}