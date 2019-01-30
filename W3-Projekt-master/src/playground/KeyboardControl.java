package playground;

import java.awt.event.KeyEvent;
import java.util.HashMap;

import collider.RectCollider;
import controller.EgoController;
import controller.FallingStarController;
import controller.SimpleShotController;
import gameobjects.EgoObject;
import gameobjects.FallingStar;
import gameobjects.GameObject;
import gameobjects.SimpleShot;
import ui.GameUI;

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
public abstract class KeyboardControl extends Playground {

  public static final int CANVASX = 1700;
  public static final int CANVASY = 1700;
  public static final double SHOTSPEED = 350;
  public static final double EGOSPEED = 400;
  public static int keyInput = 0;

  private int nextShot = 0;

  public KeyboardControl(int SIZEX, int SIZEY) {
    super(SIZEX, SIZEY);
  }



  /**
   * Receives keyboard input from the main game loop and reacts to it.
   *
   * @param keyCode the key that was pressed
   */
  @Override
  public void processInputs(HashMap<Integer, Integer> keys) {
    // System.out.println("RELEAssSSE"+releasedState) ;
    // System.out.println(keys) ;
    GameObject ego = getObject("ego");
    if (ego == null) {
      return;
    }

    for (Integer key : keys.keySet()) {
      if (keys.get(key) == 1) {

        ego.setVX(0);
        ego.setVY(0);
      }
    }


    if (keys.getOrDefault(KeyEvent.VK_LEFT, 0) == 2) {
      ego.setVY(0.0);
      ego.setVX(-SpaceInvadersLevel.EGOSPEED);
    }

    if (keys.getOrDefault(KeyEvent.VK_RIGHT, 0) == 2) {
      ego.setVY(0.0);
      ego.setVX(SpaceInvadersLevel.EGOSPEED);
    }

    if (keys.getOrDefault(KeyEvent.VK_UP, 0) == 2) {
      ego.setVX(0.0);
      ego.setVY(-SpaceInvadersLevel.EGOSPEED);
    }

    if (keys.getOrDefault(KeyEvent.VK_DOWN, 0) == 2) {
      ego.setVX(0.0);
      ego.setVY(SpaceInvadersLevel.EGOSPEED);
    }

    if ((keys.getOrDefault(KeyEvent.VK_ENTER, 0) == 2)  || 
    		(keys.getOrDefault(KeyEvent.VK_P, 0) == 2)) {
      togglePause();
      System.out.println("Das Spiel wurde pausiert.");
      keys.remove(KeyEvent.VK_ENTER);
      keys.remove(KeyEvent.VK_P);
    }
    
    if (keys.getOrDefault(KeyEvent.VK_ESCAPE, 0) == 2) {
        System.exit(0);
        System.out.println("Das Spiel wurde beendet.");
        keys.remove(KeyEvent.VK_ESCAPE);
      }

    if (keys.getOrDefault(KeyEvent.VK_SPACE, 0) == 2) {
      SimpleShotController simpleshot = new SimpleShotController();
      addObject(new SimpleShot("simpleShot" + nextShot++, this, 
    		  simpleshot,
          ego.getX(), ego.getY(), 0, -1. * SHOTSPEED));
      keys.remove(KeyEvent.VK_SPACE);
      keys.remove(KeyEvent.VK_P);
    }
    if (keys.getOrDefault(KeyEvent.VK_N, 0) == 2) {
    	System.out.println("n gedrückt.");
        keys.remove(KeyEvent.VK_N);
        keyInput = 1;
    }
    if (keys.getOrDefault(KeyEvent.VK_L, 0) == 2) {
    	System.out.println("l gedrückt.");
        keys.remove(KeyEvent.VK_L);
        keyInput = 2;
    }
      
  }

}
