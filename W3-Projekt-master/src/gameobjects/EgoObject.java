package gameobjects;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import collider.Collider;
import controller.ObjectController;
import playground.Playground;

public class EgoObject extends GameObject {
	  
	
	protected BufferedImage    egoImage     = null;

	
	
  public EgoObject(String id, Playground pg, ObjectController o, double x, double y, double vx,
      double vy, LinkedList<Collider> col) {
    super(id, pg, o, x, y, vx, vy, col);
    setRadiusMode(15);
    this.egoImage = null;
    try {
      this.egoImage = ImageIO.read(new File("./alien.png"));
      System.out.println("Bild wurde geladen");
    } catch (IOException e) {
    }

  }

  public void draw(Graphics2D g) {	  
	  
	g.setColor(Color.WHITE);
    int posX = (int) (x - radius);
    int posY = (int) (y - radius);
    int rad = (int) (2 * radius);
    g.fillOval(posX, posY, rad, rad);
    
  }
  
}

