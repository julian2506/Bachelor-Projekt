package collider;

import java.awt.Graphics2D;
import java.util.LinkedList;

import controller.ObjectController;
import playground.Playground;

public class CompositeCollider extends Collider {

	public CompositeCollider(String id, Playground playground, ObjectController controller, 
			LinkedList<Collider> sc) {
		
		super(id, playground, controller);

	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
	}


}
