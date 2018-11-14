package controller;

import gameobjects.GameObject;
import playground.Playground;

/**
 * Class that controls the LOGICAL behavior of an object independently of how it is displayed or
 * drawn. The most important method here is {@link #updateObject}: this method is, by various
 * indirections, called exactly once per game time step for every object that is on the playground.
 * It has, by virtue of the member variables {@link #playground} and {@link o} (of types
 * {@link Playground} and {@link GameObject}, respectively), full access to
 * <ul>
 * <li>the object it is controlling
 * <li>the playground this object belongs to
 * </ul>
 * Typically, updateObject would check whether an object leaves the screen to react appropriately.
 * In that case the object can be marked for deletion (by adding it to the flag "deleted" that is
 * always defined for any playground), but of course other reactions are possible like rebounding,
 * emerging on the other side, ...
 */

public abstract class ObjectController {
  protected GameObject gameObject = null;
  protected Playground playground = null;

  public void setObject(GameObject gameObject) {
    this.gameObject = gameObject;
  }

  public void setPlayground(Playground playground) {
    this.playground = playground;
  }

  /**
   * Is called once every game time step by the game itself. NEVER call this directly, not
   * necessary!<br>
   * The method can do whatever it likes, including nothing. The field {@link #gameObject} contains
   * a reference to the controlled object. The field {@link #playground} contains a reference to the
   * Playground the object belongs to (useful for getting the pixel size in x and y of the playing
   * field.
   *
   * @param gameTime the game time in milliseconds
   */
  public abstract void updateObject(double gameTime);

  /**
   * Convenience method: simply moves the object forward one step from its present position, using
   * its present speed.
   */
  public void applySpeedVector() {
    double ts = playground.getTimestep();
    gameObject.setX(gameObject.getX() + gameObject.getVX() * ts);
    gameObject.setY(gameObject.getY() + gameObject.getVY() * ts);
  }
}
