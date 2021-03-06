package playground;

import controller.FallingStarController;
import controller.LimitedTimeController;
import controller.ObjectController;
import controller.ZickZackController;
import controller.MineController;
import gameobjects.AnimatedGameobject;
import gameobjects.BitmapObject;
import gameobjects.FallingStar;
import gameobjects.GameObject;
import gameobjects.TextObject;

import java.awt.Color;
import java.util.LinkedList;

import collider.Collider;



/**
 * Class that realizes all the game logic of a very simple game level. The level contains for now
 * only two objects that are {@link GameObject} subclasses: {@link FallingStar} and {@link Student}.
 * Functions performed by this class are:
 * <ul>
 * <li>initially set up the level, spawn all object etc., in method {@link #prepareLevel}
 * <li>React to keyboard commands in method {@link #processInputs}
 * <li>define basic object movement rules for all objects in the level in the various
 * ObjectController subclasses: {@link StudentController} and {@link FallingStarController}.
 * </ul>
 */


public class MonsterLevel extends SpaceInvadersLevel {

  public MonsterLevel(int SIZEX, int SIZEY) {
    super(SIZEX, SIZEY);
  }

  @Override  
  protected void createEnemyShot(GameObject e, double gameTime) {
    double PROB = calcEnemyShotProb() ;
    double diceThrow = Math.random();
    Integer nrEnemyShots = (Integer) (getFlag("enemyShotCounter"));
    if (diceThrow < PROB) {
      setFlag("enemyShotCounter", Integer.valueOf(++nrEnemyShots));

      ObjectController mineCtrl = new MineController(5.);
      TextObject textObject = new TextObject("enmyShot"+nrEnemyShots, this, mineCtrl, e.getX(), e.getY(), 0., 100., "x",  20) ;
      textObject.setTextColor(Color.BLUE);

      addObject(textObject);
    }
  }
  

  // kleiner Tipp!
  @Override
  protected String getStartupMessage() {
    return "Get ready for level 4!!!";
  }


  @Override
  protected GameObject createSingleEnemy(String name, double x_enemy, double y_enemy,
      double vx_enemy, double vy_enemy, ObjectController enemyController, double gameTime, LinkedList<Collider> col) {
    ObjectController zzController = new ZickZackController(gameTime, 0.5);
    return new AnimatedGameobject(name, this, zzController, x_enemy, y_enemy,
            vx_enemy, vy_enemy, this.canvasX / 10, this.canvasY / 10, 0.1, alienImage, alienshowTime, startzeit, "loop", col);
  }



}
