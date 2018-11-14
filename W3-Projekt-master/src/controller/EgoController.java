package controller;

public class EgoController extends ObjectController {
  double rad = 0;

  public void updateObject(double gameTime) {
    int pgSizeX = playground.getSizeX();
    int pgSizeY = playground.getSizeY();
    rad = gameObject.getRadius();

    double ts = playground.getTimestep();
    if (gameObject.getX() + rad + gameObject.getVX() * ts >= pgSizeX
        || gameObject.getX() - rad + gameObject.getVX() * ts < 0) {
      gameObject.setX(gameObject.getX() - gameObject.getVX() * ts);
    }
    if (gameObject.getY() + rad + gameObject.getVY() * ts >= pgSizeY
        || gameObject.getY() - rad + gameObject.getVY() * ts < 0) {
      gameObject.setY(gameObject.getY() - gameObject.getVY() * ts);
    }
    applySpeedVector();
  }


}
