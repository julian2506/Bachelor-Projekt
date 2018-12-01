package playground;


import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import gameobjects.GameObject;
import playground.Playground;

public class CollisionDetector {
	
    GameObject s = Playground.getObject("ego");
    LinkedList<GameObject> enemies = collectObjects("enemy", false);
	
	boolean kollidiert = false;
	
	Timer kollision;
	
	private int temp = 0;
	
	public CollisionDetector() {
	
		kollision = new Timer();
		kollision.scheduleAtFixedRate(new TimerTask(){

			@Override
			public void run() {
				for (GameObject e : enemies) {

					if (temp == 0) {
						
						 checkCollisionRectRect(); // um Parameter ergaenzen
						 checkCollisionRectCirc(); // um Parameter ergaenzen
						 temp++;
						 
			        }
					
					if (temp >= 1 && temp <= 65) {
						temp++;
					}
					
					else if (temp == 66) {
						temp = 0;
					}
				}}		
			
		}, 0, 15);
		}

	
	public boolean checkCollisionRectRect(int rx1, int ry1, int rw1, int rh1, int rx2, int ry2, int rw2, int rh2) {
		for (GameObject e : enemies) {
			int width = 20;
			int height = 10;
			if ((rx1 + rw1/2) >= (rx2-rw2/2)) {
				if ((rx1 - rw1/2) <= (rx2+rw2/2)) {
					if ((ry1 + rh1/2) >= (ry2-rh2/2)) {
						if ((ry1-rh1) <= (ry2+rh2/2)) {
							System.out.println("Kollision zweier Rechtecke");
							return true;
						}
					}
				}
			}
		}
	}
	
	public boolean checkCollisionRectCirc(int cx, int cy, int cr, int rx, int ry, int rw, int rh) {
		for (GameObject e : enemies) {
			double circleDistX = Math.abs(cx - (rx + rw/2));
			double circleDistY = Math.abs(cy - (ry + rw/2));
			
			if(circleDistX > (rw/2 + cr )) return false; // falls horizontale Distanz groesser als Radius ist
			if(circleDistY > (rh/2 + cr )) return false; // falls vertikale Distanz groesser als Radius ist
			
			if(cx <= (rw/2)) return true; // falls horizontale Distanz kleiner gleich Radius
			if(cy <= (rh/2)) return true; // falls vertikale Distanz kleiner gleich Radius
					
			double cornerDistSqr = Math.pow(circleDistX-rw/2, 2) + Math.pow(circleDistY-rh/2, 2); // Satz des Pythagoras
			
			return (cornerDistSqr <= cr*cr); // falls true zurueckgegeben: Kollision
			
		}
	}
	
	public boolean checkCollisionCircCirc(int cx1, int cy1, int cr1, int cx2, int cy2, int cr2) {
		
	}
	

}