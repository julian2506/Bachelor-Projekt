package gameobjects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import collider.Collider;
import collider.CompositeCollider;
import controller.ObjectController;
import playground.Playground;


/**
 * The class {@link GameObject} represents a (possibly animated) object appearing in a level of the
 * game. It is therefore attached to an instance of the class {@link Playground}. A GameObject has
 * at least the following properties:
 * <ul>
 * <li>2D screen position
 * <li>2D speed
 * <li>a name that is unique within a certain {@link Playground}<br>
 * <li>a reference to the {@link Playground} object it belongs to<br>
 * <li>a reference to an instance of {@link Controller} that handles the movement logic of the
 * object<br>
 * <li>a (circular) radius for primitive collision checking. This may be handled differently in
 * subclasses<br>
 * </ul>
 * The main task of GameObject, or its subclasses, is to draw the object on the screen, which is
 * handles by the {@link #draw} method. It is this method that must be redefined if a new appearance
 * should be realized. For introducing new behavior, it is sufficient to supply a different
 * {@link Controller} instance when constructing a GameObject.
 */
public abstract class GameObject {

  public static final int RADIUS = 0;
  public static final int RECTANGLE = 1;
  public static final int MASK = 2;

  public  String id = null;
  protected double x = 0;
  protected double vx = 0;
  protected double y = 0;
  protected double vy = 0;
  protected double radius = -1;
  protected double rx = -1;
  protected double ry = -1;
  protected BufferedImage mask = null;
  protected boolean active = true;
  public int collisionMode = GameObject.RADIUS;

  private ObjectController controller = null;
  private Collider col = null;

  public GameObject(String id, Playground playground, 
		  ObjectController controller, double x,
		  double y, double vx, double vy, Collider col) {
    setX(x);
    setY(y);
    setVX(vx);
    setVY(vy);
    this.id = id;
    this.controller = controller;
    this.controller.setObject(this);
    this.controller.setPlayground(playground);
    this.col = col;
    this.col.setObject(this);
    this.col.setController(controller);
    this.col.setPlayground(playground);
  }
  
  public GameObject(String id, Playground playground, ObjectController controller, double x,
	double y, double vx, double vy) {
	setX(x);
	setY(y);
	setVX(vx);
	setVY(vy);
	this.id = id;
	this.controller = controller;
	this.controller.setObject(this);
	this.controller.setPlayground(playground);
  }

  public GameObject setRadiusMode(double r) {
    this.collisionMode = GameObject.RADIUS;
    this.radius = r;
    return this;
  }

  public GameObject setRectangleMode(double rx, double ry) {
    this.collisionMode = GameObject.RECTANGLE;
    this.rx = rx;
    this.ry = ry;
    this.radius = rx ;
    if (this.ry > this.rx) {
      this.radius = this.ry ;
    }
    return this;
  }

  public GameObject setMaskMode(BufferedImage mask) {
    this.collisionMode = GameObject.MASK;
    this.mask = mask;
    return this;
  }
  
  public boolean collisionDetection(GameObject other) {
	  System.out.println("Check");
	  if(this.col.CollidesWith(other.col.scol)) {
		 //System.out.println("Check");
		 return true;
	 }

	return false;
  }


  public double getDistance(GameObject other) {
    double dx2 = Math.pow(x - other.getX(), 2);
    double dy2 = Math.pow(y - other.getY(), 2);
    double d = Math.sqrt(dx2 + dy2);
    return d - (getRadius() + other.getRadius());
  }

  public void updateObject(double gameTime) {
    controller.updateObject(gameTime);
    
    // ueberpruefen, ob GameObject einen 
    // Collider hat und diesen updaten
    if (col != null) {
  	  col.updateCol(gameTime);
  	}
  }

  public boolean isActive() {
    return active;
  }


  public GameObject setActive(boolean flag) {
    this.active = flag;
    return this;
  }

  public void setRadius(double radius) {
    this.radius = radius;
  }

  public double getRadius() {
    return radius;
  }
  
  public void setWidthAndHeight(double rx, double ry) {
    this.rx = rx;
    this.ry = ry;
  }
  
  public double getWidth() {
	return rx;
  }
  
  public double getHeight() {
	return ry;
  }

  /**
   * gets the screen X position.
   *
   * @return double screen x position
   */
  public double getX() {
    return x;
  }

  /**
   * gets the screen Y position.
   *
   * @return double screen Y position
   */
  public double getY() {
    return y;
  }

  /**
   * gets the screen X speed in pixels per frame.
   *
   * @return double screen x speed
   */
  public double getVX() {
    return vx;
  }

  /**
   * gets the screen Y speed in pixels per frame.
   *
   * @return double screen y speed
   */
  public double getVY() {
    return vy;
  }

  /**
   * set screen x position.
   *
   * @param x double new position
   */
  public void setX(double x) {
    if (this.active == true) {
      this.x = x;
    }
  }

  /**
   * set screen y position.
   *
   * @param y double new position
   */
  public void setY(double y) {
    if (this.active == true) {
      this.y = y;
    }
  }

  /**
   * set screen x speed.
   *
   * @param vx double new x position
   */
  public void setVX(double vx) {
    if (this.active == true) {
      this.vx = vx;
    }
  }

  /**
   * set screen y speed in pix per frame.
   *
   * @param vy double new y speed.
   */
  public void setVY(double vy) {
    if (this.active == true) {
      this.vy = vy;
    }
  }

  /**
   * return the unique object ID.
   *
   * @return String unique object ID
   */
  public String getId() {
    return id;
  }

  /**
   * Sets a new object controller. Can be done anytime at all.
   *
   * @param controller An instance of {@link Controller} or one of its subclasses.
   */
  public void setObjectController(ObjectController controller) {
    this.controller = controller;
  }
  
  public void setCollider(Collider collider) {
	    this.col = collider;
  }

  /**
   * Draws the object in its current state. Is called by the game engine, should NOT be called by
   * the user.
   *
   * @param g Graphics2D object that has all the necessary drawing functionalities
   */
  public abstract void draw(Graphics2D g);
  
}
