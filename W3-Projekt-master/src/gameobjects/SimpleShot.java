package gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import controller.ObjectController;
import playground.Music;
import playground.Playground;

public class SimpleShot extends GameObject {


  public SimpleShot(String id, Playground pg, ObjectController o, double x, double y, double vx,
      double vy) {
    super(id, pg, o, x, y, vx, vy);
    setRectangleMode(3,10);
    Music.music("./laser.wav");

  }

  public void draw(Graphics2D g) {
    g.setColor(Color.CYAN);

    g.drawLine((int) (Math.round(this.x)), (int) (Math.round(this.y)), (int) Math.round(this.x),
        (int) Math.round(this.y - this.radius));
  }
}
