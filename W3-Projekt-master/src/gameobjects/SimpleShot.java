package gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import collider.Collider;
import controller.ObjectController;
import playground.Music;
import playground.Playground;
import playground.SpaceInvadersLevel;

public class SimpleShot extends GameObject {

	public File laser = null;
	
  public SimpleShot(String id, Playground pg, ObjectController o, 
		  double x, double y, double vx,
		  double vy) {
    super(id, pg, o, x, y, vx, vy);
  
    this.laser = SpaceInvadersLevel.laser;

    setRectangleMode(3,10);

  }

  public void draw(Graphics2D g) {
    g.setColor(Color.CYAN);

    g.drawLine((int) (Math.round(this.x)), (int) (Math.round(this.y)), (int) Math.round(this.x),
        (int) Math.round(this.y - this.radius));
  }
}
