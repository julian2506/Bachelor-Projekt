package playground;

import java.awt.Color;
import controller.EgoController;
import controller.FallingStarController;
import controller.ObjectController;
import gameobjects.BitmapObject;
import gameobjects.EgoObject;
import gameobjects.FallingStar;
import gameobjects.GameObject;
import gameobjects.TextObject;

/**
 * Class that realizes all the game logic of a very simple game level. The level contains for now
 * only two objects that are {@link GameObject} subclasses: {@link FallingStar} and
 * {@link EgoObject}. Functions performed by this class are:
 * <ul>
 * <li>initially set up the level, spawn all object etc., in method {@link #prepareLevel}
 * <li>React to keyboard commands in method {@link #processInputs}
 * <li>define basic object movement rules for all objects in the level in the various
 * ObjectController subclasses: {@link EgoController} and {@link FallingStarController}.
 * </ul>
 */
public class BossLevel extends SpaceInvadersLevel {

  public BossLevel(int SIZEX, int SIZEY) {
    super(SIZEX, SIZEY);
  }


  @Override
  double calcEnemyShotProb() {
    return 1.5 * this.getTimestep();
  }

  @Override
  protected double calcEnemySpeedX() {
    return ENEMYSPEEDX * 2;
  }

  @Override
  protected double calcEnemySpeedY() {
    return ENEMYSPEEDY * 2;
  }

  @Override
  protected int calcNrEnemies() {
    return (int) 1;
  }

  @Override
  protected GameObject createEnemyShotObject(GameObject parentObject, String name,
      ObjectController limitedTimeController) {
    GameObject ego = this.getObject("ego");

    double deltax = parentObject.getX() - ego.getX();
    double deltay = parentObject.getY() - ego.getY();

    double norm = Math.sqrt(deltax * deltax + deltay * deltay);
    deltax *= -ENEMYSHOTSPEED / norm;
    deltay *= -ENEMYSHOTSPEED / norm;

    TextObject to = new TextObject(name, this, limitedTimeController, parentObject.getX(),
        parentObject.getY(), deltax, deltay, "*", 20);
    to.setTextColor(Color.GREEN);
    return to;

  }


  @Override
  protected GameObject createSingleEnemy(String name, double x_enemy, double y_enemy,
      double vx_enemy, double vy_enemy, ObjectController enemyController, double gameTime) {
    return new BitmapObject(name, this, enemyController, this.canvasX / 2, 10, vx_enemy, 50,
        this.alienImage, this.canvasX / 5, this.canvasY / 5);
  }


  @Override
  protected String getStartupMessage() {
    return "BOSS LEVEL!";
  }



}
