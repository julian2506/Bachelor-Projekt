package gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import controller.ObjectController;
import playground.Playground;

public class MovingBall extends GameObject {

  private int rad = 5;

  public MovingBall(String id, Playground pg, ObjectController o, double x, double y, double vx,
      double vy) {
    super(id, pg, o, x, y, vx, vy);
    // System.out.println("ball alloc");
  }

  @Override
  public void updateObject(double gameTime) {
    int pgSize = 100;
    // System.out.println(gameTime+" "+this.x+" "+this.y+" "+this.vx) ;
    if (this.x + this.rad > pgSize) {
      this.vx = -1 * this.vx;
    }
    if (this.x + this.rad < 0) {
      this.vx = -1 * this.vx;
    }
    this.x += this.vx;
    this.y += this.vy;
  }

  public void draw(Graphics2D g) {
    // System.out.println("paint") ;
    g.setColor(Color.RED);
    g.drawOval((int) (this.x - this.rad), (int) (this.y - this.rad), this.rad, this.rad);
  }
}
