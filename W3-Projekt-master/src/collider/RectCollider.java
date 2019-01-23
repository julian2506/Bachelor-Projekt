package collider;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import controller.ObjectController;
import gameobjects.GameObject;
import playground.Playground;
import playground.SpaceInvadersLevel;

public class RectCollider extends Collider {
	
	double x;
    double y;
    double vx; 
    double vy;
    
    private Color color = Color.WHITE;
	
	public RectCollider (String id, Playground playground, ObjectController controller, double x,
		      double y, double vx, double vy) {
		
		super (id, playground, controller, x, y, vx, vy);

	}

	public boolean CollidesWith(Collider other) {
		if(checkCollisionRectRect(this.x, this.y, this.radX, this.radY, other.x, other.y, this.radX, this.radY)) return true;
		if(checkCollisionRectCirc(this.x, this.y, this.radX, other.x, other.x, this.radX, this.radY)) return true;
		else return false;
	}
	

	public void draw(Graphics2D g){
		g.setColor(color);
		int x1 = (int) (x - width/2);
		int y1 = (int) (y - height/2);
		int width1 = (int) width;
		int height1 = (int) height;
		System.out.println("gemalt");

		g.drawRect(x1, y1, width1, height1);
		
			
	}

	@Override
	public void updateObject(double gameTime) {
		
		
		
	}
	
}
