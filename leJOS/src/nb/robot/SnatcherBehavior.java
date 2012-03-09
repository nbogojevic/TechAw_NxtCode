package nb.robot;

import lejos.robotics.subsumption.Behavior;

public class SnatcherBehavior implements Behavior {

  private boolean supress;
  private SnatcherRobot snatcher;

  public SnatcherBehavior(SnatcherRobot snatcher) {
    super();
    this.snatcher = snatcher;
  }

  @Override
  public void action() {
    supress = false;
    snatcher.grab();
    if (supress) {
      return;
    }
    snatcher.findObject();
    if (supress) {
      return;
    }
    snatcher.moveCloser();
    if (supress) {
      return;
    }
    snatcher.findObject();
    if (supress) {
      return;
    }
    snatcher.moveCloser();
    if (supress) {
      return;
    }
    snatcher.backtrack();
    if (supress) {
      return;
    }
    snatcher.release();
    if (supress) {
      return;
    }
    snatcher.advance();
    if (supress) {
      return;
    }
    snatcher.grab();
  }

  @Override
  public void suppress() {
    supress = true;
  }

  @Override
  public boolean takeControl() {
    return true;
  }

}
