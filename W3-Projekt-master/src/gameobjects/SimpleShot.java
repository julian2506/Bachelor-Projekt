package gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.util.LinkedList;

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
		  double vy, LinkedList<Collider> col) {
    super(id, pg, o, x, y, vx, vy, col);
  
    this.laser = SpaceInvadersLevel.laser;

    setWidthAndHeight(3, 12);

  }

  public void draw(Graphics2D g) {
    g.setColor(Color.CYAN);

    g.drawLine((int) (Math.round(this.x)), (int) (Math.round(this.y)), (int) Math.round(this.x),
        (int) Math.round(this.y - 10));
  }
}
