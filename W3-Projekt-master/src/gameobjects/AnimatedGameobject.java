package gameobjects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import controller.ObjectController;
import playground.Playground;

import collider.Collider;
import collider.RectCollider;
import collider.CircleCollider;


public class AnimatedGameobject extends GameObject{
	
	private BufferedImage[] imageArray;
	double showtime[];
	int index = 0;
	long zeit;
	boolean loop = false;
	boolean rueckwaerts = false;
	boolean vorwaerts = false;
	
	int loopFrame = 0;
	int backFrame = 7;
	int forFrame = 0;
	
	public static double radCirc = 0;
	public static double radXRec = 0;
	public static double radYRec = 0;

	public AnimatedGameobject(String id, Playground pg, ObjectController o, 
			double x, double y, double vx,
		      double vy, double sizeX, double sizeY, double scale, BufferedImage[] imageArray, 
		      double[] showtime, long zeit, String abspielmodus, Collider col) {
		super(id, pg, o, x, y, vx, vy, col);
		
			this.imageArray = imageArray;
			this.showtime = showtime;
			this.zeit = zeit;
			
			if(abspielmodus.equals("loop")) this.loop = true;
			if(abspielmodus.equals("rueckwaerts")) this.rueckwaerts = true;
			if(abspielmodus.equals("vorwaerts")) this.vorwaerts = true;
			
			for (int i = 0; i < imageArray.length; i++ ) { 
				radXRec = (imageArray[i].getWidth() * scale);
				radYRec = (imageArray[i].getHeight() * scale);
				
				if (imageArray[i].getHeight() == imageArray[i].getWidth()) {
					radCirc = imageArray[i].getWidth() * scale;
					setRadiusMode(radCirc);
					//new CircCollider(x, y, radXRec, radYRec, imageArray[i], vx, vy);
				}
				else {
					radXRec = (imageArray[i].getWidth() * scale);
					radYRec = (imageArray[i].getHeight() * scale);
					setRectangleMode(radXRec, radYRec) ;
					//new RectCollider(id, x, y, radXRec, radYRec, vx, vy);
				}
			}
	}

	public void draw(Graphics2D g) {
	    //System.out.println("drawing"+this.getId());
		
		// Zeitmessung und Differenz der beiden messen
		long i2 = System.nanoTime();
		
		long elapsedTime = i2 - zeit;
		
		double seconds = (double)elapsedTime / 1000000000.0;
		
			if(loop) {
				
				if (loopFrame >= imageArray.length) loopFrame = 0;
				if (index >= showtime.length) index = 0;
				
				if (seconds >= showtime[index]) {
					g.drawImage(imageArray[loopFrame], (int) Math.round(x - rx), 
							(int) Math.round(y - ry), (int) rx * 2,
		    	  			(int) ry * 2, null);
					loopFrame++;
					zeit = System.nanoTime();
					//System.out.println(showtime[index]);
					index++;
					
				} else {
					g.drawImage(imageArray[loopFrame], (int) Math.round(x - rx), 
							(int) Math.round(y - ry), (int) rx * 2,
		    	  			(int) ry * 2, null);
				}
			}
			
			if (vorwaerts) {
				
				if (seconds >= showtime[index] && forFrame < imageArray.length-1) {
					g.drawImage(imageArray[forFrame], (int) Math.round(x - rx), 
							(int) Math.round(y - ry), (int) rx * 2,
		    	  			(int) ry * 2, null);
					forFrame++;
					zeit = System.nanoTime();
					index++;
				} else {
					g.drawImage(imageArray[forFrame], (int) Math.round(x - rx), 
							(int) Math.round(y - ry), (int) rx * 2,
		    	  			(int) ry * 2, null);
				}
			} 
			
			if(rueckwaerts) {
			
				if (seconds >= showtime[index] && backFrame > 1) {
					g.drawImage(imageArray[backFrame], (int) Math.round(x - rx), 
							(int) Math.round(y - ry), (int) rx * 2,
		    	  			(int) ry * 2, null);
					backFrame--;
					zeit = System.nanoTime();
					index--;
				} else {
					g.drawImage(imageArray[backFrame], (int) Math.round(x - rx), 
							(int) Math.round(y - ry), (int) rx * 2,
		    	  			(int) ry * 2, null);
				}
			}

	} 

}