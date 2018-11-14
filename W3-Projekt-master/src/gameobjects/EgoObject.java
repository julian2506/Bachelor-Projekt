package gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import controller.ObjectController;
import playground.Playground;

public class EgoObject extends GameObject {

  public EgoObject(String id, Playground pg, ObjectController o, double x, double y, double vx,
      double vy) {
    super(id, pg, o, x, y, vx, vy);
    setRadiusMode(15);
  }

  public void draw(Graphics2D g) {
    g.setColor(Color.RED);
    int posX = (int) (x - radius);
    int posY = (int) (y - radius);
    int rad = (int) (2 * radius);
    g.fillOval(posX, posY, rad, rad);
  }
}
