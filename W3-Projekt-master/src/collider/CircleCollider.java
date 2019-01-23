package collider;

import java.awt.Graphics2D;

import controller.ObjectController;
import playground.Playground;

public class CircleCollider extends Collider {
	
	public CircleCollider(String id, Playground playground, ObjectController controller, double x,
		    double y, double vx, double vy) {
		
		super(id, playground, controller, x, y, vx, vy);
	}

	@Override
	public boolean CollidesWith(Collider other) {
		//System.out.println("collision check");
				// auf Kollisionen prüfen
		if(checkCollisionCircCirc(this.x, this.y, this.radX, other.x, other.y, other.radX)) return true;
		if(checkCollisionRectCirc(this.x, this.y, this.radX, other.x, other.x, other.radX, other.radY)) return true;
		else return false;
	}
	
	
	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateObject(double gameTime) {
		// TODO Auto-generated method stub
		
	}
}
