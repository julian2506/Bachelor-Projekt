package gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;

import collider.Collider;
import controller.ObjectController;
import playground.Playground;

public class FallingStar extends GameObject {

  private Color color = Color.WHITE;

  public FallingStar(String id, Playground playground, ObjectController controller, double x,
      double y, double vx, double vy, LinkedList<Collider> col) {
    super(id, playground, controller, x, y, vx, vy, col);
    setRadiusMode(3);
  }

  public FallingStar(String id, Playground playground, ObjectController controller, double x,
      double y, double vx, double vy, Color color) {
    super(id, playground, controller, x, y, vx, vy);
    radius = 3;
    this.color = color;
  }

  public void draw(Graphics2D g) {
    g.setColor(color);
    int posX = (int) Math.round(x - radius);
    int posY = (int) Math.round(y - radius);
    int rad = (int) radius;
    g.fillOval(posX, posY, rad, rad);
  }
}
