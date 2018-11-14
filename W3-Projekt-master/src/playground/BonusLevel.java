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
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import controller.ObjectController;
import playground.Playground;



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


public class BonusLevel extends HitTwiceLevel {

  public static final double BONUSINTERVAL = 10. ;
  
  double lastMod = 0 ;
  
  public BonusLevel(int SIZEX, int SIZEY) {
    super(SIZEX, SIZEY);
  }


  // kleiner Tipp!
  @Override
  protected String getStartupMessage() {
    return "3 shots / alien required!!!";
  }
  
  
  protected void spawnBonusObject(double gameTime) {
    double rx = Math.random()*this.getSizeX()*0.8+0.1*getSizeX() ;
    double ry = Math.random()*this.getSizeY() + 0.1*getSizeY();
    TextObject bo = new TextObject("bonus", this, new LimitedTimeController(gameTime, BONUSINTERVAL*0.7), rx,ry,0,0,"bonus",20) ;
    bo.setTextColor(Color.GREEN) ;
    addObject(bo) ;
  }
  /*
  @Override
  protected int calcNrEnemies() {
    return 1;
  }*/
  

  
  @Override
  public void applyGameLogic(double gameTime) {
    String gameStatus = (String) getFlag("gameStatus") ;
    //String subStatu
    if (gameStatus.equals("playing")) {
      double mod = gameTime%BONUSINTERVAL ;
      if (mod < lastMod) {
        spawnBonusObject(gameTime) ;
      }
            
      lastMod = mod ;
      
      GameObject ego = getObject("ego") ;
      GameObject bonus = getObject("bonus") ;
      if (bonus != null) {
        if (ego.getDistance(bonus) < 0) {
          System.out.println(",newlife");
          Integer lives = (Integer) getFlag("egoLives") ;
          if (lives == null) {
            lives=3;
          }
          deleteObject(bonus.getId()) ;
          setFlag("egoLives", lives+1) ;
        }
      }
      
    
    }
    super.applyGameLogic(gameTime);
    
  }
    
 



}
