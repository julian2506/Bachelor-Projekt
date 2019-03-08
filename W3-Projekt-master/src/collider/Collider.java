package collider;

import java.awt.Graphics2D;
import java.util.LinkedList;

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
	
	public double w = 0;
	public double h = 0;
	public double r = 0;
	
	protected GameObject gameobject = null;
	protected Playground playground = null;
	protected ObjectController controller = null;
	
	protected boolean active = true;
	
	public Collider (String id, Playground playground, ObjectController controller) {
		
		this.id = id;
		this.controller = controller;
		this.playground = playground;
		
	}
	
	public boolean checkCollisionRectRect(Collider r1, Collider r2) {	

		if ((r1.x + r1.w/2) >= (r2.x - r2.w/2)) {
				if ((r1.x - r1.w/2) <= (r2.x + r2.w/2)) {
					if ((r1.y + r1.h/2) >= (r2.y - r2.h/2)) {
						if ((r1.y - r1.h) <= (r2.y + r2.h/2)) {
							//System.out.println("Kollision Rechteck mit Rechteck");
							return true;
						}
					}
				}
			}
			return false;
	}
	
	public boolean checkCollisionCircRect(Collider c, Collider r) {
		double circleDistX = Math.abs(c.x - (r.x + r.w/2));
		double circleDistY = Math.abs(c.y - (r.y + r.w/2));
			
		//System.out.println("c.x:"+c.x+" "+"c.y:"+c.y+" "+"c.r:"+c.r+" "+"r.x:"+r.x+" "+"r.y:"+r.y+" "+"r.w:"+r.w+" "+"r.h:"+r.h+" "+"circleDistX:"+circleDistX+" "+"circleDistY:"+circleDistY);

		if(circleDistX > (r.w/2 + c.r )) return false;
		if(circleDistY > (r.h/2 + c.r )) return false;
			
		if(circleDistX <= (r.w/2)) {	
			//System.out.println("Kollision Rechteck mit Kreis");
			return true; 
		}
		if(circleDistY <= (r.h/2)) {
			//System.out.println("Kollision Rechteck mit Kreis2");
			return true; 
		}
					
		double cornerDistSqr = Math.pow(circleDistX - r.w/2, 2) + Math.pow(circleDistY - r.h/2, 2); // Satz des Pythagoras
		return (cornerDistSqr <= c.r*c.r); // falls true zurueckgegeben: Kollision
	}
	
	public boolean checkCollisionCircCirc(Collider c1, Collider c2) {
		//System.out.println(c1.x + " " + c1.y + " " + c1.r + " " + c2.x + " " + c2.y+ " " + c2.r);
		int kathete1 = (int) (Math.abs(c2.x - c1.x));
		int kathete2 = (int) (Math.abs(c2.x - c1.y));
		int hypothenuse = (int) (c1.r + c2.r);
		
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
	
	public boolean CollidesWith(Collider other) {
		//System.out.println("Check");
			
			if (this.id == "rectCol" && other.id == "circCol") {
				if (checkCollisionCircRect(this, other)) {
					return true;
				}
			}
			if (this.id == "circCol" && other.id == "rectCol") {
				if (checkCollisionCircRect(this, other)) {
					return true;
				}
			}
			if (this.id == "rectCol" && other.id == "rectCol") {
				if (checkCollisionRectRect(this, other)) {
					return true;
				}
			}
			if (this.id == "circCol" && other.id == "circCol") {
				if (checkCollisionCircCirc(this, other)) {
					return true;
				}
			}		
		return false;
	}
	
	public void updateCol(double gametime) {
		
		this.x = gameobject.getX();
		this.y = gameobject.getY();
		this.vx = gameobject.getVX();
		this.vy = gameobject.getVY();
		if (this.id == "rectCol") {
			this.w = gameobject.getWidth();
			this.h = gameobject.getHeight();
		} else {
			this.r = gameobject.getRadius();
		}
		//System.out.println("x Ko ENEMY(" + gameobject.id + ": " + this.x + ", y Ko ENEMY(" + gameobject.id + ": " + this.y);  
	}

	public abstract void draw(Graphics2D g);

}