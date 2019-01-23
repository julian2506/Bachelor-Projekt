package playground;

import java.awt.Graphics2D;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import collider.Collider;
import controller.LevelController;
import gameobjects.GameObject;

/**
 * Playground represents a level of the game, focussing on the game LOGIC, i.e., not so much on the
 * graphical representation. In particular, an instance of Playground
 * <ul>
 * <li>manages the different moving or static objects in a level (e.g., collisions, adding objects,
 * removing objects). This is mainly done by the methods {@link #addObject}, {@link #deleteObject}
 * and {@link #getfObject}.
 * <li>processes keyboard inputs provided by GameMain
 * <li>represents the state of a level represented by <b>flags</b>. Each flag has a name (a String)
 * and an arbitrary value of any type. Methods: {@link #setFlag}, {@link #getFlag}. As an example,
 * the current score is a flag usually named "points", with an Integer as a value. This value can be
 * retrieved and manipulated using the above mentioned methods.
 * </ul>
 */
public abstract class Playground {

  protected int canvasX = -1;
  protected int canvasY = -1;
  protected HashMap<String, GameObject> gameObjects = null;
  //protected HashMap<String, Collider> collider = null;
  protected static HashMap<String, Object> flags = null;
  protected String level = "";
  protected double timeStep = 0;
  private boolean pausedFlag = false;
  LinkedList<GameObject> addables = new LinkedList<GameObject>();
  LinkedList<String> removables = new LinkedList<String>();


  public Playground(int sizeX, int sizeY) {
    gameObjects = new HashMap<String, GameObject>();
    //collider = new HashMap<String, Collider>();
    flags = new HashMap<String, Object>();
    this.canvasX = sizeX;
    this.canvasY = sizeY;
  }

  /**
   * Adds a graphics object to a level.
   *
   * @param o GameObject The object to be added
   */
  public void addObject(GameObject o) {
    // gameObjects.put(o.getId(), o);
    addables.addLast(o);
  }

  /**
   * Adds a graphics object to a level.
   *
   * @param o GameObject The object to be added
   */
  public void addObjectNow(GameObject o) {
    gameObjects.put(o.getId(), o);
  }



  /**
   * Puts objects with a certin substring in teir name into a LinkedLisrt and returns them.
   *
   * @param substr String. The string that must be part of the object name if object is to be
   *        considered found.
   * @return a reference to a LinkedList filled with all objects that have <b>substr</b> in thier
   *         name
   */
  LinkedList<GameObject> collectObjects(String substr, boolean filterInactive) {
    LinkedList<GameObject> l = new LinkedList<GameObject>();
    for (Map.Entry<String, GameObject> entry : this.gameObjects.entrySet()) {
      GameObject obj = entry.getValue();
      if (obj.getId().contains(substr)) {
        if (filterInactive == true) {
          if (obj.isActive()) {
            l.add(obj);
          }
        } else {
          l.add(obj);
        }
      }
    }
    return l;
  };
  
  /*LinkedList<Collider> collectCollider(String substr, boolean filterInactive) {
	    LinkedList<Collider> c = new LinkedList<Collider>();
	    for (Map.Entry<String, Collider> entry : this.collider.entrySet()) {
	    	System.out.println("colliderliste gefuellt");
	      Collider obj = entry.getValue();
	      if (obj.getId().contains(substr)) {
	        if (filterInactive == true) {
	          if (obj.isActive()) {
	            c.add(obj);
	          }
	        } else {
	          c.add(obj);
	        }
	      }
	    }
	    
	    return c;
	  };*/



  /**
   * Removes a graphics object from a level.
   *
   * @param id String The unique identifier of the object
   */
  public void deleteObject(String id) {
    // gameObjects.remove(id);
    removables.addLast(id);
  }

  /**
   * Removes a graphics object from a level immediately, CAUTION.
   *
   * @param id String The unique identifier of the object
   */
  public void deleteObjectNow(String id) {
    gameObjects.remove(id);
  }


  /**
   * Retrieves a graphics object by name.
   *
   * @param id String Unique id of the object
   * @return reference to the requested game object, or null if not found
   */
  public GameObject getObject(String id) {
    return gameObjects.get(id);
  }

  /**
   * Sets a level-wide permanent flag.
   *
   * @param flag String Q unique name in this level. If it exists value is overwritten.
   * @param value Object Any Object can be the value of a flag!
   */

  public static void setFlag(String flag, Object value) {
    flags.put(flag, value);
  }

  /**
   * Retrieves a level-wide flag by name.
   *
   * @param flag String Unique flag id
   * @return the value associated with <b>flag</b>, or <b>null</b> if the flag does not exist.
   */
  public static Object getFlag(String flag) {
    return flags.get(flag);
  }

  /**
   * Reinitializes the level.
   */
  public void reset() {
    gameObjects.clear();
    flags.clear();
  }

  public boolean isPaused() {
    return pausedFlag;
  }

  public void togglePause() {
    pausedFlag = !pausedFlag;
  }


  /**
   * Method meant to be filled with own code, processes Keyboard inputs.
   *
   * @param keyCode The symbolic constant associated with a certain key, see java doc for class
   *        KeyboardListener for details.
   */
  public abstract void processInputs(HashMap<Integer, Integer> keys);

  /**
   * Method meant to be filled with own code, handles the entore game logic (collision checks, timed
   * events, ...).
   *
   * @param gameTime int the in-game time in milliseconds
   */
  public abstract void applyGameLogic(double gameTime);

  public abstract boolean gameOver();

  public abstract boolean levelFinished();

  public int getSizeX() {
    return canvasX;
  }

  public int getSizeY() {
    return canvasY;
  }

  /**
   * Calls all object update methods in level. Internal, never call directly.
   *
   * @param gameTime In-game time in milliseconds
   */
  public void updateObjects(double gameTime) {
    for (GameObject gameObject : gameObjects.values()) {
      if (gameObject.isActive() == true) {
        gameObject.updateObject(gameTime);
      }
    }
    
    for (GameObject o : addables) {
      addObjectNow(o);
    }

    for (String s : removables) {
      deleteObjectNow(s);
    }
    removables.clear();
    addables.clear();
  }

  public void setTimestep(double s) {
    timeStep = s;
  }

  public double getTimestep() {
    return timeStep;
  }

  /**
   * To be redefined!! Draws mainly h level background and global information like points etc.
   *
   * @param g2 Graphics2D abstract drawing object of java Swing, used to carry out all drawing
   *        operations.
   */
  public abstract void redrawLevel(Graphics2D g2);

  /**
   * Internal, do not call directly.
   *
   * @param g2 Graphics2D abstract drawing object of java Swing, used to carry out all drawing
   *        operations.
   */
  public void redraw(Graphics2D g2) {
    redrawLevel(g2);
    for (GameObject gameObject : gameObjects.values()) {
      if (gameObject.isActive()) {
        gameObject.draw(g2);
      }
    }
  }

  /**
   * Sets up a single level. Prepares all objects etc.
   *
   * @param level String a string identifying the level number etc
   */
  public abstract void prepareLevel(String level);

  /**
   * Not yet used.
   *
   * @param lc A LevelController instance.
   */
  public void setLevelController(LevelController lc) {};
  


}
