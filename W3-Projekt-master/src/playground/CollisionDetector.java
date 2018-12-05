package playground;


import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import gameobjects.GameObject;
import playground.Playground;

public class CollisionDetector {
	
    GameObject s = null;
    GameObject enemies = null;
	
	boolean kollidiert = false;	
	
	public static boolean CollisionDetection(GameObject s, GameObject e) {

		if (s.collisionMode == GameObject.RECTANGLE && e.collisionMode==GameObject.RECTANGLE) {
			if(checkCollisionRectRect(s.getX(), s.getY(), 20, 10, e.getX(), e.getY(), 20, 10)) return true;
		}
		else if ((s.collisionMode == GameObject.RADIUS && e.collisionMode==GameObject.RECTANGLE) || 
				 (s.collisionMode == GameObject.RECTANGLE && e.collisionMode==GameObject.RADIUS)) {
			//if(checkCollisionRectCirc(s.getX(), s.getY(), s.getRadius(), e.getX(), e.getY(), 20, 20)) return true;
		}
		else if ((s.collisionMode == GameObject.RADIUS && e.collisionMode==GameObject.RADIUS)) {
				//System.out.println("Es funktioniert.");
				if(checkCollisionCircCirc(s.getX(), s.getY(), s.getRadius(), e.getX(), e.getY(), e.getRadius())) return true;
		}
		return false;
		}

	
	public static boolean checkCollisionRectRect(double rx1, double ry1, double rw1, double rh1, double rx2, double ry2, double rw2, double rh2) {	
		//System.out.println("checkCollisionRectRect geöffnet");

		if ((rx1 + rw1/2) >= (rx2-rw2/2)) {
				if ((rx1 - rw1/2) <= (rx2+rw2/2)) {
					if ((ry1 + rh1/2) >= (ry2-rh2/2)) {
						if ((ry1-rh1) <= (ry2+rh2/2)) {
							//System.out.println("Kollision Rechteck mit Rechteck");
							return true;
						}
					}
				}
			}
			return false;
		}
	
	
	public static boolean checkCollisionRectCirc(double cx, double cy, double cr, double rx, double ry, double rw, double rh) {
			double circleDistX = Math.abs(cx - (rx + rw/2));
			double circleDistY = Math.abs(cy - (ry + rw/2));
			
			//System.out.println("cx:"+cx+" "+"cy:"+cy+" "+"cr:"+cr+" "+"rx:"+rx+" "+"ry:"+ry+" "+"rw:"+rw+" "+"rh:"+rh+" "+"circleDistX:"+circleDistX+" "+"circleDistY:"+circleDistY);

			if(circleDistX > (rw/2 + cr )) return false; // falls horizontale Distanz groesser als Radius ist
			if(circleDistY > (rh/2 + cr )) return false; // falls vertikale Distanz groesser als Radius ist
			
			if(circleDistX <= (rw/2)) {	// falls horizontale Distanz kleiner gleich Radius
				System.out.println("Kollision Rechteck mit Kreis");
				return true; 
			}
			if(circleDistY <= (rh/2)) {	// falls vertikale Distanz kleiner gleich Radius
				System.out.println("Kollision Rechteck mit Kreis2");
				return true; 
			}
					
			double cornerDistSqr = Math.pow(circleDistX-rw/2, 2) + Math.pow(circleDistY-rh/2, 2); // Satz des Pythagoras
			return (cornerDistSqr <= cr*cr); // falls true zurueckgegeben: Kollision
	}

	public static boolean checkCollisionCircCirc(double cx1, double cy1, double cr1, double cx2, double cy2, double cr2) {
			//System.out.println(cx1 + " " + cy1 + " " + cr1 + " " + cx2 + " " + cy2+ " " + cr2);
			int kathete1 = (int) (Math.abs(cx2-cx1));	// Entfernung auf x-Achse
			int kathete2 = (int) (Math.abs(cy2-cy1));	// Entfernung auf y-Achse
			int hypothenuse = (int) (cr1+cr2); // Summe der beiden Radien
			//System.out.println(kathete1 + " " + kathete2 + " " + hypothenuse + " ");
			if (((kathete1^2) + (kathete2^2)) <= (hypothenuse^2)) {
				System.out.println("Kollision");
				return true;
			}
			return false;
	}


}