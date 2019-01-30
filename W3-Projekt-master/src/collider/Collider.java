package collider;

import java.awt.Graphics2D;
import gameobjects.GameObject;
import playground.Playground;
import controller.ObjectController;

public abstract class Collider {
	
	public double x = 0;
	protected double vx = 0;
	public double y = 0;
	protected double vy = 0;
	protected double radX = 0;
	protected double radY = 0;
	public String id = null;
	
	public double width = 0;
	public double height = 0;
	public double radius = 0;
	
	protected GameObject gameobject = null;
	protected Playground playground = null;
	protected ObjectController controller = null;
	
	protected boolean active = true;
	
	public Collider (String id, Playground playground, ObjectController controller) {
		
		this.id = id;
		this.controller = controller;
		this.playground = playground;
		
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
			//System.out.println("Kollision");
			return true;
		}
		return false;
	}

	public String getId() {
		return id;
	}
	
	public Collider getCollider() {
		return this;
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
	
	public void updateCol(double gametime) {
		this.x = gameobject.getX();
		this.y = gameobject.getY();
		this.vx = gameobject.getVX();
		this.vy = gameobject.getVY();
		if (this.id == "rect") {
			this.width = gameobject.getWidth();
			this.height = gameobject.getHeight();
		} else {
			this.radius = gameobject.getRadius();
		}
		//System.out.println("x Ko ENEMY(" + gameobject.id + ": " + this.x + ", y Ko ENEMY(" + gameobject.id + ": " + this.y);  
	}

	public abstract void draw(Graphics2D g);

}