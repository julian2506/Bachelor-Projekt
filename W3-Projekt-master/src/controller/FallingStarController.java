package controller;

/**
 * Controls background stars. When they touch the bottom of the display they reappear on top.
 */
public class FallingStarController extends ObjectController {
  int rad = 3;

  @Override
  public void updateObject(double gameTime) {
    if (gameObject.getY() + rad >= playground.getSizeY()) {
      gameObject.setY(10);
    }
    applySpeedVector();
  }
}
