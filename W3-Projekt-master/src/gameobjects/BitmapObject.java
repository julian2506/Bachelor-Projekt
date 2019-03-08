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

public class BitmapObject extends GameObject {

  private BufferedImage img = null;


  public BitmapObject(String id, Playground pg, ObjectController c, double x, double y, double vx,
      double vy, String bitmap, double scale, LinkedList<Collider> col) {
    super(id, pg, c, x, y, vx, vy, col);

    try {
      img = ImageIO.read(new File(bitmap));
    } catch (IOException e) {
    }
  }

  public BitmapObject(String id, Playground pg, ObjectController o, double x, double y, double vx,
      double vy, BufferedImage bitmap, double scale, LinkedList<Collider> col) {
    super(id, pg, o, x, y, vx, vy, col);

    img = bitmap;

    setRadiusMode(img.getWidth() * scale);
    double radX = (int) (img.getWidth() * scale);
    double radY = (int) (img.getHeight() * scale);
    setRectangleMode(radX, radY) ;
    if (img.getHeight() * scale > radius) {
      setRadiusMode(img.getHeight() * scale);
    }
  }

  public BitmapObject(String id, Playground pg, ObjectController o, double x, double y, double vx,
      double vy, BufferedImage bitmap, double sizeX, double sizeY, LinkedList<Collider> col) {
    super(id, pg, o, x, y, vx, vy, col);

    img = bitmap;

    rx = (int) sizeX / 2.;
    ry = (int) sizeY / 2.;
    setRectangleMode(rx,ry);
  }


  public void draw(Graphics2D g) {
    //System.out.println("drawing"+this.getId());
    g.setColor(Color.RED);
    g.drawImage(img, (int) Math.round(x - rx), (int) Math.round(y - ry), (int) rx * 2,
        (int) ry * 2, null);
  }

  public BufferedImage getBitmap() {
    return img;
  }

}
