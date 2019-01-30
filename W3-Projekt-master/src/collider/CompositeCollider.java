package collider;

import java.awt.Graphics2D;
import java.util.LinkedList;

import controller.ObjectController;
import playground.Playground;

public class CompositeCollider extends Collider {
	
	
	// neue Liste erzeugen, in der Collider gespeichert werden
	LinkedList<Collider> sc = new LinkedList<Collider>();

	public CompositeCollider(String id, Playground playground, ObjectController controller, double x,
		    double y, double vx, double vy, Collider col) {
		
		super(id, playground, controller, x, y, vx, vy);
		
		//Collider an Liste übergeben
		sc.add(col);
	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
	}


	@Override
	public boolean CollidesWith(Collider other) {

		for (Collider sc : this.sc) {
			if ((sc.id == "rectCol" && other.id == "circCol") || (sc.id == "circCol" && other.id == "rectCol")) {
				if (Collider.checkCollisionRectCirc(sc.x, sc.y, sc.radX, other.x, other.y, other.radX, other.radY)) {
					return true;
				}
			}
			if (sc.id == "circCol" && other.id == "circCol") {
				if (Collider.checkCollisionCircCirc(sc.x, sc.y, sc.radX, other.x, other.y, other.radX)) {
					return true;
				}
			}
			if (sc.id == "rectCol" && other.id == "rectCol") {
				if (Collider.checkCollisionRectRect(sc.x, sc.y, sc.width, sc.height, other.x, other.y, other.width,
						other.height)) {
					return true;
				}
			}
		}
		return false;
	}
}
