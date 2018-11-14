package playground;

import controller.FallingStarController;
import controller.LimitedTimeController;
import controller.ObjectController;
import controller.ZickZackController;
import controller.MineController;
import gameobjects.BitmapObject;
import gameobjects.FallingStar;
import gameobjects.GameObject;
import gameobjects.TextObject;

import java.awt.Color;



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


public class HitTwiceLevel extends SpaceInvadersLevel {

  public static final int MAX_HITS = 3 ;
  
  public HitTwiceLevel(int SIZEX, int SIZEY) {
    super(SIZEX, SIZEY);
  }


  // kleiner Tipp!
  @Override
  protected String getStartupMessage() {
    return "3 shots / alien required!!!";
  }

  
  @Override
  void actionIfEnemyIsHit(GameObject e, GameObject shot, double gameTime) {
    int counter = 0 ;
    String flagName = e.getId()+"_counter" ;
    Object counterFlag = getFlag(flagName) ;
    if (counterFlag == null) {
      setFlag(flagName, Integer.valueOf(0)) ;
      counterFlag = getFlag(flagName) ;
    }
    
    counter = (Integer) counterFlag ;
    System.out.println(flagName+counter);
    if(counter >= MAX_HITS) {
      super.actionIfEnemyIsHit(e, shot, gameTime) ;
    } else {
      setFlag(flagName, counter+1) ;
    }
    deleteObject(shot.getId());
  }
    
  @Override
  protected GameObject createEnemyShotObject(GameObject parentObject, String name,
      ObjectController limitedTimeController) {
    
    GameObject ego = getObject("ego") ;
    double diffX = parentObject.getX()-ego.getX() ;
    double diffY = parentObject.getY()-ego.getY() ;
    double norm = Math.sqrt(diffX*diffX+diffY*diffY) ;
    double vx = diffX/norm * ENEMYSHOTSPEED ;
    double vy = diffY/norm * ENEMYSHOTSPEED ;
    
    TextObject to = new TextObject(name, this, limitedTimeController, parentObject.getX(), parentObject.getY(), -vx, -vy, "*", 20);
    to.setTextColor(Color.BLUE);
    return to ;
    
  }
  




}
