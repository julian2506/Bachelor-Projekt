package controller;

public class SimpleShotController extends ObjectController {
  int rad = 3;

  @Override
  public void updateObject(double gameTime) {
    if (gameObject.getY() < 0) {
      // LinkedList<String> deleteList = (LinkedList<String>) playground.getFlag("delete");
      // deleteList.add(gameObject.getId());
      playground.deleteObject(this.gameObject.getId());
    } else {
      applySpeedVector();
    }
  }
}

