package controller;

/**
 * Controls and abject that is deleted after a lifetime specified in the constructor, and when it
 * leaves the display.
 */
public class LimitedTimeController extends ObjectController {
  int rad = 3;
  double g0 = -1;
  double duration = 0;

  /**
   * Constructor.
   *
   * @param g0 int initial game time at creation
   * @param duration int duration in milliseconds
   */
  public LimitedTimeController(double g0, double duration) {
    this.g0 = g0;
    this.duration = duration;
  }

  @Override
  public void updateObject(double gameTime) {
    applySpeedVector();

    if (gameObject.getY() >= playground.getSizeY() || gameObject.getY() < 0
        || gameObject.getX() >= playground.getSizeX() || gameObject.getX() < 0
        || (gameTime - g0) > duration) {
      // LinkedList<String> d = (LinkedList<String>) playground.getFlag("delete");
      // d.add(gameObject.getId());
      playground.deleteObject(this.gameObject.getId());
    }
  }

}
