package collider;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;

import controller.ObjectController;
import playground.Playground;

public class RectCollider extends Collider {
	
	double x;
    double y;
    double vx; 
    double vy;
    
    private Color color = Color.WHITE;
	
	public RectCollider (String id, Playground playground, ObjectController controller) {
		
		super (id, playground, controller);

	}
	
	public void draw(Graphics2D g){
		g.setColor(color);
		int x1 = (int) (this.x - 100);
		int y1 = (int) (this.y - 200/2);
		int width1 = (int) width;
		int height1 = (int) height;
		System.out.println("gemalt");

		g.fillOval(x1, y1, width1, width1);
			
	}
	
}
