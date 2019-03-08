package collider;

import java.awt.Graphics2D;
import java.util.LinkedList;

import controller.ObjectController;
import playground.Playground;

public class CompositeCollider extends Collider {

	public CompositeCollider(String id, Playground playground, ObjectController controller, 
			LinkedList<Collider> sc) {
		
		super(id, playground, controller);
		this.scol = sc;
	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean CollidesWith(LinkedList<Collider> other) {
	//System.out.println("Check");
		for (Collider sc : other) {
			
			if (this.id == "rectCol" && sc.id == "circCol") {
				if (Collider.checkCollisionCircRect(sc.x, sc.y, sc.width/2, this.x, this.y, this.width, this.height)) {
					return true;
				}
			}
			if (this.id == "circCol" && sc.id == "rectCol") {
				if (Collider.checkCollisionCircRect(this.x, this.y, this.width, sc.x, sc.y, sc.width, sc.height)) {
					return true;
				}
			}
			if (this.id == "rectCol" && sc.id == "rectCol") {
				if (Collider.checkCollisionRectRect(this.x, this.y, this.width, this.height, sc.x, sc.y, sc.width, sc.height)) {
					return true;
				}
			}
			if (this.id == "circCol" && sc.id == "circCol") {
				if (Collider.checkCollisionCircCirc(this.x, this.y, this.width, sc.x, sc.y, sc.width)) {
					return true;
				}
			}
			if (this.id == "compCol" && sc.id == "circCol") {
				for (Collider s : this.scol) {
					if (s.id == "rectCol" && sc.id == "circCol") {
						if (Collider.checkCollisionCircRect(sc.x, sc.y, sc.width/2, s.x, s.y, s.width, s.height)) {
							return true;
						}
					}	
					if (s.id == "circCol" && sc.id == "circCol") {
						if (Collider.checkCollisionCircCirc(s.x, s.y, s.width, sc.x, sc.y, sc.width)) {
							return true;
						}
					}
				}
			}
			if (this.id == "compCol" && sc.id == "rectCol") {
				for (Collider s : this.scol) {
					if (s.id == "rectCol" && sc.id == "rectCol") {
						if (Collider.checkCollisionRectRect(s.x, s.y, s.width, s.height, sc.x, sc.y, sc.width, sc.height)) {
							return true;
						}
					}
					if (s.id == "circCol" && sc.id == "rectCol") {
						if (Collider.checkCollisionCircRect(s.x, s.y, s.width, sc.x, sc.y, sc.width, sc.height)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
