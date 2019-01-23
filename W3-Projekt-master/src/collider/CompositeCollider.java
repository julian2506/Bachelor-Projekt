package collider;

import java.awt.Graphics2D;

import controller.ObjectController;
import playground.Playground;

public class CompositeCollider extends Collider {

	public CompositeCollider(String id, Playground playground, ObjectController controller, double x,
		    double y, double vx, double vy) {
		
		super(id, playground, controller, x, y, vx, vy);
	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateObject(double gameTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean CollidesWith(Collider other) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
