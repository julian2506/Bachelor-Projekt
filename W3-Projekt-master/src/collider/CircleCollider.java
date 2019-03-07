package collider;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;

import controller.ObjectController;
import playground.Playground;

public class CircleCollider extends Collider {
	
	double x;
    double y;
    double vx; 
    double vy;
    
    private Color color = Color.WHITE;
	
	
	public CircleCollider(String id, Playground playground, ObjectController controller) {
		
		super(id, playground, controller);
	}
	
	
	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}



}
