import java.awt.Font;
import java.awt.font.TextAttribute;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.AttributedString;
import java.util.ArrayList;

import com.sun.prism.paint.Color;

import gameobjects.GameObject;
import playground.Level1;
import playground.Level2;
import playground.Level3;
import playground.Level4;
import playground.BossLevel;
import playground.MonsterLevel;
import playground.HitTwiceLevel;
import playground.BonusLevel;

import playground.Playground;
import playground.SpaceInvadersLevel;
import playground.SpaceInvadersLevel.DateiHandler;
import ui.GameUI;

/**
 * Main class starting any game, contains main(). Apart from that, this class manages all
 * non-logical functionalities which should be hidden from a game designer like:
 * <ul>
 * <li>Setting up windows, panels, buttons, action callbacks, ...
 * <li>Reading keyboard inputs
 * <li>Redrawing game window if necessary
 * <li>managing the game time and calling the appropriate {@link GameObject} or {@link Playground}
 * methods periodically, at every time step of the game.
 * </ul>
 * There will normally never be a need to modify this file, a designer/game programmer should always
 * redefine the {@link GameObject} and {@link Playground} classes and implement new functionality
 * there. To make a long story short<br>
 * <b>HANDZ OFF THIS FILE!!</b>
 */
public class GameMain {

  public static final int SIZEX = 700;
  public static final int SIZEY = 700;

  public static void main(String[] args) throws IOException {
    ArrayList<Playground> levels = new ArrayList<Playground>();
    //levels.add(new BossLevel(SIZEX, SIZEY));
    //levels.add(new BonusLevel(SIZEX, SIZEY));
    levels.add(new Level1(SIZEX, SIZEY));
    
    
    GameUI gameUI = new GameUI(SIZEX, SIZEY);

    double gameTime = -1;
    Playground playground = null;

    for (int levelIndex = 0; levelIndex < levels.size(); levelIndex++) {
      playground = levels.get(levelIndex);
      playground.prepareLevel("2");
      gameUI.setPlayground(playground);
      // System.out.println("LEVEL" + levelIndex);

      gameTime = 0;

      while ((playground.levelFinished() || playground.gameOver()) == false) {
    	  
    	  

        // paint current state of level and measure time
        long strt = System.nanoTime();
        gameUI.waitWhilePainting();


        // calc real timstep in ms
        long nd = System.nanoTime();
        double realTS = ((nd - strt) / 1000000000.);
        // System.out.println(1./realTS);

        // now update internal state, using as timestep
        // the time it took to paint everything
        // System.out.println("LEVEL" + levelIndex + " painted!");

        playground.setTimestep(realTS);
        gameUI.grabFocus();
        playground.processInputs(gameUI.getCurrentKey());
        if (playground.isPaused() == false) {
          gameTime += realTS;
          playground.updateObjects(gameTime);
          playground.applyGameLogic(gameTime);
        }
      }
      if (playground.gameOver() == true) {
    	  SpaceInvadersLevel.DateiHandler.writeFile();
          break;
      }
    }
    System.exit(0);
  }

}
