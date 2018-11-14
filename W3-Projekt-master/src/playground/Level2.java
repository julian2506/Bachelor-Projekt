package playground;

import controller.FallingStarController;
import gameobjects.FallingStar;
import gameobjects.GameObject;


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
public class Level2 extends SpaceInvadersLevel {

  public Level2(int SIZEX, int SIZEY) {
    super(SIZEX, SIZEY);
  }


  // kleiner Tipp!
  @Override
  protected String getStartupMessage() {
    return "Get ready for level 2!";
  }



}
