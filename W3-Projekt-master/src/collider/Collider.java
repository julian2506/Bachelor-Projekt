package collider;

import gameobjects.GameObject;
import playground.Playground;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;

import controller.ObjectController;
import gameobjects.AnimatedGameobject;

public abstract class Collider {
	
	protected double x = 0;
	protected double vx = 0;
	protected double y = 0;
	protected double vy = 0;
	protected double radX = 0;
	protected double radY = 0;
	public String id = null;
	
	protected double width;
	protected double height;
	
	protected GameObject gameobject = null;
	protected Playground playground = null;
	protected ObjectController controller = null;
	
	protected boolean active = true;
	
	public Collider (String id, Playground playground, ObjectController controller, double x,
		      double y, double vx, double vy) {
		
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		
		this.id = id;
		this.controller = controller;
		this.playground = playground;
		
		this.width = 100;
		this.height = 200;
		
		//System.out.println("collider erstellt");
	}
	
	
	public static boolean checkCollisionRectRect(double rx1, double ry1, double rw1, double rh1, double rx2, double ry2, double rw2, double rh2) {	

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

			if(circleDistX > (rw/2 + cr )) return false;
			if(circleDistY > (rh/2 + cr )) return false;
			
			if(circleDistX <= (rw/2)) {	
				//System.out.println("Kollision Rechteck mit Kreis");
				return true; 
			}
			if(circleDistY <= (rh/2)) {
				//System.out.println("Kollision Rechteck mit Kreis2");
				return true; 
			}
					
			double cornerDistSqr = Math.pow(circleDistX-rw/2, 2) + Math.pow(circleDistY-rh/2, 2); // Satz des Pythagoras
			return (cornerDistSqr <= cr*cr); // falls true zurueckgegeben: Kollision
	}

	public static boolean checkCollisionCircCirc(double cx1, double cy1, double cr1, double cx2, double cy2, double cr2) {
			//System.out.println(cx1 + " " + cy1 + " " + cr1 + " " + cx2 + " " + cy2+ " " + cr2);
			int kathete1 = (int) (Math.abs(cx2-cx1));
			int kathete2 = (int) (Math.abs(cy2-cy1));
			int hypothenuse = (int) (cr1+cr2);
			//System.out.println(kathete1 + " " + kathete2 + " " + hypothenuse + " ");
			if (((kathete1^2) + (kathete2^2)) <= (hypothenuse^2)) {
				System.out.println("Kollision");
				return true;
			}
			return false;
	}

	
	


	public String getId() {
		return id;
	}


	public boolean isActive() {
	    return active;
	  }

	public void setObject(GameObject gameObject) {
	    this.gameobject = gameObject;
	}

	public void setController(ObjectController controller) {
		this.controller = controller;
	}


	public void setPlayground(Playground playground) {
		this.playground = playground;
	}

	public abstract boolean CollidesWith(Collider other);
	
	public abstract void updateObject(double gameTime);

	public abstract void draw(Graphics2D g);
	
	public void applySpeedVector() {
	    double ts = playground.getTimestep();
	    gameobject.setX(gameobject.getX() + gameobject.getVX() * ts);
	    gameobject.setY(gameobject.getY() + gameobject.getVY() * ts);
	  }


}