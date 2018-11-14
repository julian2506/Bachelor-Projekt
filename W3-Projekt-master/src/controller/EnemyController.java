package controller;

import playground.SpaceInvadersLevel;

/**
 * This class controls the space invaders.
 */
public class EnemyController extends ObjectController {
  @Override
  public void updateObject(double gameTime) {
    //System.out.println("updatre"+gameObject.getId());
    if ((gameObject.getX() > this.playground.getSizeX() * 0.9) && (gameObject.getVX() > 0)) {
      // System.out.println("toleft!"+gameObject.getX());
      gameObject.setVX(-SpaceInvadersLevel.ENEMYSPEEDX);
    }
    if ((gameObject.getX() < this.playground.getSizeX() * 0.1) && (gameObject.getVX() < 0)) {
      // System.out.println("toright!"+gameObject.getX());
      gameObject.setVX(SpaceInvadersLevel.ENEMYSPEEDX);
    }

    // if it reaches the bottom, delete it and deduct points
    if (gameObject.getY() >= this.playground.getSizeY()) {
      this.playground.deleteObject(gameObject.getId());
      // add to points counter
      Integer pts = (Integer) this.playground.getFlag("points");
      this.playground.setFlag("points", pts - 200);
    }

    applySpeedVector();
  }
}
